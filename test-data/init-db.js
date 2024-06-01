print('Initializing MongoDB...');

db = db.getSiblingDB('admin');
print('Switching to admin DB...');
db.auth("kayakto19", "kayakto20");
print('Authenticated as root user...');

db = db.getSiblingDB('assistiveServiseIVR');
print('Switching to assistiveServiseIVR DB...');

db.createUser({
    user: "user",
    pwd: "pwd",
    roles: [{
        role: 'dbOwner',
        db: 'assistiveServiseIVR'
    }]
});
print('User created...');

db.createCollection("videos");
print('Collection created...');

function transformData(data) {
  data.forEach(item => {
    // Transform _id
    if (item._id && item._id.$oid) {
      item._id = ObjectId(item._id.$oid);
    }

    // Transform children
    if (item.children && Array.isArray(item.children)) {
      item.children = item.children.map(child => {
        if (child.$oid) {
          return ObjectId(child.$oid);
        }
        return child;
      });
    }

    if (item.info_children && Array.isArray(item.info_children)) {
      item.info_children = item.info_children.map(child => {
          if (child.$oid) {
              return ObjectId(child.$oid);
          }
          return child;
      });
    }
  });
  return data;
};
const collection = db.getCollection("videos");
const fs = require('fs');
const scriptContent = fs.readFileSync('/docker-entrypoint-initdb.d/test/database.json', 'utf8');
const data = JSON.parse(scriptContent);
const transformedData = transformData(data);
collection.insertMany(transformedData);
print('Data inserted...');

collection.createIndex({ text_simple: "text", is_searchable: 1 }, { default_language: "russian" });
print('Index created...');