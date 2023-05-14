package com.manuscript.rest.api;

import com.manuscript.core.exceptions.FailedUploadException;
import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.forms.request.ImageRequest;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.forms.response.ImageResponse;
import com.manuscript.rest.service.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import static com.manuscript.rest.common.Constants.RESOURCE_IMAGE;


@RestController
@RequestMapping(RESOURCE_IMAGE)
@CrossOrigin("*")
@AllArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/uploadDocumentMetadata")
    public ResponseEntity<ImageResponse> uploadDocumentMetadata(@RequestBody ImageRequest imageRequest)
                                            throws IllegalArgumentException, IOException, FailedUploadException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        checkRequestNotNull(imageRequest, true);
        ImageResponse imageResponse = imageService.save(imageRequest);
        if (imageResponse == null)
            throw new FailedUploadException("failed to upload image information.");
        return ResponseEntity.ok(imageResponse);
    }

    @PostMapping("/uploadDocumentData/{metadataId}")
    public void uploadDocumentData(@RequestParam("file") List<MultipartFile> filesList, @PathVariable UUID metadataId) throws IOException, IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        if (filesList == null || filesList.isEmpty())
            throw new IllegalArgumentException("Invalid document data.");
        checkIdNotNull(metadataId);
        for (int index = 0; index<filesList.toArray().length; index++) {
            MultipartFile file = filesList.get(index);
            ImageDataRequest imageDataRequest = ImageDataRequest.builder()
                    .imageId(metadataId)
                    .data(file.getBytes())
                    .fileName(Objects.requireNonNull(file.getOriginalFilename()))
                    .index(index)
                    .build();
            imageService.saveData(imageDataRequest);
        }
    }

    @PutMapping("/updateDocumentMetadata")
    public ResponseEntity<ImageResponse> updateDocumentMetadata(@RequestBody ImageRequest imageRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        checkRequestNotNull(imageRequest,false);
        ImageResponse imageResponse = imageService.update(imageRequest);
        return ResponseEntity.ok(imageResponse);
    }

    @GetMapping("/getDocumentMetadataById/{metadataId}/{uid}")
    public ResponseEntity<ImageResponse> getDocumentMetadataById(@PathVariable UUID metadataId, @PathVariable String uid) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        checkIdNotNull(metadataId);
        checkUserIdNotNull(uid);
        ImageResponse result = imageService.getById(metadataId, uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getDocumentDataById/{documentDataId}/{uid}")
    public ResponseEntity<List<ImageDataResponse>> getDocumentDataById(@PathVariable UUID documentDataId, @PathVariable String uid) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        checkIdNotNull(documentDataId);
        checkUserIdNotNull(uid);
        List<ImageDataResponse> result = imageService.getAllByIdData(documentDataId, uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllDocumentsMetadataByUid/{uid}")
    public ResponseEntity<List<ImageResponse>> getAllDocumentsMetadataByUid(@PathVariable String uid) throws IllegalArgumentException, NoUserFoundException{
        checkUserIdNotNull(uid);
        List<ImageResponse> result = imageService.getAllByUid(uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllPublicDocumentsMetadata")
    public ResponseEntity<List<ImageResponse>> getAllPublicDocumentsMetadata(){
        List<ImageResponse> result = imageService.getAllPublicImages();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllSharedDocumentsMetadataByUid/{uid}")
    public ResponseEntity<List<ImageResponse>> getAllSharedDocumentsMetadataByUid(@PathVariable String uid) throws IllegalArgumentException, NoUserFoundException  {
        checkUserIdNotNull(uid);
        List<ImageResponse> result = imageService.getAllSharedImages(uid);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteDocumentMetadataById/{metadataId}/{uid}")
    public void deleteDocumentMetadataById(@PathVariable UUID metadataId, @PathVariable String uid) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException{
        checkIdNotNull(metadataId);
        checkUserIdNotNull(uid);
        imageService.deleteById(metadataId, uid);
    }
    @DeleteMapping("/deleteDocumentDataById/{documentDataId}/{uid}")
    public void deleteDocumentDataById(@PathVariable UUID documentDataId, @PathVariable String uid) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException{
        checkIdNotNull(documentDataId);
        checkUserIdNotNull(uid);
        imageService.deleteDataById(documentDataId, uid);
    }

    private void checkIdNotNull(UUID id) throws IllegalArgumentException{
        if (id == null)
            throw new IllegalArgumentException("Image ID can't be null.");
    }

    private void checkUserIdNotNull(String uid) throws IllegalArgumentException{
        if (uid == null)
            throw new IllegalArgumentException("User ID can't be null.");
    }

    private void checkRequestNotNull(ImageRequest imageRequest, boolean newRequest) throws IllegalArgumentException{
        if(newRequest) {
            if (Stream.of(imageRequest.getUid(),
                    imageRequest.getTitle(), imageRequest.getStatus(), imageRequest.getPrivacy()).anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Image request's fields must not be null.");
        }
        else if(Stream.of(imageRequest.getId(),  imageRequest.getUid(),
                imageRequest.getTitle(), imageRequest.getStatus(), imageRequest.getPrivacy()).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Image request's fields must not be null.");
    }
}
