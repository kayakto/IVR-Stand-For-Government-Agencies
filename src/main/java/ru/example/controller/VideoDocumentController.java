package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.controller.dto.VideoDocumentDTO;
import ru.example.model.Document;
import ru.example.service.DocumentService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/videoDoc")
public class VideoDocumentController {
    @Autowired
    private DocumentService documentService;

    private final List<String> MainVideoDocumentsIds = List.of(new String[]{
            "6612ad7253c466832b383ae7",
            "6617e4ba6182526904884fab",
            "661815b06182526904884fd0",
            "661fdaf161a7adbdc4a02360"
    }); //TODO not all ids

    @GetMapping("{id}")
    public VideoDocumentDTO getVideoDocument(@PathVariable("id") String id){
        Document document = documentService.findById(id);
        return new VideoDocumentDTO(document.getId(), document.getTextSimple(),
                document.getVideoURL(), document.getChildren(),
                document.getInfoChildren());
    }

    @GetMapping
    public List<VideoDocumentDTO> getVideoDocumentsByIds(@RequestParam List<String> ids) {
        List<VideoDocumentDTO> foundedDocuments = new ArrayList<>();
        for (String id: ids) {
            Document document = documentService.findById(id);
            if (document != null) {
                VideoDocumentDTO foundedDocument = new VideoDocumentDTO(
                        document.getId(), document.getTextSimple(),
                        document.getVideoURL(), document.getChildren(),
                        document.getInfoChildren());
                foundedDocuments.add(foundedDocument);
            }
        }
        return foundedDocuments;
    }

    @GetMapping("/main")
    public List<VideoDocumentDTO> getMainDocuments() {
        return getVideoDocumentsByIds(MainVideoDocumentsIds);
    }

    @GetMapping("/search/{text}")
    public List<VideoDocumentDTO> searchVideoDocumentsByText(@PathVariable("text") String text) {
        List<VideoDocumentDTO> result = new ArrayList<>();
        List<Document> founded = documentService.findByWords(text);
        for (Document document: founded) {
            result.add(new VideoDocumentDTO(document.getId(), document.getTextSimple(),
                    document.getVideoURL(), document.getChildren(), document.getInfoChildren()));
        }
        return result;
    }
}
