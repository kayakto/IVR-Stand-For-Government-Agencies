package ru.pincode_dev.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.pincode_dev.controller.dto.VideoDocumentDTO;

import java.util.Arrays;
import java.util.Objects;

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

    /**
     * Парсит данный объект в VideoDocumentDTO
     * @return документ в формате VideoDocumentDTO
     */
    public VideoDocumentDTO toVideoDocumentDTO() {
        return new VideoDocumentDTO(id,
                textSimple,
                videoURL,
                children,
                infoChildren,
                iconURL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoDocument document = (VideoDocument) o;
        return Objects.equals(id, document.id) &&
                Objects.equals(textSimple, document.textSimple) &&
                Objects.equals(videoURL, document.videoURL) &&
                Arrays.equals(children, document.children) &&
                Arrays.equals(infoChildren, document.infoChildren) &&
                Objects.equals(isSearchable, document.isSearchable) &&
                Objects.equals(iconURL, document.iconURL);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, textSimple, videoURL, isSearchable, iconURL);
        result = 31 * result + Arrays.hashCode(children);
        result = 31 * result + Arrays.hashCode(infoChildren);
        return result;
    }

    @Override
    public String toString() {
        return "VideoDocument{" +
                "id='" + id + '\'' +
                ", textSimple='" + textSimple + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", children=" + Arrays.toString(children) +
                ", infoChildren=" + Arrays.toString(infoChildren) +
                ", isSearchable=" + isSearchable +
                ", iconURL='" + iconURL + '\'' +
                '}';
    }
}
