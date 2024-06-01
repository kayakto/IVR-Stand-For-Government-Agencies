package ru.example.util;

import ru.example.model.VideoDocument;

public class VideoDocumentUtils {
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
