# Инструкция для запуска
1. Убедитесь, что у вас установлены следующие зависимости:
    - Java Development Kit (JDK) 17 версии (https://www.oracle.com/java/technologies/downloads/#java17)
    - MongoDB Compass (https://www.mongodb.com/products/tools/compass)
2. Запустите MongoDB Compass на вашем локальном компьютере
   1. Откройте Mongosh
   2. Создайте базу данных assistiveServiseIVR: `use assistiveServiseIVR`
   3. Создайте коллекцию videos: `db.createCollection("videos")`
   4. Сделайте индекс для поиска по тексту: `db.videos.ensureIndex({text_simple: "text", is_searchable: 1}, {default_language: "russian"})`
   5. Зайдите в MongoDB Compass, перейдите в коллекцию videos и нажмите "Add data", далее "Import JSON or CSV file", вставьте данные из test-data/assistiveServiseIVR.videos.json
3. Перейдите в директорию проекта в командной строке и соберите проект с помощью Gradle:
   `gradlew build` или `./gradlew build`
4. После успешной сборки приложения запустите JAR-файл приложения из папки build/libs :
   `java -jar .\build\libs\IVR-Stand-For-Government-Agencies-1.0-SNAPSHOT.jar`
6. Приложение будет запущено на порту 8080 по умолчанию. (http://localhost:8080/swagger-ui/index.html#/)