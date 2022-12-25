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
    private final IImageService documentService;


    public ImageController(IImageService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/documents")
    public void uploadDocument(@RequestBody ImageRequest document) {
        documentService.save(document);
    }

    @PutMapping("/documents")
    public void updateDocument(@RequestBody ImageRequest document) {
        documentService.update(document);
    }

    @DeleteMapping("/documents/{id}")
    public void deleteDocumentById(@PathVariable UUID id) {
        documentService.deleteById(id);
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<ImageResponse> getDocumentById(@PathVariable UUID id) {
        ImageResponse result = documentService.getById(id);
        return ResponseEntity.ok(result);
    }

}
