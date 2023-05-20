package com.manuscript.rest.api;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.exceptions.FailedUploadException;
import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import com.manuscript.rest.service.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/uploadDocumentInfo")
    public ResponseEntity<ImageInfoResponse> uploadImageInfo(@RequestBody ImageInfoRequest imageInfoRequest) {
            checkRequestNotNull(imageInfoRequest, true);
            ImageInfoResponse imageInfoResponse = imageService.saveInfo(imageInfoRequest);
            if (imageInfoResponse == null)
                throw new FailedUploadException("Failed to upload image information.");
            return ResponseEntity.ok(imageInfoResponse);
    }

    @PostMapping("/uploadDocumentData/{imageInfoId}")
    public void uploadImageData(@RequestParam("file") List<MultipartFile> filesList, @PathVariable UUID imageInfoId) {
        if (filesList == null || filesList.isEmpty())
            throw new IllegalArgumentException("Invalid document data.");
        for (MultipartFile file : filesList) {
            checkLegalFile(file);
        }
        checkIdNotNull(imageInfoId);
        try {
            for (int index = 0; index < filesList.toArray().length; index++) {
                MultipartFile file = filesList.get(index);
                ImageDataRequest imageDataRequest = ImageDataRequest.builder()
                        .imageId(imageInfoId)
                        .data(file.getBytes())
                        .fileName(Objects.requireNonNull(file.getOriginalFilename()))
                        .index(index)
                        .build();
                imageService.saveData(imageDataRequest);
            }
        }
        catch (IOException ioException) {
            throw new FailedUploadException("Something went wrong trying to retrieve file data");
        }
    }

    @PatchMapping("/updateDocumentInfo")
    public ResponseEntity<ImageInfoResponse> updateImageInfo(@RequestBody ImageInfoRequest imageInfoRequest) {
        checkRequestNotNull(imageInfoRequest,false);
        ImageInfoResponse imageInfoResponse = imageService.updateInfo(imageInfoRequest);
        return ResponseEntity.ok(imageInfoResponse);
    }

    @GetMapping("/getDocumentInfoById/{imageInfoId}/{uid}")
    public ResponseEntity<ImageInfoResponse> getImageInfoById(@PathVariable UUID imageInfoId, @PathVariable String uid) {
        checkIdNotNull(imageInfoId);
        checkUserIdNotNull(uid);
        ImageInfoResponse result = imageService.getByIdInfo(imageInfoId, uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getDocumentDataById/{imageDataId}/{uid}")
    public ResponseEntity<byte[]> getImageDataById(@PathVariable UUID imageDataId, @PathVariable String uid) {
        checkIdNotNull(imageDataId);
        checkUserIdNotNull(uid);
        ImageDataResponse result = imageService.getByIdData(imageDataId, uid);
        return ResponseEntity.ok(result.getData());
    }

    @GetMapping("/getDocumentDatasByDocumentInfoId/{imageInfoId}/{uid}")
    public ResponseEntity<List<ImageDataResponse>> getImageDatasByImageInfoId(@PathVariable UUID imageInfoId, @PathVariable String uid) {
        checkIdNotNull(imageInfoId);
        checkUserIdNotNull(uid);
        List<ImageDataResponse> result = imageService.getAllByImageInfoIdImageDatas(imageInfoId, uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllDocumentInfosByUid/{uid}")
    public ResponseEntity<List<ImageInfoResponse>> getAllImageInfosByUid(@PathVariable String uid) {
        checkUserIdNotNull(uid);
        List<ImageInfoResponse> result = imageService.getAllByUidImageInfos(uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllPublicDocumentInfos")
    public ResponseEntity<List<ImageInfoResponse>> getAllPublicImageInfos() {
        List<ImageInfoResponse> result = imageService.getAllPublicImages();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllSharedDocumentInfosByUid/{uid}")
    public ResponseEntity<List<ImageInfoResponse>> getAllSharedImageInfosByUid(@PathVariable String uid) {
        checkUserIdNotNull(uid);
        List<ImageInfoResponse> result = imageService.getAllSharedImages(uid);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteDocumentInfoById/{imageInfoId}/{uid}")
    public ResponseEntity<String> deleteImageInfoById(@PathVariable UUID imageInfoId, @PathVariable String uid) {
        checkIdNotNull(imageInfoId);
        checkUserIdNotNull(uid);
        imageService.deleteByIdImageInfo(imageInfoId, uid);
        return ResponseEntity.ok("Document deleted successfully");
    }

    @DeleteMapping("/deleteDocumentDataById/{imageDataId}/{uid}/{deleteAnnotation}")
    public ResponseEntity<String> deleteImageDataById(@PathVariable UUID imageDataId, @PathVariable String uid, @PathVariable boolean deleteAnnotation) {
        checkIdNotNull(imageDataId);
        checkUserIdNotNull(uid);
        imageService.deleteByIdImageData(imageDataId, uid, deleteAnnotation);
        return ResponseEntity.ok("Image deleted successfully");
    }

    @PatchMapping("/transferOwnership/{newOwnerUid}")
    public ResponseEntity<ImageInfoResponse> transferOwnership(@RequestBody ImageInfoRequest imageInfoRequest, @PathVariable String newOwnerUid) {
        checkRequestNotNull(imageInfoRequest,false);
        ImageInfoResponse imageInfoResponse = imageService.transferOwnership(imageInfoRequest, newOwnerUid);
        return ResponseEntity.ok(imageInfoResponse);
    }

    @GetMapping("/getImageInfoByTextSearch/{searchText}/{uid}")
    public ResponseEntity<Map<Privacy, List<ImageInfoResponse>>> getImageInfoByTextSearch(@PathVariable String searchText, @PathVariable String uid) {
        checkSearchTextNoNull(uid);
        Map<Privacy, List<ImageInfoResponse>> result = imageService.getImageInfoByTextSearch(searchText,uid);
        return ResponseEntity.ok(result);
    }

    private void checkIdNotNull(UUID id) throws IllegalArgumentException{
        if (id == null)
            throw new IllegalArgumentException("Image ID can't be null.");
    }

    private void checkUserIdNotNull(String uid) throws IllegalArgumentException{
        if (uid == null)
            throw new IllegalArgumentException("User ID can't be null.");
    }

    private void checkSearchTextNoNull(String searchText) throws IllegalArgumentException{
        if (searchText == null)
            throw new IllegalArgumentException("Search text can't be null.");
    }

    private void checkRequestNotNull(ImageInfoRequest imageInfoRequest, boolean newRequest) throws IllegalArgumentException {
        if(newRequest) {
            if (Stream.of(imageInfoRequest.getUid(),
                    imageInfoRequest.getTitle(), imageInfoRequest.getStatus(), imageInfoRequest.getPrivacy()).anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Image request's fields must not be null.");
        }
        else if(Stream.of(imageInfoRequest.getId(),  imageInfoRequest.getUid(),
                imageInfoRequest.getTitle(), imageInfoRequest.getStatus(), imageInfoRequest.getPrivacy()).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Image request's fields must not be null.");
    }

    private void checkLegalFile(MultipartFile file) throws IllegalArgumentException{
        if (file.getSize() > 10L * 1024 * 1024){
            throw new IllegalArgumentException("File size over 10MB");
        }
        String fileType = file.getContentType();
        boolean isSupportedType = fileType.equals("image/png")
                || fileType.equals("image/jpg")
                || fileType.equals("image/jpeg")
                || fileType.equals("application/pdf");
        if (!isSupportedType){
            throw new IllegalArgumentException("unsupported file type");
        }
    }

    @ExceptionHandler(FailedUploadException.class)
    private ResponseEntity<String> handleFailedUploadException(FailedUploadException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
