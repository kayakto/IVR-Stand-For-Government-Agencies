package ru.example.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoDocumentRepository extends MongoRepository<VideoDocument, String> {
    @Query("{ $text: { $search: ?0 }, is_searchable: true }")
    List<VideoDocument> findByText(String searchText);
}
