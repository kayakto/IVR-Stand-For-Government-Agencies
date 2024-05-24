package ru.example.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IVRStandExceptionHandler {
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        System.out.println(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }

    // TODO add logger
}
