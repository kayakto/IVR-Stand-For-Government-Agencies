package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.controller.dto.TextDocumentDTO;
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

    @GetMapping("{id}")
    public VideoDocumentDTO getVideoDocument(@PathVariable("id") String id){
        Document document = documentService.findById(id);
        return new VideoDocumentDTO(document.getId(),
                document.getParent(), document.getTextSimple(), document.getTextRussian(),
                document.getVideoURL(), document.getChildren());
    }

    @GetMapping
    public List<VideoDocumentDTO> getVideoDocumentsByIds(@RequestParam List<String> ids) {
        List<VideoDocumentDTO> foundedDocuments = new ArrayList<>();
        for (String id: ids) {
            Document document = documentService.findById(id);
            if (document != null) {
                VideoDocumentDTO foundedDocument = new VideoDocumentDTO(document.getId(),
                        document.getParent(), document.getTextSimple(),
                        document.getTextRussian(), document.getVideoURL(),
                        document.getChildren());
                foundedDocuments.add(foundedDocument);
            }
        }
        return foundedDocuments;
    }
}
