package com.manuscript.rest.api;

import com.manuscript.rest.forms.request.AlgorithmRequest;
import com.manuscript.rest.forms.request.AnnotationRequest;
import com.manuscript.rest.forms.response.AlgorithmResponse;
import com.manuscript.core.domain.common.enums.AlgorithmStatus;
import com.manuscript.rest.forms.response.AnnotationResponse;
import com.manuscript.rest.service.IAlgorithmService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static com.manuscript.rest.common.Constants.RESOURCE_ALGORITHM;

@RestController
@RequestMapping(RESOURCE_ALGORITHM)
@CrossOrigin("*")
@AllArgsConstructor
public class AlgorithmController {
    private final IAlgorithmService algorithmService;

    @PostMapping("/runAlgorithm")
    public ResponseEntity<String> runAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) throws Exception{
        checkNotNull(algorithmRequest);
        algorithmService.run(algorithmRequest);
        return ResponseEntity.ok("Algorithm was executed successfully.");
    }

    @PostMapping("/uploadAlgorithm")
    public ResponseEntity<AlgorithmResponse> uploadAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        checkNotNull(algorithmRequest);
        AlgorithmResponse result = algorithmService.create(algorithmRequest);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/updateAlgorithm")
    public ResponseEntity<AlgorithmResponse> updateAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        checkNotNull(algorithmRequest);
        AlgorithmResponse result = algorithmService.update(algorithmRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAlgorithmById/{algorithmId}")
    public ResponseEntity<AlgorithmResponse> getAlgorithmById(@PathVariable UUID algorithmId) {
        if(algorithmId == null)
            throw new IllegalArgumentException("Invalid algorithm ID.");
        return ResponseEntity.ok(algorithmService.getById(algorithmId));
    }

    @GetMapping("/getAlgorithmById/{url}")
    public ResponseEntity<AlgorithmResponse> getAlgorithmByUrl(@PathVariable String url) {
        if(url == null)
            throw new IllegalArgumentException("Invalid algorithm ID.");
        return ResponseEntity.ok(algorithmService.getByUrl(url));
    }

    @GetMapping("/getAllAlgorithmsByUid/{uid}")
    public ResponseEntity<List<AlgorithmResponse>> getAllAlgorithmsByUid(@PathVariable String uid) {
        if(uid == null)
            throw new IllegalArgumentException("Invalid user ID.");
        return ResponseEntity.ok(algorithmService.getAllByUid(uid));
    }

    @GetMapping("/getAllRunnableAlgorithms/{uid}")
    public ResponseEntity<List<AlgorithmResponse>> getAllRunnableAlgorithms(@PathVariable String uid) {
        if(uid == null)
            throw new IllegalArgumentException("Invalid user ID.");
        return ResponseEntity.ok(algorithmService.getAllRunnable(uid));
    }

    @GetMapping("/getAllAlgorithmsByUid/{algorithmStatuses}/{uid}")
    public ResponseEntity<List<AlgorithmResponse>> getAllAlgorithmsByStatus(@PathVariable Set<AlgorithmStatus> algorithmStatuses, @PathVariable String uid) {
        if(algorithmStatuses.size() == 0)
            throw new IllegalArgumentException("Invalid algorithm status.");
        return ResponseEntity.ok(algorithmService.getAllByAlgorithmStatuses(algorithmStatuses, uid));
    }

    @GetMapping("/getAllAlgorithms/{uid}")
    public ResponseEntity<List<AlgorithmResponse>> getAllAlgorithms(@PathVariable String uid) {
        if(uid == null)
            throw new IllegalArgumentException("Invalid user ID.");
        return ResponseEntity.ok(algorithmService.getAll(uid));
    }

    @DeleteMapping("/deleteAlgorithmById/{algorithmId}/{uid}")
    public ResponseEntity<String> deleteAlgorithmById(@PathVariable UUID algorithmId, @PathVariable String uid) {
        if(algorithmId == null || uid == null)
            throw new IllegalArgumentException("Invalid algorithm or user ID.");
        algorithmService.deleteById(algorithmId, uid);
        return ResponseEntity.ok("Algorithm was deleted successfully.");
    }

    @DeleteMapping("/deleteAlgorithmByUrl/{url}/{uid}")
    public ResponseEntity<String> deleteAlgorithmByUrl(@PathVariable String url, @PathVariable String uid) {
        if(url == null || uid == null)
            throw new IllegalArgumentException("Invalid URL or user ID.");
        algorithmService.deleteByUrl(url, uid);
        return ResponseEntity.ok("Algorithm deleted successfully.");
    }

    @DeleteMapping("/deleteAllByUidAlgorithms/{uid}/{adminUid}")
    public ResponseEntity<String> deleteAllByUidAlgorithms(@PathVariable String uid, @PathVariable String adminUid) {
        algorithmService.deleteAllByUid(uid, adminUid);
        return ResponseEntity.ok("All algorithms submitted by the user were deleted.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    private void checkNotNull(AlgorithmRequest algorithmRequest) {
        if(Stream.of(algorithmRequest.getUid(),
                algorithmRequest.getTitle(), algorithmRequest.getDescription(),
                algorithmRequest.getModelType(), algorithmRequest.getStatus(),
                algorithmRequest.getUrl()).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Algorithm request's fields must not be null.");
    }

    /**Annotation section**/
    @PostMapping("/addManualAnnotation")
    public ResponseEntity<AnnotationResponse> addManualAnnotation(@RequestBody AnnotationRequest annotationRequest) {
        checkNotNull(annotationRequest, true);
        checkCoordinates(annotationRequest.getStartX(), annotationRequest.getStartY(),
                annotationRequest.getEndX(), annotationRequest.getEndY());
        AnnotationResponse result = algorithmService.createAnnotation(annotationRequest);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/updateAnnotation")
    public ResponseEntity<AnnotationResponse> updateAnnotation(@RequestBody AnnotationRequest annotationRequest) {
        checkNotNull(annotationRequest, false);
        checkCoordinates(annotationRequest.getStartX(), annotationRequest.getStartY(),
                annotationRequest.getEndX(), annotationRequest.getEndY());
        AnnotationResponse result = algorithmService.updateAnnotation(annotationRequest);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping ("/deleteAnnotationById/{annotationId}/{uid}/{imageDataId}")
    public ResponseEntity<String> deleteAnnotationById(@PathVariable UUID annotationId, @PathVariable String uid, @PathVariable UUID imageDataId) {
        if(annotationId == null)
            throw new IllegalArgumentException("Invalid annotation, document or user ID.");
        algorithmService.deleteByIdAnnotation(annotationId, uid, imageDataId);
        return ResponseEntity.ok("Annotation deleted successfully.");
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
            throw new IllegalArgumentException("Annotation's coordinates must be non-negative.");
    }
}
