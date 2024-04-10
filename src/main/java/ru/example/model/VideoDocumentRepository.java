package ru.example.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoDocumentRepository extends MongoRepository<VideoDocument, String> {

}
