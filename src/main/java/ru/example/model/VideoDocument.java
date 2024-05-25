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
    @Setter
    private String[] children;
    @Field("info_children")
    @Setter
    private String[] infoChildren;
    @Field("is_searchable")
    private final boolean isSearchable;
    @Field("icon_url")
    private final String iconUrl;

    @PersistenceConstructor
    public VideoDocument(String textSimple, String videoURL,
                         String[] children, String[] infoChildren,
                         boolean isSearchable, String iconUrl) {
        this.textSimple = textSimple;
        this.videoURL = videoURL;
        this.children = children;
        this.infoChildren = infoChildren;
        this.isSearchable = isSearchable;
        this.iconUrl = iconUrl;
    }


//    public VideoDocument(String id, String textSimple,
//                         String videoURL, String[] children,
//                         String[] infoChildren, boolean isSearchable) {
//        this.id = id;
//        this.textSimple = textSimple;
//        this.videoURL = videoURL;
//        this.children = children;
//        this.infoChildren = infoChildren;
//        this.isSearchable = isSearchable;
//    }

    public VideoDocumentDTO toVideoDocumentDTO() {
        return new VideoDocumentDTO(id, textSimple, videoURL, children, infoChildren, iconUrl);
    }
}
