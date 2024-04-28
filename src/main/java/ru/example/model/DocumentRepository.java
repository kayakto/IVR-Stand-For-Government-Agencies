package ru.example.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<Document, String> {
    @Query("{ $text: { $search: ?0 }, is_searchable: true }")
    List<Document> findByText(String searchText);
}
