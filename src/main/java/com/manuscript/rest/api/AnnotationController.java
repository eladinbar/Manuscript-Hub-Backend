package com.manuscript.rest.api;

import com.manuscript.rest.forms.request.AnnotationRequest;
import com.manuscript.rest.forms.response.AnnotationResponse;
import com.manuscript.rest.service.IAnnotationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static com.manuscript.rest.common.Constants.RESOURCE_ANNOTATION;

@RestController
@RequestMapping(RESOURCE_ANNOTATION)
@CrossOrigin("*")
@AllArgsConstructor
public class AnnotationController {
    private final IAnnotationService annotationService;

    @PostMapping("/addAnnotation")
    public ResponseEntity<AnnotationResponse> addAnnotation(@RequestBody AnnotationRequest annotationRequest) {
        checkNotNull(annotationRequest, true);
        checkCoordinates(annotationRequest.getStartX(), annotationRequest.getStartY(),
                annotationRequest.getEndX(), annotationRequest.getEndY());
        AnnotationResponse result = annotationService.create(annotationRequest);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/updateAnnotation")
    public ResponseEntity<AnnotationResponse> updateAnnotation(@RequestBody AnnotationRequest annotationRequest) {
        checkNotNull(annotationRequest, false);
        checkCoordinates(annotationRequest.getStartX(), annotationRequest.getStartY(),
                annotationRequest.getEndX(), annotationRequest.getEndY());
        AnnotationResponse result = annotationService.update(annotationRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllAnnotationsByDocumentId/{documentId}/{uid}")
    public ResponseEntity<List<AnnotationResponse>> getAllAnnotationsByDocumentId(@PathVariable UUID documentId, @PathVariable String uid) {
        if(documentId == null || uid == null)
            throw new IllegalArgumentException("Invalid document or user ID.");
        List<AnnotationResponse> result = annotationService.getAllByImageDataId(documentId, uid);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping ("/deleteAnnotation/{annotationId}")
    public ResponseEntity<String> deleteAnnotation(@PathVariable UUID annotationId) {
        if(annotationId == null)
            throw new IllegalArgumentException("Invalid annotation, document or user ID.");
        annotationService.delete(annotationId);
        return ResponseEntity.ok("Annotation deleted successfully");
    }

    @DeleteMapping("/deleteAllAnnotationsByDocumentId/{imageDataId}")
    public ResponseEntity<String> deleteAllAnnotationsByImageDataId(@PathVariable UUID imageDataId) {
        annotationService.deleteAllByImageDataId(imageDataId);
        return ResponseEntity.ok("All related annotations deleted successfully");
    }

    private void checkNotNull(AnnotationRequest annotationRequest, boolean newRequest) {
        if(newRequest) {
            if (Stream.of(annotationRequest.getUid(),  annotationRequest.getImageDataId(),
                    annotationRequest.getAlgorithmId(), annotationRequest.getContent()).anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Annotation request's fields must not be null.");
        }

        else if(Stream.of(annotationRequest.getId(), annotationRequest.getUid(), annotationRequest.getImageDataId(),
                        annotationRequest.getAlgorithmId(), annotationRequest.getContent()).anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Annotation request's fields must not be null.");
    }

    private void checkCoordinates(int startX, int startY, int endX, int endY) {
        if(startX < 0 || startY < 0 || endX < 0 || endY < 0)
            throw new IllegalArgumentException("Annotation's coordinates must be non-negative");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
