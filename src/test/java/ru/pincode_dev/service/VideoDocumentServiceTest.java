package ru.pincode_dev.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pincode_dev.IVRStandApplicationTest;
import ru.pincode_dev.model.VideoDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VideoDocumentServiceTest extends IVRStandApplicationTest {
    @Autowired
    VideoDocumentService videoDocumentService;

    @Test
    public void findByIdTest() {
        String documentId = "665b2c17f9ca9159b3b75f54";
        VideoDocument fondedDocument = videoDocumentService.findById(documentId);
        assertArrayEquals(fondedDocument.getChildren(),
                new String[]{"665b2c6db78d24e8a2fa2489"});
        assertEquals(fondedDocument.getVideoURL(),
                "https://storage.yandexcloud.net/akhidov-ivr/16.5.mp4");
    }

    @Test
    public void findIdsByWordTest() {
        String textForSearch = "налоги оплата госпошлина";
        List<String> fondedDocuments = videoDocumentService.findIdsByWords(textForSearch);
        assert fondedDocuments.size() == 2;
        assertEquals(fondedDocuments.get(0), "6659d9d793b975acf5bb3f0d");
        assertEquals(fondedDocuments.get(1), "6659c4943caa308eaf3f45f9");
    }

    @Test
    public void insertDocumentsTest() {
        List<VideoDocument> documentsToInsert = new ArrayList<>();
        documentsToInsert.add(new VideoDocument(
                "1", "родитель", "https://storage.yandexcloud.net/akhidov-ivr/16.5.mp4",
                new String[] {"2"}, new String[0], true,
                "https://storage.yandexcloud.net/akhidov-ivr/16.5.svg"));
        documentsToInsert.add(new VideoDocument(
                "2", "ребенок", "https://storage.yandexcloud.net/akhidov-ivr/16.6.mp4",
                new String[0], new String[0], false,
                "https://storage.yandexcloud.net/akhidov-ivr/16.6.svg"));

        Map<String, String> idmap = videoDocumentService.insertDocuments(documentsToInsert);
        assert idmap.size() == 2;

        String newParentId = idmap.get("1");
        String newChildId = idmap.get("2");

        VideoDocument parentDocument = videoDocumentService.findById(newParentId);
        videoDocumentService.deleteById(newParentId);
        videoDocumentService.deleteById(newChildId); // удаляем из бд и поиска сразу

        assertEquals(parentDocument.getTextSimple(), "родитель");
    }

    @Test
    public void deleteByIdTest() {
        List<VideoDocument> documentsToInsert = new ArrayList<>();
        documentsToInsert.add(new VideoDocument(
                "2", "ребенок", "https://storage.yandexcloud.net/akhidov-ivr/16.6.mp4",
                new String[0], new String[0], true,
                "https://storage.yandexcloud.net/akhidov-ivr/16.6.svg"));

        Map<String, String> idmap = videoDocumentService.insertDocuments(documentsToInsert);
        String newChildId = idmap.get("2");

        boolean isDeletedFromSearch = videoDocumentService.deleteById(newChildId);
        assert isDeletedFromSearch;
    }

    @Test
    public void updateFieldByIdTest() {
        List<VideoDocument> documentsToInsert = new ArrayList<>();
        documentsToInsert.add(new VideoDocument(
                "2", "ребенок", "https://storage.yandexcloud.net/akhidov-ivr/16.6.mp4",
                new String[0], new String[0], true,
                "https://storage.yandexcloud.net/akhidov-ivr/16.6.svg"));

        Map<String, String> idmap = videoDocumentService.insertDocuments(documentsToInsert);
        String newChildId = idmap.get("2");
        VideoDocument updatedDocument = null;

        try {
            updatedDocument = videoDocumentService.updateFieldById(newChildId,
                    "textSimple", "продажа бильярдных столов в МФЦ со скидкой для студентов");
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals(videoDocumentService.findById(newChildId), updatedDocument);
        videoDocumentService.deleteById(newChildId);
    }
}
