package ru.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.example.IVRStandApplicationTest;
import ru.example.model.VideoDocument;

import static org.junit.jupiter.api.Assertions.*;

public class VideoDocumentServiceTest extends IVRStandApplicationTest {
    @Autowired
    VideoDocumentService videoDocumentService;

    @Test
    public void findByIdTest() {
        String documentId = "6612ad7253c466832b383ae7";
        VideoDocument fondedDocument = videoDocumentService.findById(documentId);
        assertArrayEquals(fondedDocument.getChildren(),
                new String[]{"6612b05e53c466832b383ae9",
                "6612b06253c466832b383aea", "6612b06653c466832b383aeb"});
        assertEquals(fondedDocument.getVideoURL(),
                "https://storage.yandexcloud.net/akhidov-ivr/long.mp4");
        assertEquals(fondedDocument.getTextSimple(), "Консультация по загранпаспорту");
    }
}
