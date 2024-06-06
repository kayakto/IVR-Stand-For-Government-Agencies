package ru.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.example.controller.dto.VideoDocumentDTO;
import ru.example.controller.request.UpdateRequest;
import ru.example.controller.request.VideoDocumentRequest;
import ru.example.model.VideoDocument;
import ru.example.service.VideoDocumentService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/videoDoc")
@Tag(name = "Документы", description = "Методы для работы с документами")
@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK")})
public class VideoDocumentController {
    @Autowired
    private VideoDocumentService videoDocumentService;

    private final List<String> MainVideoDocumentsIds = List.of(new String[]{
            "6659c55ebbc8ff905fd7e61a",
            "6659d57e67313ea32ccbef4a"
    }); //TODO not all ids

    @GetMapping("/main")
    @Operation(summary = "Метод получения главных документов")
    public ResponseEntity<List<VideoDocumentDTO>> getMainDocuments(){
        return getVideoDocumentsByIds(MainVideoDocumentsIds);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Метод получения одного дочернего документа по идентификатору")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "application/json"))})
    public ResponseEntity<VideoDocumentDTO> getVideoDocument(
            @Parameter(description = "Уникальный идентификатор документа", example = "6659d7e53e980cbfb95dcd54")
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
    @GetMapping("/ids")
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
        List<String> foundedIds = videoDocumentService.findIdsByWords(text);

        if (foundedIds.isEmpty())
            return ResponseEntity.noContent().build();

        for (String id : foundedIds){
            VideoDocument foundedDocument = videoDocumentService.findById(id);
            result.add(foundedDocument.toVideoDocumentDTO());
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{objectId}")
    @Operation(summary = "Метод удаления документа по его идентификатору")
    public ResponseEntity<Boolean> deleteById(
            @Parameter(description = "Идентификатор записи в базе данных", example = "6659d7e53e980cbfb95dcd54")
            @PathVariable("objectId")
            String objectId) {
        boolean message = videoDocumentService.deleteById(objectId);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/add")
    @Operation(summary = "Метод добавления документа в базу данных")
    public ResponseEntity<Map<String, String>> addVideoDocument(@RequestBody VideoDocumentRequest[] requestedDocuments) {
        List<VideoDocument> documentsToInsert = new ArrayList<VideoDocument>();
        for (VideoDocumentRequest requestedDoc : requestedDocuments) {
            VideoDocument videoDocument = requestedDoc.toVideoDocument();
            documentsToInsert.add(videoDocument);
        }

        Map<String, String> idMap = videoDocumentService.insertDocuments(documentsToInsert); // Todo hashmap or map or dictionary
        if (!idMap.isEmpty())
            return ResponseEntity.ok(idMap);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Метод обновления одного поля документа в базе данных")
    public ResponseEntity<VideoDocumentDTO> updateDocument(
            @PathVariable
            @Parameter(description = "Идентификатор записи в базе данных", example = "6659d7e53e980cbfb95dcd54")
            String id,
            @RequestBody UpdateRequest updateRequest) {
        String fieldName = updateRequest.getFieldName();
        Object newValue = updateRequest.getNewValue();
        try {
            VideoDocument updatedDocument = videoDocumentService.updateFieldById(id, fieldName, newValue);
            if (updatedDocument == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedDocument.toVideoDocumentDTO());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
