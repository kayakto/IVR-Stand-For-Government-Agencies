package ru.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.example.controller.dto.VideoDocumentDTO;

@Getter
@Setter
@Document(collection = "videos")
@AllArgsConstructor
public class VideoDocument {
    @Id
    private String id;
    @Field("text_simple")
    private final String textSimple;
    @Field("video_url")
    private final String videoURL;
    @Field("children")
    private String[] children;
    @Field("info_children")
    private String[] infoChildren;
    @Field("is_searchable")
    private final Boolean isSearchable;
    @Field("icon_url")
    private final String iconURL;

    @PersistenceConstructor
    public VideoDocument(String textSimple, String videoURL,
                         String[] children, String[] infoChildren,
                         Boolean isSearchable, String iconURL) {
        this.textSimple = textSimple;
        this.videoURL = videoURL;
        this.children = children;
        this.infoChildren = infoChildren;
        this.isSearchable = isSearchable;
        this.iconURL = iconURL;
    }

    public VideoDocumentDTO toVideoDocumentDTO() {
        return new VideoDocumentDTO(id,
                textSimple,
                videoURL,
                children,
                infoChildren,
                iconURL);
    }
}
