package ru.example.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import ru.example.model.VideoDocument;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Информация о видео-документе")
public class VideoDocumentDTO { // maybe record???
    @Schema(description = "Идентификатор", example = "6659d643039023acf1059717")
    private final String id;
    @Schema(description = "Текст на простом языке", example = "Достижение 20 лет/Достижение 45 лет")
    private final String textSimple;
    @Schema(description = "Ссылка на видео", example = "https://storage.yandexcloud.net/akhidov-ivr/22.mp4")
    private final String videoURL;
    @Schema(description = "Массив дочерних записей", example = "[\"6659d7e53e980cbfb95dcd54\"]")
    private final String[] children;
    @Schema(description = "Массив с идентификаторами дополнительной информации", example = "null")
    private final String[] infoChildren;
    @Schema(description = "Ссылка на иконку", example = "https://storage.yandexcloud.net/akhidov-ivr/icon22.svg")
    private final String iconURL;

    public VideoDocument toVideoDocument(Boolean isSearchable) {
        return new VideoDocument(id,
                textSimple,
                videoURL,
                children,
                infoChildren,
                isSearchable,
                iconURL);
    }
}
