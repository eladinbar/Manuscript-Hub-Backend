package com.manuscript.rest.api;

import com.manuscript.rest.forms.request.AlgorithmRequest;
import com.manuscript.rest.forms.response.AlgorithmResponse;
import com.manuscript.rest.service.IAlgorithmService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
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
    public void runAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        checkNotNull(algorithmRequest, false);
        algorithmService.run(algorithmRequest);
    }

    @PostMapping("/uploadAlgorithm")
    public ResponseEntity<AlgorithmResponse> uploadAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        checkNotNull(algorithmRequest, true);
        AlgorithmResponse result = algorithmService.create(algorithmRequest);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateAlgorithm")
    public ResponseEntity<AlgorithmResponse> updateAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        checkNotNull(algorithmRequest, false);
        AlgorithmResponse result = algorithmService.update(algorithmRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAlgorithm/{algorithmId}/{uid}")
    public ResponseEntity<AlgorithmResponse> getAlgorithm(@PathVariable UUID algorithmId, @PathVariable String uid) {
        if(algorithmId == null || uid == null)
            throw new IllegalArgumentException("Invalid algorithm user ID.");
        AlgorithmResponse result = algorithmService.getById(algorithmId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteAlgorithm/{algorithmId}/{uid}")
    public void deleteAlgorithm(@PathVariable UUID algorithmId, @PathVariable String uid) {
        if(algorithmId == null || uid == null)
            throw new IllegalArgumentException("Invalid algorithm or user ID.");
        algorithmService.delete(algorithmId, uid);
    }

    @DeleteMapping("/deleteAllByUserIdAlgorithm")
    public void deleteAllByUserIdAlgorithm(@RequestBody UUID userId) {
        algorithmService.deleteAllByUserId(userId);
    }

    private void checkNotNull(AlgorithmRequest algorithmRequest, boolean newRequest) {
        if(newRequest) {
            if(Stream.of(algorithmRequest.getUid(), algorithmRequest.getImageId(),
                    algorithmRequest.getUrl()).anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Algorithm request's fields must not be null.");
        }
        else if(Stream.of(algorithmRequest.getId(), algorithmRequest.getUid(), algorithmRequest.getImageId(),
                        algorithmRequest.getUrl()).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Algorithm request's fields must not be null.");
    }
}
