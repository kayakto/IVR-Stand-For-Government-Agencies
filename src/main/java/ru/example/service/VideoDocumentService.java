package ru.example.service;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.example.enums.UpdateField;
import ru.example.model.VideoDocument;
import ru.example.model.VideoDocumentRepository;
import ru.example.util.EnumUtils;
import ru.example.util.VideoDocumentUtils;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class VideoDocumentService {
    @Autowired
    private VideoDocumentRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${flask-api.url}")
    private String flaskUrl;

    @Autowired
    private RestTemplate restTemplate;

    public VideoDocument findById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<String> findIdsByWords(String words) {
        String url = flaskUrl + "/get_emb";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"dialog\": [\"" + words + "\"]}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        System.out.println("exchange started");
        try {
            ResponseEntity<List<Map<String, String>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<List<Map<String, String>>>() {}
            );
            System.out.println("exchange successfully");
            List<String> ids = new ArrayList<>();
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                for (Map<String, String> result : response.getBody()) {
                    ids.add(result.get("id"));
                }
            }

            return ids;

        } catch (HttpStatusCodeException e) {
            System.err.println("HTTP Status Code: " + e.getStatusCode());
            System.err.println("HTTP Response Body: " + e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            System.err.println("Resource Access Exception: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        return Collections.emptyList();
    }

    public boolean deleteById(String id) {
        VideoDocument documentToDelete = findById(id);
        boolean result = false;

        if (documentToDelete != null) {
            if (documentToDelete.getIsSearchable()) {
                result = deleteFromSearch(id);
            }
            repository.deleteById(id);
        }

        return result; // TODO false, если удалили из векторного поиска
    }

    /**
     * Удаляет документ из векторного поиска
     * @param id идентификатор документа
     * @return true, если удалилось
     */
    private boolean deleteFromSearch(String id) {
        String url = flaskUrl + "/delete_doc";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"id\": \"" + id + "\"}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, String>>() {}
        );

        return response.getStatusCode() == HttpStatus.OK && response.getBody() != null;
    }

    /**
     * Добавляет документ в векторный поиск
     * @param id идентификатор документа
     * @param textSimple текст документа
     * @return true, если документ добавлен
     */
    private boolean insertToVectorSearch(String id, String textSimple) {
        String url = flaskUrl + "/add_doc";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"id\": \"" + id + "\", \"text_simple\": \"" + textSimple + "\"}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, String>>() {}
        );

        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null){
            System.out.println("Money");
            return true;
        }
        return false;
    }

    /**
     * Вставляет документ в базу данных и при необходимости в векторный поиск
     * @param videoDocument документ для вставки
     * @return идентификатор документа, если вставка произошла успешно
     */
    private String insertDocument(VideoDocument videoDocument) {
        VideoDocument documentToInsert = VideoDocumentUtils.createWithoutId(videoDocument);
        VideoDocument savedDocument = repository.insert(documentToInsert);
        String id = savedDocument.getId();
        if (!videoDocument.getIsSearchable()) {
            return id;
        }
        String textSimple = savedDocument.getTextSimple();
        try {
            return insertToVectorSearch(id, textSimple) ? id : null; // TODO обработать null
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Map<String, String> insertDocuments(List<VideoDocument> remainingDocuments) {
        Map<String, String> idMap = new HashMap<>();
        for (Iterator<VideoDocument> iterator = remainingDocuments.iterator(); iterator.hasNext(); ){
            VideoDocument document = iterator.next();
            if (document.getChildren().length == 0 && document.getInfoChildren().length == 0){
                String fakeId = document.getId();
                String realId = insertDocument(document); // TODO надо обработать
                idMap.put(fakeId, realId);
                iterator.remove();
            }
        }

        boolean documentsInserted;
        do {
            documentsInserted = false;
            for (Iterator<VideoDocument> iterator = remainingDocuments.iterator(); iterator.hasNext(); ) {
                VideoDocument document = iterator.next();
                if (allChildrenInMap(document, idMap)) {
                    updateChildrenIds(document, idMap);
                    String fakeId = document.getId();
                    String realId = insertDocument(document);
                    idMap.put(fakeId, realId);
                    iterator.remove();
                    documentsInserted = true;
                }
            }
        } while (documentsInserted && !remainingDocuments.isEmpty());

        if (!remainingDocuments.isEmpty()) {
            throw new IllegalStateException("Some documents could not be inserted due to missing children references.");
        }

        return idMap;
    }

    /**
     * Проверяет, все ли дети переданного документа в map
     * @param document документ для проверки
     * @param idMap map, хранящая фейковые и настоящие значения идентификаторов
     * @return true, если все дети лежат в map
     */
    private boolean allChildrenInMap(VideoDocument document, Map<String, String> idMap) {
        if (document.getChildren() != null) {
            for (String child : document.getChildren()) {
                if (!idMap.containsKey(child)) {
                    return false;
                }
            }
        }
        if (document.getInfoChildren() != null) {
            for (String infoChild : document.getInfoChildren()) {
                if (!idMap.containsKey(infoChild)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Обновляет в документе идентификаторы детей на настоящие
     * @param document документ для обновления детей
     * @param idMap map, хранящая фейковые и настоящие значения идентификаторов
     */
    private void updateChildrenIds(VideoDocument document, Map<String, String> idMap) {
        if (document.getChildren() != null) {
            List<String> updatedChildren = new ArrayList<>();
            for (String child : document.getChildren()) {
                updatedChildren.add(idMap.get(child));
            }
            document.setChildren(updatedChildren.toArray(new String[0]));
        }
        if (document.getInfoChildren() != null) {
            List<String> updatedInfoChildren = new ArrayList<>();
            for (String infoChild : document.getInfoChildren()) {
                updatedInfoChildren.add(idMap.get(infoChild));
            }
            document.setInfoChildren(updatedInfoChildren.toArray(new String[0]));
        }
    }

    public VideoDocument updateFieldById(String id, String fieldName, Object newValue) throws Exception {
        if (!EnumUtils.isValidUpdateField(fieldName)) {
            return null;
        }
        // проверка существования документа
        VideoDocument oldDocument = findById(id);
        if (oldDocument == null) {
            return null;
        }

        Field field = getFieldByName(VideoDocument.class, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("No such field: " + fieldName);
        }

        Object convertedValue = convertValueToFieldType(newValue, field.getType());

        if (!isValueChanged(field, oldDocument, convertedValue)) {
            return oldDocument;
        }

        if (fieldName.equals(UpdateField.IS_SEARCHABLE.getFieldName())) {
            handleSearchableField(id, oldDocument.getTextSimple(),
                    (Boolean) newValue);
        }

        updateFieldInDocument(id, fieldName, convertedValue);

        if (fieldName.equals(UpdateField.TEXT_SIMPLE.getFieldName()) && oldDocument.getIsSearchable()) {
            handleTextSimpleField(id, newValue.toString());
        }

        return findById(id); // Возвращаем обновленный документ
    }

    /**
     * получает fieldName поле из указанного класса
     * @param classForSearсh класс, из которого получаем поле
     * @param fieldName название поля
     * @return Поле класса
     */
    private Field getFieldByName(Class<?> classForSearсh, String fieldName) {
        try {
            Field field = classForSearсh.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Конвертирует переданный параметр в нужный класс
     * @param value значение для конвертации
     * @param fieldType класс, в который нужно конвертировать
     * @return value нужного класса
     */
    private Object convertValueToFieldType(Object value, Class<?> fieldType) {
        if (value == null) {
            return null;
        }

        if (fieldType.isInstance(value)) {
            return value;
        }

        if (fieldType == String[].class && value instanceof ArrayList) {
            ArrayList<?> list = (ArrayList<?>) value;
            ObjectId[] objectIds = new ObjectId[list.size()];
            for (int i = 0; i < list.size(); i++) {
                objectIds[i] = new ObjectId((String) list.get(i));
            }
            return objectIds;
        }
        throw new IllegalArgumentException("Cannot convert value to field type " + fieldType.getSimpleName());
    }

    /**
     * Проверяет, отличается ли переданное новое значение от уже имеющегося
     * @param field поле, значение которого нужно поменять
     * @param oldDocument документ, в котором нужно поменять значение поля
     * @param newValue новое значение
     * @return true, если значение отличается
     * @throws IllegalAccessException если не получится получить поле из документа
     */
    private boolean isValueChanged(Field field, VideoDocument oldDocument, Object newValue) throws IllegalAccessException {
        Object currentValue = field.get(oldDocument);
        return !((currentValue == null && newValue == null) || (currentValue != null && currentValue.equals(newValue)));
    }

    /**
     * Обновляет массив элементов, которые есть в векторном поиске
     * @param id идентификатор документа
     * @param textSimple текст документа
     * @param newValue новое значение isSearchable
     * @throws Exception если значение не обновилось
     */
    private void handleSearchableField(String id, String textSimple, Boolean newValue) throws Exception {
        boolean addedOrDeletedStatus;
        if (Boolean.TRUE.equals(newValue)) {
            addedOrDeletedStatus = insertToVectorSearch(id, textSimple);
        } else {
            addedOrDeletedStatus = deleteFromSearch(id);
        }
        if (!addedOrDeletedStatus) {
            throw new Exception("Failed to update searchable status");
        }
    }

    /**
     * Обновляет переданное поле в документе в базе данных
     * @param id идентификатор документа
     * @param fieldName название поля
     * @param newValue новое значение поля
     * @throws Exception если поле не обновилось в базе данных
     */
    private void updateFieldInDocument(String id, String fieldName, Object newValue) throws Exception {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set(fieldName, newValue);
        UpdateResult result = mongoTemplate.updateFirst(query, update, VideoDocument.class);

        if (result.getMatchedCount() == 0) {
            throw new Exception("Failed to update document with id " + id);
        }
    }

    /**
     * Обновляет текст в векторном поиске
     * @param id идентификатор записи
     * @param newText новый текст
     * @throws Exception если не получилось обновить
     */
    private void handleTextSimpleField(String id, String newText) throws Exception {
        deleteFromSearch(id);
        insertToVectorSearch(id, newText);
    }
}
