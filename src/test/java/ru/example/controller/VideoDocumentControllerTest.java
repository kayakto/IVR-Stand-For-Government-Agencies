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
        VideoDocumentDTO realDocumentDTO = new VideoDocumentDTO("6612b06653c466832b383aeb", "6612ad7253c466832b383ae7",
                "Внесение изменений в паспорт старого образца/Вписать детей в загранпаспорт",
                "Внесение изменений в паспорт старого образца/Вписать детей в загранпаспорт",
                "https://drive.google.com/file/d/1-XhH1CQaWZW3S_x93PQGM5wy4A--mns8/view?usp=sharing",
                new String[] {"661415256df3a0792d1c2fe3"});
        assertEquals(foundedDocument.getId(), realDocumentDTO.getId());
        assertEquals(foundedDocument.getParent(), realDocumentDTO.getParent());
        assertEquals(foundedDocument.getTextSimple(), realDocumentDTO.getTextSimple());
        assertEquals(foundedDocument.getTextRussian(), realDocumentDTO.getTextRussian());
        assertEquals(foundedDocument.getVideoURL(), realDocumentDTO.getVideoURL());
        assertArrayEquals(foundedDocument.getChildren(), realDocumentDTO.getChildren());
    }

    @Test
    public void getVideoDocumentByIdsTest() {
        List<String> ids = List.of("66141d446df3a0792d1c2fe7", "66141d446df3a0792d1c2fe8");
        List<VideoDocumentDTO> foundedDocuments = videoDocumentController.getVideoDocumentsByIds(ids);
        VideoDocumentDTO realDoc1 = new VideoDocumentDTO("66141d446df3a0792d1c2fe7",
                "6612b29c53c466832b383aef",
                "До 14 лет", "Загранпаспорт ребенку до 14 лет",
                "https://drive.google.com/file/d/1-XhH1CQaWZW3S_x93PQGM5wy4A--mns8/view?usp=sharing",
                new String[] {"661420836df3a0792d1c2feb"});
        VideoDocumentDTO realDoc2 = new VideoDocumentDTO("66141d446df3a0792d1c2fe8",
                "6612b29c53c466832b383aef", "После 14 лет",
                "Загранпаспорт ребенку после 14 лет",
                "https://drive.google.com/file/d/1-XhH1CQaWZW3S_x93PQGM5wy4A--mns8/view?usp=sharing",
                new String[] {"661421536df3a0792d1c2fec"});
        VideoDocumentDTO foundedDocument1 = foundedDocuments.get(0);
        VideoDocumentDTO foundedDocument2 = foundedDocuments.get(1);
        assertEquals(2, foundedDocuments.size());
        assertEquals(realDoc1, foundedDocument1);
        assertEquals(realDoc2, foundedDocument2);
    }
}
