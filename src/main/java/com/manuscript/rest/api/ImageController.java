package com.manuscript.rest.api;

import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;
import com.manuscript.rest.service.IImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.manuscript.rest.common.Constants.RESOURCE_IMAGE;


@RestController
@RequestMapping(RESOURCE_IMAGE)
@CrossOrigin("**")
public class ImageController {
    private final IImageService imageService;


    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/documents")
    public void uploadDocument(@RequestBody ImageRequest imageRequest) {
        imageService.save(imageRequest);
    }

    @PutMapping("/documents")
    public void updateDocument(@RequestBody ImageRequest imageRequest) {
        imageService.update(imageRequest);
    }

    @DeleteMapping("/documents/{id}")
    public void deleteDocumentById(@PathVariable UUID id) {
        imageService.deleteById(id);
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<ImageResponse> getDocumentById(@PathVariable UUID id) {
        ImageResponse result = imageService.getById(id);
        return ResponseEntity.ok(result);
    }

}
