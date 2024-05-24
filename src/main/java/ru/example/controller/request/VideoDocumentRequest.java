package ru.example.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.example.model.VideoDocument;

@AllArgsConstructor
@Data
@Getter
@Schema(description = "Информация о видео-документе, запрашиваемая для добавления")
public class VideoDocumentRequest {
    @Schema(description = "Текст на простом языке", example = "Внесение изменений в паспорт старого образца/Вписать детей в загранпаспорт")
    private final String textSimple;
    @Schema(description = "Ссылка на видео", example = "https://storage.yandexcloud.net/akhidov-ivr/long.mp4")
    private final String videoURL;
    @Schema(description = "Массив дочерних записей", example = "[\"661415256df3a0792d1c2fe3\"]")
    private final String[] children;
    @Schema(description = "Массив с идентификаторами дополнительной информации", example = "null")
    private final String[] infoChildren;
    @Schema(description = "Флаг, показывающий, можно ли искать данный документ", example = "true")
    private final boolean isSearchable;

    public VideoDocument toVideoDocument(){
        return new VideoDocument(
                this.textSimple,
                this.videoURL,
                this.children,
                this.infoChildren,
                this.isSearchable
        );
    }
}
