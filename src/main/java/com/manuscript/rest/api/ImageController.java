package com.manuscript.rest.api;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.exceptions.FailedUploadException;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

    @PostMapping("/uploadDocumentInfo")
    public ResponseEntity<ImageInfoResponse> uploadImageInfo(@RequestBody ImageInfoRequest imageInfoRequest) {
        checkRequestNotNull(imageInfoRequest, true);
        ImageInfoResponse imageInfoResponse = imageService.saveInfo(imageInfoRequest);
        if (imageInfoResponse == null)
            throw new FailedUploadException("Failed to upload document information.");
        return ResponseEntity.ok(imageInfoResponse);
    }

    @PostMapping("/uploadDocumentData/{imageInfoId}/{uid}")
    public ResponseEntity<String> uploadImageData(@RequestParam("file") List<MultipartFile> filesList, @PathVariable UUID imageInfoId, @PathVariable String uid) {
        if (filesList == null || filesList.isEmpty())
            throw new IllegalArgumentException("Invalid document data.");
        checkIdNotNull(imageInfoId);
        boolean succeededSavingData = false;
        try {
            List<MultipartFile> correctFilesList = checkAndFixFilesList(filesList);
            List<ImageDataRequest> imageDataRequestList = new ArrayList<>();
            for (int index = 0; index < correctFilesList.toArray().length; index++) {
                MultipartFile file = correctFilesList.get(index);
                ImageDataRequest imageDataRequest = ImageDataRequest.builder()
                        .imageId(imageInfoId)
                        .data(file.getBytes())
                        .fileName(Objects.requireNonNull(file.getOriginalFilename()))
                        .index(index)
                        .build();
                imageDataRequestList.add(imageDataRequest);
            }
            for (ImageDataRequest imageDataRequest : imageDataRequestList) {
                imageService.saveData(imageDataRequest);
                succeededSavingData = true;
            }
        } catch (IOException ioException) {
            throw new FailedUploadException("Something went wrong when trying to retrieve file data.");
        } catch (Exception exception) {
            if (!succeededSavingData) {
                deleteImageInfoById(imageInfoId, uid);
            }
            throw exception;
        }
        return ResponseEntity.ok("The document was uploaded to the system successfully.");
    }

    @PatchMapping("/updateDocumentInfo")
    public ResponseEntity<ImageInfoResponse> updateImageInfo(@RequestBody ImageInfoRequest imageInfoRequest) {
        checkRequestNotNull(imageInfoRequest, false);
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
        return ResponseEntity.ok("Document info deleted successfully.");
    }

    @DeleteMapping("/deleteDocumentDataById/{imageDataId}/{uid}/{deleteAnnotation}")
    public ResponseEntity<String> deleteImageDataById(@PathVariable UUID imageDataId, @PathVariable String uid, @PathVariable boolean deleteAnnotation) {
        checkIdNotNull(imageDataId);
        checkUserIdNotNull(uid);
        imageService.deleteByIdImageData(imageDataId, uid, deleteAnnotation);
        return ResponseEntity.ok("Document data deleted successfully.");
    }

    @PatchMapping("/transferOwnership/{newOwnerUid}")
    public ResponseEntity<ImageInfoResponse> transferOwnership(@RequestBody ImageInfoRequest imageInfoRequest, @PathVariable String newOwnerUid) {
        checkRequestNotNull(imageInfoRequest, false);
        ImageInfoResponse imageInfoResponse = imageService.transferOwnership(imageInfoRequest, newOwnerUid);
        return ResponseEntity.ok(imageInfoResponse);
    }

    @GetMapping("/getImageInfoByTextSearch/{searchText}/{uid}")
    public ResponseEntity<Map<Privacy, List<ImageInfoResponse>>> getImageInfoByTextSearch(@PathVariable String searchText, @PathVariable String uid) {
        checkSearchTextNoNull(uid);
        Map<Privacy, List<ImageInfoResponse>> result = imageService.getImageInfoByTextSearch(searchText, uid);
        return ResponseEntity.ok(result);
    }

    private void checkIdNotNull(UUID id) throws IllegalArgumentException {
        if (id == null)
            throw new IllegalArgumentException("Document ID can't be null.");
    }

    private void checkUserIdNotNull(String uid) throws IllegalArgumentException {
        if (uid == null)
            throw new IllegalArgumentException("Document ID can't be null.");
    }

    private void checkSearchTextNoNull(String searchText) throws IllegalArgumentException {
        if (searchText == null)
            throw new IllegalArgumentException("Search text can't be null.");
    }

    private void checkRequestNotNull(ImageInfoRequest imageInfoRequest, boolean newRequest) throws IllegalArgumentException {
        if (newRequest) {
            if (Stream.of(imageInfoRequest.getUid(),
                    imageInfoRequest.getTitle(), imageInfoRequest.getStatus(), imageInfoRequest.getPrivacy()).anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Document request's fields must not be null.");
        } else if (Stream.of(imageInfoRequest.getId(), imageInfoRequest.getUid(),
                imageInfoRequest.getTitle(), imageInfoRequest.getStatus(), imageInfoRequest.getPrivacy()).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Document request's fields must not be null.");
    }

    private List<MultipartFile> checkAndFixFilesList(List<MultipartFile> filesList) throws NullPointerException, IOException {
        List<MultipartFile> adjustedMultipartFileList = new ArrayList<>();
        for (MultipartFile file : filesList) {
            String fileType = file.getContentType();
            if (fileType == null) {
                throw new NullPointerException("No file type detected.");
            }
            fileType = fileType.toLowerCase();
            checkFileIsLegal(fileType, file.getSize());
            if (fileType.equals("application/pdf")) {
                //TODO fix pdf to images function
                //adjustedMultipartFileList.addAll(convertIfPdfToPngFiles(file));
                adjustedMultipartFileList.add(file);
            } else {
                adjustedMultipartFileList.add(file);
            }
        }
        return adjustedMultipartFileList;
    }

    private void checkFileIsLegal(String fileType, Long fileSize) throws IllegalArgumentException {
        if (fileSize > 10L * 1024 * 1024) {
            throw new IllegalArgumentException("File size is over the 10MB threshold.");
        }
        boolean isSupportedType = fileType.equals("image/png")
                || fileType.equals("image/jpg")
                || fileType.equals("image/jpeg")
                || fileType.equals("application/pdf");
        if (!isSupportedType) {
            throw new IllegalArgumentException("Unsupported file type, expected image or pdf file.");
        }
    }

    //TODO: fix function to convert pdf to list of images saved as MultipartFile
    private List<MultipartFile> convertIfPdfToPngFiles(MultipartFile file) throws IOException {
        List<MultipartFile> images = new ArrayList<>();

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream pageBytes = new ByteArrayOutputStream();
        ImageIO.write(image, "png", pageBytes);
        pageBytes.flush();
        byte[] imageBytes = pageBytes.toByteArray();
        pageBytes.close();

        return images;
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
