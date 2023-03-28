package com.manuscript.rest.api;

import com.manuscript.rest.request.AlgorithmRequest;
import com.manuscript.rest.response.AlgorithmResponse;
import com.manuscript.rest.service.IAlgorithmService;
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
public class AlgorithmController {
    private final IAlgorithmService algorithmService;

    @PostMapping("/uploadAlgorithm")
    public ResponseEntity<AlgorithmResponse> uploadAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        AlgorithmResponse result = algorithmService.create(algorithmRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateAlgorithm")
    public ResponseEntity<AlgorithmResponse> updateAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        AlgorithmResponse result = algorithmService.update(algorithmRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAlgorithm")
    public ResponseEntity<AlgorithmResponse> getAlgorithm(@PathVariable UUID algorithmId) {
        AlgorithmResponse result = algorithmService.get(algorithmId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteAlgorithm")
    public ResponseEntity<AlgorithmResponse> deleteAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        AlgorithmResponse result = algorithmService.delete(algorithmRequest);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteAllByUserIdAlgorithm")
    public ResponseEntity<List<AlgorithmResponse>> deleteAllByUserIdAlgorithm(@RequestBody UUID userId) {
        List<AlgorithmResponse> result = algorithmService.deleteAllByUserId(userId);
        return ResponseEntity.ok(result);
    }
}
