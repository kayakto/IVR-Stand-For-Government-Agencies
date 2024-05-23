# Инструкция для запуска(Backend)
1. Скачайте данный репозиторий:
    ```bash
    git clone https://github.com/kayakto/IVR-Stand-For-Government-Agencies.git
    ```
2. Перейдите в директорию проекта:
    ```bash
    cd IVR-Stand-For-Government-Agencies
    ```
3. Сделайте билд и запустите приложение с помощью Docker Compose:
    ```bash
    docker compose up -d
    ```
Приложение будет доступно по адресу http://localhost:8080 
Документация расположена по адресу http://localhost:8080/ivr-hor/swagger-ui/index.html#

Чтобы остановить приложение и удалить контейнеры:
```bash
docker-compose down
```
или
```bash
docker-compose down --remove-orphans
```