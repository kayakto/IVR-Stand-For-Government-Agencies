package ru.pincode_dev.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${app.base-url}")
    private String baseUrl;
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().description("Документация для сервиса \"IVR стенд для государственных учреждений\"")
                .title("IVR стенд для государственных учреждений OpenAPI спецификация")
                .version("1.0.2");
        Server server = new Server()
                .description("dev server")
                .url(baseUrl);
        return new OpenAPI()
                .info(info)
                .addServersItem(server);
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springIVR-public")
                .pathsToMatch("/**")
                .build();
    }
}
