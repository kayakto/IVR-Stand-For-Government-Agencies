package ru.pincode_dev.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.pincode_dev.controller.dto.VideoDocumentDTO;
import ru.pincode_dev.controller.request.UpdateRequest;
import ru.pincode_dev.controller.request.VideoDocumentRequest;
import ru.pincode_dev.model.VideoDocument;
import ru.pincode_dev.service.VideoDocumentService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/videoDoc")
@Tag(name = "Документы", description = "Методы для работы с документами")
@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "application/json"))})
public class VideoDocumentController {
    @Autowired
    private VideoDocumentService videoDocumentService;

    private final List<String> MainVideoDocumentsIds = List.of(new String[]{
            "6659c55ebbc8ff905fd7e61a",
            "6659d57e67313ea32ccbef4a"
    });

    @GetMapping("/main")
    @Operation(summary = "Метод получения главных документов")
    public ResponseEntity<List<VideoDocumentDTO>> getMainDocuments(){
        return getVideoDocumentsByIds(MainVideoDocumentsIds);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Метод получения одного дочернего документа по идентификатору")
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
    public ResponseEntity<List<VideoDocumentDTO>> getVideoDocumentsByIds(
            @Parameter(description = "Уникальные идентификаторы документов", example = "[\"6659d7e53e980cbfb95dcd54\", \"665b3c85ed647e57aae98c08\"]")
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
    @Operation(summary = "Метод удаления одного документа по его идентификатору")
    public ResponseEntity<Boolean> deleteById(
            @Parameter(description = "Идентификатор записи в базе данных", example = "6659d7e53e980cbfb95dcd54")
            @PathVariable("objectId")
            String objectId) {
        try {
            boolean message = videoDocumentService.deleteById(objectId);
            return ResponseEntity.ok(message);
        } catch (NullPointerException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/delete/ids")
    @Operation(summary = "Метод удаления нескольких документов по его идентификаторам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"6659cfddcad64ac69a381ebc\": true, \"6659cd67451edb9bf53a17f2\": false}")
                    )
            )
    })
    public ResponseEntity<Map<String, Boolean>> deleteManyByIds(
            @Parameter(description = "Идентификаторы записей в базе данных",
                    example = "[\"6659cfddcad64ac69a381ebc\", \"6659cd67451edb9bf53a17f2\"]")
            @RequestParam
            List<String> ids){
        Map<String, Boolean> results = new HashMap<>();

        for (String id: ids) {
            try {
                boolean message = videoDocumentService.deleteById(id);
                results.put(id, message);
            } catch (NullPointerException e) {
                continue;
            }
        }

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(results);
    }

    @PostMapping("/add")
    @Operation(summary = "Метод добавления одного или нескольких документов в базу данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"1\": \"66658c7be8f3f456cb5c3917\", \"2\": \"66658c7be8f7f456cb5c3918\"}")
                    )
            )
    })
    public ResponseEntity<Map<String, String>> addVideoDocument(@RequestBody VideoDocumentRequest[] requestedDocuments) {
        List<VideoDocument> documentsToInsert = new ArrayList<>();
        for (VideoDocumentRequest requestedDoc : requestedDocuments) {
            VideoDocument videoDocument = requestedDoc.toVideoDocument();
            documentsToInsert.add(videoDocument);
        }

        Map<String, String> idMap = videoDocumentService.insertDocuments(documentsToInsert);

        if (!idMap.isEmpty())
            return ResponseEntity.ok(idMap);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Метод изменения одного поля документа в базе данных")
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
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(updatedDocument.toVideoDocumentDTO());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
