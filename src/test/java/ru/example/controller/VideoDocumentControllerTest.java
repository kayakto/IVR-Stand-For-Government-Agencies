package ru.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.example.IVRStandApplicationTest;
import ru.example.controller.dto.VideoDocumentDTO;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class VideoDocumentControllerTest extends IVRStandApplicationTest {
    @Autowired
    VideoDocumentController videoDocumentController;

    @Test
    public void getVideoDocumentTest() {
        VideoDocumentDTO foundedDocument = videoDocumentController.getVideoDocument("6612ad7253c466832b383ae7").getBody();
        VideoDocumentDTO realDocumentDTO = new VideoDocumentDTO("6612ad7253c466832b383ae7",
                "Консультация по загранпаспорту",
                "https://storage.yandexcloud.net/akhidov-ivr/long.mp4",
                new String[]{"6612b05e53c466832b383ae9", "6612b06253c466832b383aea", "6612b06653c466832b383aeb"},
                null, "https://storage.yandexcloud.net/akhidov-ivr/icon5.png");
        assert foundedDocument != null;
        assertEquals(foundedDocument.getId(), realDocumentDTO.getId());
    }

    @Test
    public void getVideoDocumentByIdsTest() {
        List<String> ids = List.of("66141d446df3a0792d1c2fe7", "66141d446df3a0792d1c2fe8");
        List<VideoDocumentDTO> foundedDocuments = videoDocumentController.getVideoDocumentsByIds(ids).getBody();
        VideoDocumentDTO realDoc1 = new VideoDocumentDTO("66141d446df3a0792d1c2fe7",
                "До 14 лет", "https://storage.yandexcloud.net/akhidov-ivr/long.mp4",
                new String[] {"661420836df3a0792d1c2feb"}, null, "https://storage.yandexcloud.net/akhidov-ivr/icon5.png");
        VideoDocumentDTO realDoc2 = new VideoDocumentDTO("66141d446df3a0792d1c2fe8",
                "После 14 лет","https://storage.yandexcloud.net/akhidov-ivr/long.mp4",
                new String[] {"661421536df3a0792d1c2fec"},null, "https://storage.yandexcloud.net/akhidov-ivr/icon5.png");
        assert foundedDocuments != null;
        VideoDocumentDTO foundedDocument1 = foundedDocuments.get(0);
        VideoDocumentDTO foundedDocument2 = foundedDocuments.get(1);
        assertEquals(realDoc1, foundedDocument1);
        assertEquals(realDoc2, foundedDocument2);
    }

    @Test
    public void getMainDocumentsTest(){
        List<VideoDocumentDTO> mainDocumentsDTO = videoDocumentController.getMainDocuments().getBody();
        List<String> mainDocumentsIds = new ArrayList<>();
        assert mainDocumentsDTO != null;
        for (VideoDocumentDTO dto : mainDocumentsDTO) {
            mainDocumentsIds.add(dto.getId());
        }
        assertEquals(4, mainDocumentsIds.size());
    }
}
