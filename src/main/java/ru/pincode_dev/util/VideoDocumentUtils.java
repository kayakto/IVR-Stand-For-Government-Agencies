package ru.pincode_dev.util;

import ru.pincode_dev.model.VideoDocument;

public class VideoDocumentUtils {
    /**
     * Создает документ без идентификатора
     * @param videoDocument документ, у которого надо убрать идентификатор
     * @return документ без идентификатора
     */
    public static VideoDocument createWithoutId(VideoDocument videoDocument) {
        return new VideoDocument(
                videoDocument.getTextSimple(),
                videoDocument.getVideoURL(),
                videoDocument.getChildren(),
                videoDocument.getInfoChildren(),
                videoDocument.getIsSearchable(),
                videoDocument.getIconURL()
        );
    }
}
