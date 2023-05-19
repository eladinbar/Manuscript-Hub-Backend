package com.manuscript.rest.api;

import com.manuscript.rest.request.AlgorithmRequest;
import com.manuscript.rest.response.AlgorithmResponse;
import com.manuscript.rest.service.IAlgorithmService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> runAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        try {
            checkNotNull(algorithmRequest, false);
            algorithmService.run(algorithmRequest);
            return ResponseEntity.ok("Algorithm was executed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/uploadAlgorithm")
    public ResponseEntity<?> uploadAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        try {
            checkNotNull(algorithmRequest, true);
            AlgorithmResponse result = algorithmService.create(algorithmRequest);
            return ResponseEntity.ok(result);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PatchMapping("/updateAlgorithm")
    public ResponseEntity<?> updateAlgorithm(@RequestBody AlgorithmRequest algorithmRequest) {
        try {
            checkNotNull(algorithmRequest, false);
            AlgorithmResponse result = algorithmService.update(algorithmRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @GetMapping("/getAlgorithmById/{algorithmId}")
    public ResponseEntity<?> getAlgorithmById(@PathVariable UUID algorithmId) {
        try {
            if(algorithmId == null)
                throw new IllegalArgumentException("Invalid algorithm ID.");
            return ResponseEntity.ok(algorithmService.getById(algorithmId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @GetMapping("/getAlgorithmById/{url}")
    public ResponseEntity<?> getAlgorithmByUrl(@PathVariable String url) {
        try {
            if(url == null)
                throw new IllegalArgumentException("Invalid algorithm ID.");
            return ResponseEntity.ok(algorithmService.getByUrl(url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @GetMapping("/getAllAlgorithms")
    public ResponseEntity<?> getAllAlgorithms() {
        try {
            return ResponseEntity.ok(algorithmService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @DeleteMapping("/deleteAlgorithmById/{algorithmId}/{uid}")
    public ResponseEntity<?> deleteAlgorithmById(@PathVariable UUID algorithmId, @PathVariable String uid) {
        try {
            if(algorithmId == null || uid == null)
                throw new IllegalArgumentException("Invalid algorithm or user ID.");
            algorithmService.deleteById(algorithmId, uid);
            return ResponseEntity.ok("Algorithm was deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @DeleteMapping("/deleteAlgorithmByUrl/{url}/{uid}")
    public ResponseEntity<?> deleteAlgorithmByUrl(@PathVariable String url, @PathVariable String uid) {
        try {
            if(url == null || uid == null)
                throw new IllegalArgumentException("Invalid URL or user ID.");
            algorithmService.deleteByUrl(url, uid);
            return ResponseEntity.ok("Algorithm deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @DeleteMapping("/deleteAllByUserIdAlgorithm")
    public ResponseEntity<?> deleteAllByUserIdAlgorithm(@RequestBody UUID userId) {
        try {
            algorithmService.deleteAllByUserId(userId);
            return ResponseEntity.ok("All algorithms submitted by the user were deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    private void checkNotNull(AlgorithmRequest algorithmRequest, boolean newRequest) {
//        if(newRequest) {
            if(Stream.of(algorithmRequest.getUid(),
                    algorithmRequest.getTitle(), algorithmRequest.getDescription(),
                    algorithmRequest.getModelType(), algorithmRequest.getStatus(),
                    algorithmRequest.getUrl()).anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Algorithm request's fields must not be null.");
//        }
//        else if(Stream.of(algorithmRequest.getId(), algorithmRequest.getUid(),
//                algorithmRequest.getTitle(), algorithmRequest.getDescription(),
//                algorithmRequest.getModelType(), algorithmRequest.getStatus(),
//                algorithmRequest.getUrl()).anyMatch(Objects::isNull))
//            throw new IllegalArgumentException("Algorithm request's fields must not be null.");
    }
}
