package ru.example;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
                .description("kayakto dev serser")
                .url("http://localhost:8080");
        return new OpenAPI()
                .info(info)
                .addServersItem(server);
    }

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080", "http://localhost:5173",
                                "http://localhost:3000");
            }
        };
    }
}