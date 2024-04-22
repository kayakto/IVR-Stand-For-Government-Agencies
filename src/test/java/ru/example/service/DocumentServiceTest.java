package ru.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.example.IVRStandApplicationTest;
import ru.example.model.Document;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentServiceTest extends IVRStandApplicationTest {
    @Autowired
    DocumentService documentService;

    @Test
    public void findByIdTest() {
        String documentId = "6612ad7253c466832b383ae7";
        Document fondedDocument = documentService.findById(documentId);
        assertArrayEquals(fondedDocument.getKeywords(),
                new String[]{"консультация", "загранпаспорт", "граница"});
        assertArrayEquals(fondedDocument.getChildren(),
                new String[]{"6612b05e53c466832b383ae9",
                "6612b06253c466832b383aea", "6612b06653c466832b383aeb"});
        assertNull(fondedDocument.getParent());
        assertEquals(fondedDocument.getVideoURL(),
                "https://drive.google.com/file/d/1-XhH1CQaWZW3S_x93PQGM5wy4A--mns8/view?usp=sharing");
        assertEquals(fondedDocument.getTextSimple(), "Консультация по загранпаспорту");
    }
}
