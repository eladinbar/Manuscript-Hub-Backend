package com.manuscript.rest.api;

import com.manuscript.rest.request.AnnotationRequest;
import com.manuscript.rest.response.AnnotationResponse;
import com.manuscript.rest.service.IAnnotationService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/addAnnotation")
    public ResponseEntity<AnnotationResponse> addAnnotation(@RequestBody AnnotationRequest annotationRequest) {
        AnnotationResponse result = annotationService.create(annotationRequest);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/updateAnnotation")
    public ResponseEntity<AnnotationResponse> updateAnnotation(@RequestBody AnnotationRequest annotationRequest) {
        AnnotationResponse result = annotationService.update(annotationRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAnnotationsByDocumentId/{documentId}/{uid}")
    public ResponseEntity<List<AnnotationResponse>> getAllAnnotationsByDocumentId(@PathVariable UUID documentId, @PathVariable String uid) {
        List<AnnotationResponse> result = annotationService.getAllByImageId(documentId, uid);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping ("/deleteAnnotation/{annotationId}/{documentId}/{uid}")
    public void deleteAnnotation(@PathVariable UUID annotationId, @PathVariable UUID documentId, @PathVariable String uid) {
        annotationService.delete(annotationId, documentId, uid);
    }

    @DeleteMapping("/deleteAllAnnotationsByDocumentId")
    public void deleteAllAnnotationsByDocumentId(@RequestBody AnnotationRequest annotationRequest) {
        throw new RuntimeException("Unimplemented");
    }
}
