package ru.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.controller.dto.VideoDocumentDTO;
import ru.example.model.VideoDocument;
import ru.example.service.VideoDocumentService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/videoDoc")
@Tag(name = "Документы", description = "Методы для работы с документами")
@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK")})
public class VideoDocumentController {
    @Autowired
    private VideoDocumentService videoDocumentService;

    private final List<String> MainVideoDocumentsIds = List.of(new String[]{
            "6612ad7253c466832b383ae7",
            "6617e4ba6182526904884fab",
            "661815b06182526904884fd0",
            "661fdaf161a7adbdc4a02360"
    }); //TODO not all ids

    @GetMapping("/main")
    @Operation(summary = "Метод получения главных документов")
    public ResponseEntity<List<VideoDocumentDTO>> getMainDocuments(){
        return getVideoDocumentsByIds(MainVideoDocumentsIds);
    }

    @GetMapping("{id}")
    @Operation(summary = "Метод получения одного дочернего документа по идентификатору")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "application/json"))})
    public ResponseEntity<VideoDocumentDTO> getVideoDocument(
            @Parameter(description = "Уникальный идентификатор документа", example = "66141d446df3a0792d1c2fe7")
            @PathVariable("id") String id){
        VideoDocument document = videoDocumentService.findById(id);

        if (document == null)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(document.toVideoDocumentDTO());
    }

    /**
     * Получает все найденные документы из базы данных по идентификаторам
     * @param ids неопределенное количество идентификаторов, чьи записи нужно найти
     * @return список всех найденных документов в формате DTO
     */
    @GetMapping
    @Operation(summary = "Метод получения нескольких дочерних документов по идентификаторам")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "application/json"))})
    public ResponseEntity<List<VideoDocumentDTO>> getVideoDocumentsByIds(
            @Parameter(description = "Уникальные идентификаторы документов")
            @RequestParam
            List<String> ids) {
        List<VideoDocumentDTO> foundedDocuments = new ArrayList<>();

        for (String id: ids) {
            VideoDocument document = videoDocumentService.findById(id);
            if (document != null) {
                VideoDocumentDTO foundedDocument = document.toVideoDocumentDTO();
                foundedDocuments.add(foundedDocument);
            }
        }

        if (foundedDocuments.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(foundedDocuments);
    }

    @GetMapping("/search/{text}")
    @Operation(summary = "Метод поиска по тексту всех подходящих документов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "application/json")),
            })
    public ResponseEntity<List<VideoDocumentDTO>> searchVideoDocumentsByText(
            @Parameter(description = "Текст запроса, написанный на русском языке", example = "оформление загранпаспорта")
            @PathVariable("text")
            String text) {
        List<VideoDocumentDTO> result = new ArrayList<>();
        List<VideoDocument> founded = videoDocumentService.findByWords(text);

        if (founded.isEmpty())
            return ResponseEntity.noContent().build();

        for (VideoDocument document: founded) {
            result.add(document.toVideoDocumentDTO());
        }

        return ResponseEntity.ok(result);
    }
}
