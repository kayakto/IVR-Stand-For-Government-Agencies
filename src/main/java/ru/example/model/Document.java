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
    private final String category;// TODO delete
    @Field("text_simple")
    private final String textSimple;
    @Field("video_url")
    private final String videoURL;
    @Field("children")
    private final String[] children;
    @Field("info_children")
    private final String[] infoChildren;
    @Field("is_searchable")
    private final String[] isSearchable;
}
