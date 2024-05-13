package ru.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.example.controller.dto.VideoDocumentDTO;

@AllArgsConstructor
@Getter
@Document(collection = "videos")
public class VideoDocument {
    @Id
    private final String id;
    @Field("text_simple")
    private final String textSimple;
    @Field("video_url")
    private final String videoURL;
    @Field("children")
    private final String[] children;
    @Field("info_children")
    private final String[] infoChildren;
    @Field("is_searchable")
    private final boolean isSearchable;

    public VideoDocumentDTO toVideoDocumentDTO() {
        return new VideoDocumentDTO(id, textSimple, videoURL, children, infoChildren);
    }
}
