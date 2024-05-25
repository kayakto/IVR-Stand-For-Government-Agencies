package ru.example.utill;

import ru.example.model.VideoDocument;

import java.util.Map;

public class VideoDocumentUtils {
    public static VideoDocument createWithoutId(VideoDocument videoDocument) {
        return new VideoDocument(
                videoDocument.getTextSimple(),
                videoDocument.getVideoURL(),
                videoDocument.getChildren(),
                videoDocument.getInfoChildren(),
                videoDocument.isSearchable(),
                videoDocument.getIconUrl()
        );
    }
}
