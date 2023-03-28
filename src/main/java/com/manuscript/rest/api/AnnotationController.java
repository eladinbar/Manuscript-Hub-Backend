package com.manuscript.rest.api;

import com.manuscript.rest.request.AnnotationRequest;
import com.manuscript.rest.response.AnnotationResponse;
import com.manuscript.rest.service.IAnnotationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        throw new RuntimeException("Unimplemented");
    }

    @GetMapping("/getAnnotationsByDocumentId")
    public ResponseEntity<List<AnnotationResponse>> getAllAnnotationsByDocumentId(@RequestBody AnnotationRequest annotationRequest) {
        throw new RuntimeException("Unimplemented");
    }

    @DeleteMapping ("/deleteAnnotation")
    public ResponseEntity<AnnotationResponse> deleteAnnotation(@RequestBody AnnotationRequest annotationRequest) {
        throw new RuntimeException("Unimplemented");
    }

    @DeleteMapping("/deleteAllAnnotationsByDocumentId")
    public ResponseEntity<List<AnnotationResponse>> deleteAllAnnotationsByDocumentId(@RequestBody AnnotationRequest annotationRequest) {
        throw new RuntimeException("Unimplemented");
    }
}
