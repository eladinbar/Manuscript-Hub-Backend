package com.manuscript.rest.api;

import com.manuscript.rest.forms.response.AnnotationResponse;
import com.manuscript.rest.service.IAnnotationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.manuscript.rest.common.Constants.RESOURCE_ANNOTATION;

@RestController
@RequestMapping(RESOURCE_ANNOTATION)
@CrossOrigin("*")
@AllArgsConstructor
public class AnnotationController {
    private final IAnnotationService annotationService;

    @GetMapping("/getAllAnnotationsByDocumentDataId/{imageDataId}/{uid}")
    public ResponseEntity<List<AnnotationResponse>> getAllAnnotationsByImageDataId(@PathVariable UUID imageDataId, @PathVariable String uid) {
        if(imageDataId == null || uid == null)
            throw new IllegalArgumentException("Invalid document or user ID.");
        List<AnnotationResponse> result = annotationService.getAllByImageDataId(imageDataId, uid);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteAllAnnotationsByDocumentDataId/{imageDataId}")
    public ResponseEntity<String> deleteAllAnnotationsByImageDataId(@PathVariable UUID imageDataId) {
        if(imageDataId == null)
            throw new IllegalArgumentException("Invalid image data ID.");
        annotationService.deleteAllByImageDataId(imageDataId);
        return ResponseEntity.ok("All related annotations were deleted successfully.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
