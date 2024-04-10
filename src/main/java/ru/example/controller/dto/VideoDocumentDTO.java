package ru.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
public class VideoDocumentDTO { // maybe record???
    private final String id;
    private final String parent;
    private final String textSimple;
    private final String textRussian;
    private final String videoURL;
    private final String[] children;
}
