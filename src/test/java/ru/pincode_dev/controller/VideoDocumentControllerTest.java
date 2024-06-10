package ru.pincode_dev.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pincode_dev.IVRStandApplicationTest;
import ru.pincode_dev.controller.dto.VideoDocumentDTO;
import ru.pincode_dev.controller.request.UpdateRequest;
import ru.pincode_dev.controller.request.VideoDocumentRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class VideoDocumentControllerTest extends IVRStandApplicationTest {
    @Autowired
    VideoDocumentController videoDocumentController;

    @Test
    public void getVideoDocumentTest() {
        VideoDocumentDTO foundedDocument = videoDocumentController.
                getVideoDocument("665b26a9be980542eae0b2fb").getBody();

        VideoDocumentDTO realDocumentDTO = new VideoDocumentDTO("665b26a9be980542eae0b2fb",
                "Документы, удостоверяющие личность законных представителей или документы, которые подтверждают установление опеки;",
                "https://storage.yandexcloud.net/akhidov-ivr/15.2.mp4",
                new String[]{"665b2722f17c1a0c992e6dbd"},
                null, "https://storage.yandexcloud.net/akhidov-ivr/15.2.svg");

        assert foundedDocument != null;
        assertEquals(foundedDocument.getId(), realDocumentDTO.getId());
    }

    @Test
    public void getVideoDocumentsByIdsTest() {
        List<String> ids = List.of("6659c75d7bc437f3b63c607b", "6659cd3b888de37cc538c467");
        List<VideoDocumentDTO> foundedDocuments = videoDocumentController.
                getVideoDocumentsByIds(ids).getBody();

        VideoDocumentDTO realDoc1 = new VideoDocumentDTO("6659c75d7bc437f3b63c607b",
                "Постоянная регистрация Ребенок после 14 лет", "https://storage.yandexcloud.net/akhidov-ivr/5.mp4",
                new String[] {"6659cd3b888de37cc538c467"}, null, "https://storage.yandexcloud.net/akhidov-ivr/icon5.svg");
        VideoDocumentDTO realDoc2 = new VideoDocumentDTO("6659cd3b888de37cc538c467",
                "- Для оформления регистрации по месту проживания несовершеннолетнего лица с 14 до 18 лет необходимо предоставить:",
                "https://storage.yandexcloud.net/akhidov-ivr/11.1.mp4",
                new String[] {"6659cfddcad64ac69a381ebc"},null, "https://storage.yandexcloud.net/akhidov-ivr/5.svg");

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

        assertEquals(2, mainDocumentsIds.size());
    }

    @Test
    public void searchVideoDocumentsByTextTest() {
        String textForSearch = "налоги оплата госпошлина";
        List<VideoDocumentDTO> fondedDocuments = videoDocumentController.
                searchVideoDocumentsByText(textForSearch).getBody();
        assert Objects.requireNonNull(fondedDocuments).size() == 2;
        assertEquals(fondedDocuments.get(0).getId(), "6659d9d793b975acf5bb3f0d");
        assertEquals(fondedDocuments.get(1).getTextSimple(), "Консультация по услугам");
    }

    @Test
    public void deleteByIdTest() {
        VideoDocumentRequest[] documentsToInsert = {
                new VideoDocumentRequest(
                        "1", "родитель 1", "https://storage.yandexcloud.net/akhidov-ivr/16.5.mp4",
                        new String[0], new String[0], true,
                        "https://storage.yandexcloud.net/akhidov-ivr/16.5.svg"),
        };

        Map<String, String> idmap = videoDocumentController.
                addVideoDocument(documentsToInsert).getBody();
        assert idmap != null;
        String newParentId = idmap.get("1");

        boolean isDeletedFromSearch = Boolean.TRUE.equals(
                videoDocumentController.deleteById(newParentId).getBody());

        assert isDeletedFromSearch;
    }

    @Test
    public void deleteManyByIdsTest() {
        VideoDocumentRequest[] documentsToInsert = {
                new VideoDocumentRequest(
                        "1", "родитель 1", "https://storage.yandexcloud.net/akhidov-ivr/16.5.mp4",
                        new String[] {"2", "3"}, new String[0], true,
                        "https://storage.yandexcloud.net/akhidov-ivr/16.5.svg"),
                new VideoDocumentRequest(
                        "2", "ребенок 1", "https://storage.yandexcloud.net/akhidov-ivr/16.6.mp4",
                        new String[0], new String[0], false,
                        "https://storage.yandexcloud.net/akhidov-ivr/16.6.svg"),
                new VideoDocumentRequest(
                        "3", "ребенок 2", "https://storage.yandexcloud.net/akhidov-ivr/16.4.mp4",
                        new String[0], new String[0], false,
                        "https://storage.yandexcloud.net/akhidov-ivr/16.4.svg")

        };

        Map<String, String> idmap = videoDocumentController.
                addVideoDocument(documentsToInsert).getBody();

        assert idmap != null;
        List<String> idsToDelete = idmap.values().stream().toList();
        Map<String, Boolean> result =
                videoDocumentController.deleteManyByIds(idsToDelete).getBody();

        assert Objects.requireNonNull(result).size() == 3;
        assert result.get(idsToDelete.get(0));
    }

    @Test
    public void addVideoDocumentTest() {
        VideoDocumentRequest[] documentsToInsert = {
                new VideoDocumentRequest(
                        "1", "родитель 1", "https://storage.yandexcloud.net/akhidov-ivr/16.5.mp4",
                        new String[] {"2"}, new String[0], true,
                        "https://storage.yandexcloud.net/akhidov-ivr/16.5.svg"),
                new VideoDocumentRequest(
                        "2", "ребенок 1", "https://storage.yandexcloud.net/akhidov-ivr/16.6.mp4",
                        new String[0], new String[0], false,
                        "https://storage.yandexcloud.net/akhidov-ivr/16.6.svg")
        };

        Map<String, String> idmap = videoDocumentController.
                addVideoDocument(documentsToInsert).getBody();

        assert Objects.requireNonNull(idmap).size() == 2;

        String newParentId = idmap.get("1");
        String newChildId = idmap.get("2");

        VideoDocumentDTO parentDocument = videoDocumentController.
                getVideoDocument(newParentId).getBody();

        List<String> ids = List.of(newParentId, newChildId);
        videoDocumentController.deleteManyByIds(ids); // удаляем из бд и поиска сразу

        assert parentDocument != null;
        assertEquals(parentDocument.getTextSimple(), "родитель 1");
    }

    @Test
    public void updateDocumentTest() {
        VideoDocumentRequest[] documentsToUpdate = {
                new VideoDocumentRequest(
                        "2", "ребенок 1", "https://storage.yandexcloud.net/akhidov-ivr/16.6.mp4",
                        new String[0], new String[0], false,
                        "https://storage.yandexcloud.net/akhidov-ivr/16.6.svg")
        };

        Map<String, String> idmap = videoDocumentController.addVideoDocument(documentsToUpdate).getBody();
        assert idmap != null;
        String newChildId = idmap.get("2");
        VideoDocumentDTO updatedDocument;

        updatedDocument = videoDocumentController.updateDocument(
                newChildId, new UpdateRequest(
                        "textSimple", "продажа бильярдных столов в МФЦ со скидкой для пенсионеров"))
                .getBody();

        assertEquals(videoDocumentController.getVideoDocument(newChildId).getBody(), updatedDocument);
        videoDocumentController.deleteById(newChildId);
    }
}
