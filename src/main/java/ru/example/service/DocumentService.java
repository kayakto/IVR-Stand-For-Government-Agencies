package ru.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.model.Document;
import ru.example.model.DocumentRepository;
import java.util.List;

@Component
public class DocumentService {
    @Autowired
    private DocumentRepository repository;

    public Document findById(String id) {
        return repository.findById(id).orElse(null);
    }
}
