package ru.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.controller.dto.VideoDocumentDTO;
import ru.example.model.VideoDocument;
import ru.example.service.VideoService;

@RestController
@RequestMapping(path = "/api/videoDoc")
public class VideoDocumentController {
    @Autowired
    private VideoService videoService;

    @GetMapping("{id}")
    public VideoDocumentDTO getVideoDocument(@PathVariable("id") String id){
        VideoDocument document = videoService.findById(id);
        return new VideoDocumentDTO(document.getId(),
                document.getParent(), document.getTextSimple(), document.getTextRussian(),
                document.getVideoURL(), document.getChildren());
    }
}
