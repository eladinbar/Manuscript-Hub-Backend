package com.manuscript.rest.api;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
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
import java.util.stream.Stream;

import static com.manuscript.rest.common.Constants.RESOURCE_IMAGE;


@RestController
@RequestMapping(RESOURCE_IMAGE)
@CrossOrigin("*")
@AllArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/uploadDocument/{userId}")
    public ResponseEntity<ImageResponse> uploadDocument(@RequestParam("file") List<MultipartFile> filesList,
                                                        @PathVariable String userId,
                                                        @RequestParam("title") String title,
                                                        @RequestParam("author") String author,
                                                        @RequestParam("publicationDate") Date publicationDate,
                                                        @RequestParam("description") String description,
                                                        @RequestParam("tags") List<String> tags)
                                            throws IllegalArgumentException, IOException, FailedUploadException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        if (filesList == null || filesList.isEmpty())
            throw new IllegalArgumentException("Invalid document data.");
        checkUserIdNotNull(userId);
        ImageRequest imageRequest = ImageRequest.builder()
                .userId(userId)
                .title(title)
                .author(author)
                .publicationDate(publicationDate)
                .description(description)
                .tags((ArrayList<String>) tags)
                .status(Status.active)
                .privacy(Privacy.Private)
                .build();
        ImageResponse imageResponse = imageService.save(imageRequest);
        if (imageResponse == null)
            throw new FailedUploadException("failed to upload image information.");
        uploadDocumentData(filesList, imageResponse.getImageId());
        return ResponseEntity.ok(imageResponse);
    }

    private void uploadDocumentData(List<MultipartFile> filesList, UUID imageId) throws IOException, IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        checkImageIdNotNull(imageId);
        for (int index = 0; index<filesList.toArray().length; index++) {
            MultipartFile file = filesList.get(index);
            ImageDataRequest imageDataRequest = ImageDataRequest.builder()
                    .imageId(imageId)
                    .data(file.getBytes())
                    .fileName(Objects.requireNonNull(file.getOriginalFilename()))
                    .index(index)
                    .build();
            imageService.saveData(imageDataRequest);
        }
    }

    @PutMapping("/updateDocument")
    public ResponseEntity<ImageResponse> updateDocument(@RequestBody ImageRequest imageRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        checkNotNull(imageRequest,false);
        ImageResponse imageResponse = imageService.update(imageRequest);
        return ResponseEntity.ok(imageResponse);
    }

    @GetMapping("/getDocumentById/{imageId}/{userId}")
    public ResponseEntity<ImageResponse> getDocumentById(@PathVariable UUID imageId, @PathVariable String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        checkImageIdNotNull(imageId);
        checkUserIdNotNull(userId);
        ImageResponse result = imageService.getById(imageId, userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getDocumentData/{imageId}/{userId}")
    public ResponseEntity<List<ImageDataResponse>> getDocumentData(@PathVariable UUID imageId, @PathVariable String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        checkImageIdNotNull(imageId);
        checkUserIdNotNull(userId);
        List<ImageDataResponse> result = imageService.getAllByIdData(imageId, userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllDocumentsByUid/{userId}")
    public ResponseEntity<List<ImageResponse>> getAllDocumentsByUid(@PathVariable String userId) throws IllegalArgumentException, NoUserFoundException{
        checkUserIdNotNull(userId);
        List<ImageResponse> result = imageService.getAllByUid(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllPublicDocuments")
    public ResponseEntity<List<ImageResponse>> getAllPublicDocuments(){
        List<ImageResponse> result = imageService.getAllPublicImages();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllSharedDocuments/{userId}")
    public ResponseEntity<List<ImageResponse>> getAllSharedDocuments(@PathVariable String userId) throws IllegalArgumentException, NoUserFoundException  {
        checkUserIdNotNull(userId);
        List<ImageResponse> result = imageService.getAllSharedImages(userId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteDocumentById/{imageId}/{userId}")
    public void deleteDocumentById(@PathVariable UUID imageId, @PathVariable String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException{
        checkImageIdNotNull(imageId);
        checkUserIdNotNull(userId);
        imageService.deleteById(imageId, userId);
    }

    private void checkImageIdNotNull(UUID id) throws IllegalArgumentException{
        if (id == null)
            throw new IllegalArgumentException("Image ID can't be null.");
    }

    private void checkUserIdNotNull(String id) throws IllegalArgumentException{
        if (id == null)
            throw new IllegalArgumentException("User ID can't be null.");
    }

    private void checkNotNull(ImageRequest imageRequest, boolean newRequest) throws IllegalArgumentException{
        if(newRequest) {
            if (Stream.of(imageRequest.getUserId(),
                    imageRequest.getTitle(), imageRequest.getStatus(), imageRequest.getPrivacy()).anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Image request's fields must not be null.");
        }
        else if(Stream.of(imageRequest.getImageId(),  imageRequest.getUserId(),
                imageRequest.getTitle(), imageRequest.getStatus(), imageRequest.getPrivacy()).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Image request's fields must not be null.");
    }

}
