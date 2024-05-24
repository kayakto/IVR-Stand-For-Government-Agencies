package ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.example.controller.request.VideoDocumentRequest;
import ru.example.model.VideoDocument;
import ru.example.model.VideoDocumentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class VideoDocumentService {
    @Autowired
    private VideoDocumentRepository repository;

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

    public String deleteById(String id) {
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

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String deletedId = response.getBody().get("id");
            repository.deleteById(deletedId);
            System.out.println("Success");
            return deletedId;
        } else {
            System.out.println("Failure");
            return null;
        }
    }

    public String insertDocument(VideoDocument videoDocument){
        VideoDocument savedDocument = repository.insert(videoDocument);
        String id = savedDocument.getId();
        String textSimple = savedDocument.getTextSimple();

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
            return id;
        }
        else {
            System.out.println("no Money");
            return null;
        }
    }
}
