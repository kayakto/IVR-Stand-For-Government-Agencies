package ru.pincode_dev.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ru.pincode_dev.model.VideoDocument;

@AllArgsConstructor
@Data
@Getter
@Schema(description = "Информация о видео-документе, запрашиваемая для добавления")
public class VideoDocumentRequest {
    @Schema(description = "Идентификатор", example = "1")
    private final String id;
    @Schema(description = "Текст на простом языке", example = "Достижение 20 лет/Достижение 45 лет")
    private final String textSimple;
    @Schema(description = "Ссылка на видео", example = "https://storage.yandexcloud.net/akhidov-ivr/22.mp4")
    private final String videoURL;
    @Schema(description = "Массив дочерних записей", example = "[\"2\"]")
    private final String[] children;
    @Schema(description = "Массив с идентификаторами дополнительной информации", example = "[]")
    private final String[] infoChildren;
    @Schema(description = "Флаг, показывающий, можно ли искать данный документ", example = "true")
    private final Boolean searchable;
    @Schema(description = "Ссылка на иконку", example = "https://storage.yandexcloud.net/akhidov-ivr/icon22.svg")
    private final String iconURL;

    /**
     * Парсит этот документ в VideoDocument
     * @return документ в  формате VideoDocument
     */
    public VideoDocument toVideoDocument(){
        return new VideoDocument(
                id,
                textSimple,
                videoURL,
                children,
                infoChildren,
                searchable,
                iconURL);
    }
}
