package ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.model.VideoDocument;
import ru.example.model.VideoDocumentRepository;

import java.util.List;

@Component
public class VideoDocumentService {
    @Autowired
    private VideoDocumentRepository repository;

    public VideoDocument findById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<VideoDocument> findByWords(String words) {
        return repository.findByText(words); // TODO
    }
}
