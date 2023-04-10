package com.manuscript.rest.api;

import com.manuscript.rest.request.AlgorithmRequest;
import com.manuscript.rest.response.AlgorithmResponse;
import com.manuscript.rest.service.IAlgorithmService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.manuscript.rest.common.Constants.RESOURCE_ALGORITHM;

@RestController
@RequestMapping(RESOURCE_ALGORITHM)
@CrossOrigin("*")
@AllArgsConstructor
public class AlgorithmController {
    private final IAlgorithmService algorithmService;

    @PostMapping("/runAlgorithm")
    public void runAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        algorithmService.run(algorithmRequest);
    }

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

    @GetMapping("/getAlgorithm/{algorithmId}")
    public ResponseEntity<AlgorithmResponse> getAlgorithm(@PathVariable UUID algorithmId) {
        AlgorithmResponse result = algorithmService.get(algorithmId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteAlgorithm")
    public void deleteAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        algorithmService.delete(algorithmRequest);
    }

    @DeleteMapping("/deleteAllByUserIdAlgorithm")
    public void deleteAllByUserIdAlgorithm(@RequestBody UUID userId) {
        algorithmService.deleteAllByUserId(userId);
    }
}
