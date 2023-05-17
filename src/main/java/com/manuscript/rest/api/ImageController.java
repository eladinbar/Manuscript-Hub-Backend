package com.manuscript.rest.api;

import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;
import com.manuscript.rest.service.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static com.manuscript.rest.common.Constants.RESOURCE_IMAGE;


@RestController
@RequestMapping(RESOURCE_IMAGE)
@CrossOrigin("*")
@AllArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/uploadInputDocument/{uid}")
    public ResponseEntity<ImageResponse> uploadDocument(@RequestParam("file") MultipartFile file, @PathVariable String uid) throws IllegalArgumentException, IOException{
        if (file == null)
            throw new IllegalArgumentException("Invalid document data");
        if (uid == null)
            throw new IllegalArgumentException("User ID can't be null.");
        ImageRequest imageRequest = ImageRequest.builder().uid(uid).data(file.getBytes()).status(Status.Enabled).fileName(Objects.requireNonNull(file.getOriginalFilename())).build();
        ImageResponse imageResponse = imageService.save(imageRequest);
        return ResponseEntity.ok(imageResponse);
    }

    @PutMapping("/documents")
    public ResponseEntity<ImageResponse> updateDocument(@RequestBody ImageRequest imageRequest) throws IllegalArgumentException{
        checkNotNull(imageRequest,false);
        ImageResponse imageResponse = imageService.update(imageRequest);
        return ResponseEntity.ok(imageResponse);
    }

    @DeleteMapping("/deleteDocumentById/{id}")
    public void deleteDocumentById(@PathVariable UUID id) throws IllegalArgumentException{
        if (id == null)
            throw new IllegalArgumentException("User ID can't be null.");
        imageService.deleteById(id);
    }

    @GetMapping("/getDocumentById/{id}")
    //TODO change to return response entity
    public ResponseEntity<byte[]> getDocumentById(@PathVariable UUID id) throws IllegalArgumentException{
        if (id == null)
            throw new IllegalArgumentException("User ID can't be null.");
        ImageResponse result = imageService.getById(id);
        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/getAllDocumentsByUid/{uid}")
    public ResponseEntity<List<ImageResponse>> getAllDocumentsByUid(@PathVariable String uid) throws IllegalArgumentException{
        if (uid == null)
            throw new IllegalArgumentException("User ID can't be null.");
        List<ImageResponse> result = imageService.getAllByUid(uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllPublicImages")
    public ResponseEntity<List<ImageResponse>> getAllPublicImages(){
        List<ImageResponse> result = imageService.getAllPublicImages();
        return ResponseEntity.ok(result);
    }

    private void checkNotNull(ImageRequest imageRequest, boolean newRequest) throws IllegalArgumentException{
        if(newRequest) {
            if (Stream.of(imageRequest.getUid(),
                    imageRequest.getFileName(), imageRequest.getData()).anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Image request's fields must not be null.");
        }
        else if(Stream.of(imageRequest.getDocumentId(),  imageRequest.getUid(),
                imageRequest.getFileName(), imageRequest.getData()).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Image request's fields must not be null.");
    }

}
