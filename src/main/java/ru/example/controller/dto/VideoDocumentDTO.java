package ru.example.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.example.model.VideoDocument;

@Data
@AllArgsConstructor
@Schema(description = "Информация о видео-документе")
public class VideoDocumentDTO { // maybe record???
    @Schema(description = "Идентификатор", example = "6612b06653c466832b383aeb")
    private final String id;
    @Schema(description = "Текст на простом языке", example = "Внесение изменений в паспорт старого образца/Вписать детей в загранпаспорт")
    private final String textSimple;
    @Schema(description = "Ссылка на видео", example = "https://storage.yandexcloud.net/akhidov-ivr/long.mp4")
    private final String videoURL;
    @Schema(description = "Массив дочерних записей", example = "[\"661415256df3a0792d1c2fe3\"]")
    private final String[] children;
    @Schema(description = "Массив с идентификаторами дополнительной информации", example = "null")
    private final String[] infoChildren;
    @Schema(description = "Ссылка на иконку", example = "https://storage.yandexcloud.net/akhidov-ivr/icon5.png")
    private final String iconUrl;

    public VideoDocument toVideoDocument(boolean isSearchable) {
        return new VideoDocument(id, textSimple, videoURL, children, infoChildren, isSearchable, iconUrl);
    }
}
