package com.manuscript.rest.api;

import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;
import com.manuscript.rest.service.IImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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

    @PostMapping("/uploadInputDocument")
    public void uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
        ImageRequest imageRequest = ImageRequest.builder().data(file.getBytes()).status(Status.active).fileName(Objects.requireNonNull(file.getOriginalFilename())).build();
        imageService.save(imageRequest);
    }

    @PutMapping("/documents")
    public void updateDocument(@RequestBody ImageRequest imageRequest) {
        imageService.update(imageRequest);
    }

    @DeleteMapping("/deleteDocumentById/{id}")
    public void deleteDocumentById(@PathVariable UUID id) {
        imageService.deleteById(id);
    }

    @GetMapping("/getDocumentById/{id}")
    public ResponseEntity<byte[]> getDocumentById(@PathVariable UUID id) {
        ImageResponse result = imageService.getById(id);
        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/getAllDocuments")
    public ResponseEntity<List<ImageResponse>> getAllDocuments() {
        List<ImageResponse> result = imageService.getAll();
        return ResponseEntity.ok(result);
    }

}
