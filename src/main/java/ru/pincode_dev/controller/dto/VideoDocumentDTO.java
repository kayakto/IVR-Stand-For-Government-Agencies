package ru.pincode_dev.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pincode_dev.model.VideoDocument;

import java.util.Arrays;
import java.util.Objects;

@Data
@AllArgsConstructor
@Schema(description = "Информация о видео-документе")
public class VideoDocumentDTO {
    @Schema(description = "Идентификатор", example = "6659d643039023acf1059717")
    private final String id;
    @Schema(description = "Текст на простом языке", example = "Достижение 20 лет/Достижение 45 лет")
    private final String textSimple;
    @Schema(description = "Ссылка на видео", example = "https://storage.yandexcloud.net/akhidov-ivr/22.mp4")
    private final String videoURL;
    @Schema(description = "Массив дочерних записей", example = "[\"6659d7e53e980cbfb95dcd54\"]")
    private final String[] children;
    @Schema(description = "Массив с идентификаторами дополнительной информации", example = "[]")
    private final String[] infoChildren;
    @Schema(description = "Ссылка на иконку", example = "https://storage.yandexcloud.net/akhidov-ivr/icon22.svg")
    private final String iconURL;

    /**
     * Преобразует VideoDocumentDto в VideoDocument
     * @param isSearchable флаг, отвечающий за возможность поиска элемента
     * @return Этот документ в формате VideoDocument
     */
    public VideoDocument toVideoDocument(Boolean isSearchable) {
        return new VideoDocument(id,
                textSimple,
                videoURL,
                children,
                infoChildren,
                isSearchable,
                iconURL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoDocumentDTO that = (VideoDocumentDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(textSimple, that.textSimple) && Objects.equals(videoURL, that.videoURL) && Arrays.equals(children, that.children) && Arrays.equals(infoChildren, that.infoChildren) && Objects.equals(iconURL, that.iconURL);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, textSimple, videoURL, iconURL);
        result = 31 * result + Arrays.hashCode(children);
        result = 31 * result + Arrays.hashCode(infoChildren);
        return result;
    }

    @Override
    public String toString() {
        return "VideoDocumentDTO{" +
                "id='" + id + '\'' +
                ", textSimple='" + textSimple + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", children=" + Arrays.toString(children) +
                ", infoChildren=" + Arrays.toString(infoChildren) +
                ", iconURL='" + iconURL + '\'' +
                '}';
    }
}
