import numpy as np
import faiss
from transformers import AutoTokenizer, AutoModel
import torch
from flask import Flask, request, jsonify
import pymongo
import os

os.environ['KMP_DUPLICATE_LIB_OK'] = 'True'
mongo_uri = os.getenv('MONGO_URI', 'mongodb://localhost:27017/')

client = pymongo.MongoClient(mongo_uri)
db = client["assistiveServiseIVR"]
collection = db["videos"]

app = Flask(__name__)

tokenizer = AutoTokenizer.from_pretrained("intfloat/multilingual-e5-large")
model = AutoModel.from_pretrained("intfloat/multilingual-e5-large")

def embed_documents(documents, model, tokenizer):
    embeddings = []
    for doc in documents:
        if doc is None:
            print("Empty document found!")
            continue
        print(f"Embedding document: {doc}")
        inputs = tokenizer(doc, return_tensors='pt', padding=True, truncation=True)
        with torch.no_grad():
            embedding = model(**inputs).last_hidden_state.mean(dim=1).numpy()
        embeddings.append(embedding)
    return np.vstack(embeddings)

# Извлечение данных из MongoDB
documents = []
ids = []
for doc in collection.find({"is_searchable": True}):
    documents.append(doc["text_simple"])
    ids.append(str(doc["_id"]))

if not documents:
    raise ValueError("No documents to embed")

document_embeddings = embed_documents(documents, model, tokenizer)

index = faiss.IndexFlatL2(document_embeddings.shape[1])
index.add(document_embeddings)

def retrieve_documents(query, index, model, tokenizer, texts, top_k=2):
    query_embedding = embed_documents([query], model, tokenizer)
    distances, indices = index.search(query_embedding, top_k)
    results = [{"id": str(ids[i]), "text": texts[i]} for i in indices[0] if i != -1]
    return results

@app.route('/get_emb', methods=['POST'])
def get_emb():
    data = request.json
    dialog_data = data.get('dialog', [])

    if not dialog_data:
        return jsonify({"error": "No dialog provided"}), 400

    text = " ".join(dialog_data)
    results = retrieve_documents(text, index, model, tokenizer, documents)
    return jsonify(results)


@app.route('/add_doc', methods=['POST'])
def add_doc():
    data = request.json
    text_simple = data.get('text_simple')
    doc_id = data.get('id')

    if not text_simple:
        return jsonify({"error": "No text_simple provided"}), 400
    if not doc_id:
        return jsonify({"error": "No id provided"}), 400

    # Добавление документа в MongoDB
    # new_doc = {
    #     "text_simple": text_simple,
    #     "is_searchable": True
    # }
    # result = collection.insert_one(new_doc)
    # new_id = result.inserted_id

    # Встраивание документа
    new_embedding = embed_documents([text_simple], model, tokenizer)
    index.add(new_embedding)

    # Обновление локальных списков
    documents.append(text_simple)
    ids.append(str(doc_id))
    print(ids)

    return jsonify({"id": str(doc_id), "text": text_simple}), 201


@app.route('/delete_doc', methods=['POST'])
def delete_doc():
    data = request.json
    doc_id = data.get('id')

    if not doc_id:
        return jsonify({"error": "No id provided"}), 400


    try:
        idx = ids.index(doc_id)  # Преобразование ObjectId в строку
    except ValueError:
        return jsonify({"error": "Document not found"}), 404

    # Удаление вектора из индекса FAISS
    index.remove_ids(np.array([idx]))

    # Обновление локальных списков
    del documents[idx]
    del ids[idx]

    return jsonify({"id": doc_id, "status": "deleted"}), 200

if __name__ == '__main__':
    app.run('0.0.0.0', port=5005) #тут я поменял хост
