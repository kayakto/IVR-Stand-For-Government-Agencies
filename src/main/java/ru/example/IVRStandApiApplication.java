package ru.example;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IVRStandApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(IVRStandApiApplication.class);
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().description("Документация для сервиса \"IVR стенд для государственных учреждений\"")
                .title("IVR стенд для государственных учреждений OpenAPI спецификация")
                .version("1.0.0");
        Server server = new Server()
                .description("dev server")
                .url("http://localhost:8080");
        return new OpenAPI()
                .info(info)
                .addServersItem(server);
    }
}