package ru.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.example.IVRStandApplicationTest;
import ru.example.controller.dto.VideoDocumentDTO;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class VideoDocumentControllerTest extends IVRStandApplicationTest {
    @Autowired
    VideoDocumentController videoDocumentController;

    @Test
    public void getVideoDocumentTest() {
        VideoDocumentDTO foundedDocument = videoDocumentController.getVideoDocument("6612b06653c466832b383aeb");
        VideoDocumentDTO realDocumentDTO = new VideoDocumentDTO("6612b06653c466832b383aeb",
                "Внесение изменений в паспорт старого образца/Вписать детей в загранпаспорт",
                "https://storage.yandexcloud.net/akhidov-ivr/long.mp4",
                new String[] {"661415256df3a0792d1c2fe3"}, null);
        assertEquals(foundedDocument.getId(), realDocumentDTO.getId());
        assertEquals(foundedDocument.getTextSimple(), realDocumentDTO.getTextSimple());
        assertEquals(foundedDocument.getVideoURL(), realDocumentDTO.getVideoURL());
        assertArrayEquals(foundedDocument.getChildren(), realDocumentDTO.getChildren());
        assertArrayEquals(foundedDocument.getInfoChildren(), realDocumentDTO.getInfoChildren());
    }

    @Test
    public void getVideoDocumentByIdsTest() {
        List<String> ids = List.of("66141d446df3a0792d1c2fe7", "66141d446df3a0792d1c2fe8");
        List<VideoDocumentDTO> foundedDocuments = videoDocumentController.getVideoDocumentsByIds(ids);
        VideoDocumentDTO realDoc1 = new VideoDocumentDTO("66141d446df3a0792d1c2fe7",
                "До 14 лет", "https://storage.yandexcloud.net/akhidov-ivr/long.mp4",
                new String[] {"661420836df3a0792d1c2feb"}, null);
        VideoDocumentDTO realDoc2 = new VideoDocumentDTO("66141d446df3a0792d1c2fe8",
                "После 14 лет","https://storage.yandexcloud.net/akhidov-ivr/long.mp4",
                new String[] {"661421536df3a0792d1c2fec"}, null);
        VideoDocumentDTO foundedDocument1 = foundedDocuments.get(0);
        VideoDocumentDTO foundedDocument2 = foundedDocuments.get(1);
        assertEquals(2, foundedDocuments.size());
        assertEquals(realDoc1, foundedDocument1);
        assertEquals(realDoc2, foundedDocument2);
    }
}
