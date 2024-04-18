package ru.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Getter
@org.springframework.data.mongodb.core.mapping.Document(collection = "videos")
public class Document {
    @Id
    private final String id;
    @Field("category")
    private final String category;
    @Field("keywords")
    private final String[] keywords;
    @Field("parent")
    private final String parent;
    @Field("text_simple")
    private final String textSimple;
    @Field("text_russian")
    private final String textRussian;
    @Field("video_url")
    private final String videoURL;
    @Field("children")
    private final String[] children;
}
