package ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.model.VideoDocument;
import ru.example.model.VideoDocumentRepository;
import java.util.List;

@Component
public class VideoService {
    @Autowired
    private VideoDocumentRepository repository;

    public List<VideoDocument> findAllById(List<String> ids) {
        return (List<VideoDocument>) repository.findAllById(ids);
    }

    public VideoDocument findById(String id) {
        return repository.findById(id).orElse(null);
    }
}
