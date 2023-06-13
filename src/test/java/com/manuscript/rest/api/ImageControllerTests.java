package com.manuscript.rest.api;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import com.manuscript.rest.service.IImageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageControllerTests {
    //test classes
    private IImageService imageService;
    private ImageController imageController;
    private ImageInfoRequest imageInfoRequest;
    private ImageInfoResponse testImageInfoResponse;
    private ImageDataRequest imageDataRequest;
    private ImageDataResponse testImageDataResponse;

    //test data image info
    private final UUID imageInfoId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final String uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
    private final String title = "title";
    private final String description = "description";
    private final String author = "author";
    private final Date publicationDate = new Date();
    private final List<String> tags = new ArrayList<>();
    private final List<String> sharedUserIds = new ArrayList<>();
    private final Status status = Status.Enabled;
    private final Privacy privacy = Privacy.Public;
    private Date createdTime;
    private Date updatedTime;
    private List<ImageInfoResponse> testImageInfoResponseList;
    private final String email = "example@email.com";
    private final String[] sharedUserEmails = new String[1];

    //test data image data
    private final UUID imageDataId = UUID.randomUUID();
    private final String fileName = "file name";
    private final byte[] data = {0};
    private final int index = 0;
    private MockMultipartFile multipartFile;
    private List<ImageDataResponse> testImageDataResponseList;

    @BeforeAll
    public void setup() {
        //controller setup
        imageService = Mockito.mock(IImageService.class);
        imageController = new ImageController(imageService);

        //data setup
        multipartFile = new MockMultipartFile(fileName, data);
        sharedUserEmails[0] = email;

        //date setup
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        createdTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 3);
        updatedTime = cal.getTime();

        //mocks
        when(imageService.updateInfo(any())).thenReturn(testImageInfoResponse);
    }

    @BeforeEach
    public void beforeEach() {
        //request setup
        imageInfoRequest = new ImageInfoRequest(imageInfoId, uid, title, author, publicationDate, description,
                tags, sharedUserIds, status, privacy);

        imageDataRequest = new ImageDataRequest(imageDataId, imageInfoId, fileName, data, index);

        //test response setup
        testImageInfoResponse = ImageInfoResponse.builder()
                .id(imageInfoId)
                .uid(uid)
                .title(title).author(author).publicationDate(publicationDate).description(description)
                .tags(tags).sharedUserIds(sharedUserIds)
                .status(status).privacy(privacy)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
        testImageInfoResponseList = new ArrayList<>();
        testImageInfoResponseList.add(testImageInfoResponse);

        testImageDataResponse = ImageDataResponse.builder()
                .id(imageDataId)
                .infoId(imageInfoId)
                .fileName(fileName)
                .data(data)
                .index(index)
                .build();
        testImageDataResponseList = new ArrayList<>();
        testImageDataResponseList.add(testImageDataResponse);
    }

    /////////--------------ImageInfo tests----------------------------

    //---------------------uploadImageInfo tests:---------------------
    @Test
    public void uploadImageInfo_success() {
        //mocks
        when(imageService.saveInfo(any())).thenReturn(testImageInfoResponse);

        //act
        ResponseEntity<ImageInfoResponse> responseEntity = imageController.uploadImageInfo(imageInfoRequest);
        ImageInfoResponse imageInfoResponse = responseEntity.getBody();

        //assert
        assertTrue(responseEntity.hasBody());
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(title, imageInfoResponse.getTitle());
        assertEquals(author, imageInfoResponse.getAuthor());
        assertEquals(publicationDate, imageInfoResponse.getPublicationDate());
        assertEquals(description, imageInfoResponse.getDescription());
        assertEquals(tags, imageInfoResponse.getTags());
        assertNull(imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void uploadImageInfo_nullDescription_success() {
        //set up
        imageInfoRequest.setDescription(null);
        testImageInfoResponse.setDescription(null);
        //mocks
        when(imageService.saveInfo(any())).thenReturn(testImageInfoResponse);

        //act
        ResponseEntity<ImageInfoResponse> responseEntity = imageController.uploadImageInfo(imageInfoRequest);
        ImageInfoResponse imageInfoResponse = responseEntity.getBody();

        //assert
        assertTrue(responseEntity.hasBody());
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(title, imageInfoResponse.getTitle());
        assertEquals(author, imageInfoResponse.getAuthor());
        assertEquals(publicationDate, imageInfoResponse.getPublicationDate());
        assertNull(imageInfoResponse.getDescription());
        assertEquals(tags, imageInfoResponse.getTags());
        assertNull(imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }


    //---------------------updateImageInfo tests:---------------------
    @Test
    public void updateImageInfo_success() {
        //mock
        when(imageService.updateInfo(imageInfoRequest)).thenReturn(testImageInfoResponse);

        //act
        ResponseEntity<ImageInfoResponse> responseEntity = imageController.updateImageInfo(imageInfoRequest);
        ImageInfoResponse imageInfoResponse = responseEntity.getBody();

        //assert
        assertTrue(responseEntity.hasBody());
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(title, imageInfoResponse.getTitle());
        assertEquals(author, imageInfoResponse.getAuthor());
        assertEquals(publicationDate, imageInfoResponse.getPublicationDate());
        assertEquals(description, imageInfoResponse.getDescription());
        assertEquals(tags, imageInfoResponse.getTags());
        assertNull(imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void updateImageInfo_nullDescription_success() {
        //set up
        imageInfoRequest.setDescription(null);
        testImageInfoResponse.setDescription(null);
        //mock
        when(imageService.updateInfo(imageInfoRequest)).thenReturn(testImageInfoResponse);

        //act
        ResponseEntity<ImageInfoResponse> responseEntity = imageController.updateImageInfo(imageInfoRequest);
        ImageInfoResponse imageInfoResponse = responseEntity.getBody();

        //assert
        assertTrue(responseEntity.hasBody());
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(title, imageInfoResponse.getTitle());
        assertEquals(author, imageInfoResponse.getAuthor());
        assertEquals(publicationDate, imageInfoResponse.getPublicationDate());
        assertNull(imageInfoResponse.getDescription());
        assertEquals(tags, imageInfoResponse.getTags());
        assertNull(imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    //---------------------shareImage tests:---------------------

    @Test
    public void shareImage_success() {
        //mock
        when(imageService.shareImage(any(ImageInfoRequest.class), any(String[].class))).thenReturn(testImageInfoResponse);

        //act
        ResponseEntity<ImageInfoResponse> responseEntity = imageController.shareImage(imageInfoRequest, sharedUserEmails);

        //assert
        assertTrue(responseEntity.hasBody());
        ImageInfoResponse imageInfoResponse = responseEntity.getBody();
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(title, imageInfoResponse.getTitle());
        assertEquals(author, imageInfoResponse.getAuthor());
        assertEquals(publicationDate, imageInfoResponse.getPublicationDate());
        assertEquals(description, imageInfoResponse.getDescription());
        assertEquals(tags, imageInfoResponse.getTags());
        assertNull(imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }


    //---------------------getAllSharedEmailsByImageInfoId tests:---------------------

    @Test
    public void getAllSharedEmailsByImageInfoId_success() {
        //set up
        List<String> sharedUserEmails = new ArrayList<>();
        sharedUserEmails.add(email);

        //mock
        when(imageService.getAllSharedEmailsByImageInfoId(any(UUID.class), any(String.class))).thenReturn(sharedUserEmails);

        //act
        ResponseEntity<List<String>> responseEntity = imageController.getAllSharedEmailsByImageInfoId(imageInfoId, uid);

        //assert
        assertTrue(responseEntity.hasBody());
        List<String> returnedEmails = responseEntity.getBody();
        assertEquals(sharedUserEmails, returnedEmails);
    }


    //---------------------getImageInfoById tests:---------------------
    @Test
    public void getImageInfoById_success() {
        //mock
        when(imageService.getByIdInfo(any(UUID.class), any(String.class))).thenReturn(testImageInfoResponse);

        //act
        ResponseEntity<ImageInfoResponse> responseEntity = imageController.getImageInfoById(imageInfoId, uid);

        //assert
        assertTrue(responseEntity.hasBody());
        ImageInfoResponse imageInfoResponse = responseEntity.getBody();
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(title, imageInfoResponse.getTitle());
        assertEquals(author, imageInfoResponse.getAuthor());
        assertEquals(publicationDate, imageInfoResponse.getPublicationDate());
        assertEquals(description, imageInfoResponse.getDescription());
        assertEquals(tags, imageInfoResponse.getTags());
        assertNull(imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }


    //---------------------getAllImageInfosByUid tests:---------------------
    @Test
    public void getAllImageInfosByUid_success() {
        //mock
        when(imageService.getAllByUidImageInfos(any(String.class))).thenReturn(testImageInfoResponseList);

        //act
        ResponseEntity<List<ImageInfoResponse>> responseEntity = imageController.getAllImageInfosByUid(uid);

        //assert
        assertTrue(responseEntity.hasBody());
        List<ImageInfoResponse> imageInfoResponses = responseEntity.getBody();
        assertNotNull(imageInfoResponses);
        assertFalse(imageInfoResponses.isEmpty());
        ImageInfoResponse imageInfoResponse = imageInfoResponses.get(0);
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(title, imageInfoResponse.getTitle());
        assertEquals(author, imageInfoResponse.getAuthor());
        assertEquals(publicationDate, imageInfoResponse.getPublicationDate());
        assertEquals(description, imageInfoResponse.getDescription());
        assertEquals(tags, imageInfoResponse.getTags());
        assertNull(imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }


    //---------------------getAllPublicImageInfos tests:---------------------
    @Test
    public void getAllPublicImages_success() {
        //set up
        testImageInfoResponseList.get(0).setPrivacy(Privacy.Public);

        //mock
        when(imageService.getAllPublicImages()).thenReturn(testImageInfoResponseList);

        //act
        ResponseEntity<List<ImageInfoResponse>> responseEntity = imageController.getAllPublicImageInfos();

        //assert
        assertTrue(responseEntity.hasBody());
        List<ImageInfoResponse> imageInfoResponses = responseEntity.getBody();
        assertNotNull(imageInfoResponses);
        assertFalse(imageInfoResponses.isEmpty());
        ImageInfoResponse imageInfoResponse = imageInfoResponses.get(0);
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(title, imageInfoResponse.getTitle());
        assertEquals(author, imageInfoResponse.getAuthor());
        assertEquals(publicationDate, imageInfoResponse.getPublicationDate());
        assertEquals(description, imageInfoResponse.getDescription());
        assertEquals(tags, imageInfoResponse.getTags());
        assertNull(imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(Privacy.Public, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    //---------------------getAllSharedImageInfosByUid tests:---------------------

    @Test
    public void getAllSharedImageInfosByUid_success() {
        //set up
        testImageInfoResponseList.get(0).setPrivacy(Privacy.Shared);

        //mock
        when(imageService.getAllSharedImages(any(String.class))).thenReturn(testImageInfoResponseList);

        //act
        ResponseEntity<List<ImageInfoResponse>> responseEntity = imageController.getAllSharedImageInfosByUid(uid);

        //assert
        assertTrue(responseEntity.hasBody());
        List<ImageInfoResponse> imageInfoResponses = responseEntity.getBody();
        assertNotNull(imageInfoResponses);
        assertFalse(imageInfoResponses.isEmpty());
        ImageInfoResponse imageInfoResponse = imageInfoResponses.get(0);
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(title, imageInfoResponse.getTitle());
        assertEquals(author, imageInfoResponse.getAuthor());
        assertEquals(publicationDate, imageInfoResponse.getPublicationDate());
        assertEquals(description, imageInfoResponse.getDescription());
        assertEquals(tags, imageInfoResponse.getTags());
        assertNull(imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(Privacy.Shared, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    //---------------------deleteImageInfoById tests:---------------------
    @Test
    public void deleteImageInfoById_success() {
        //assert
        imageController.deleteImageInfoById(imageInfoId, uid);
    }

    @Test
    public void deleteImageInfoById_InvalidUid_fail() {
        //assert
        assertThrows(IllegalArgumentException.class, () -> imageController.deleteImageInfoById(imageInfoId, null));
    }

    /////////--------------ImageData tests----------------------------

    //---------------------getImageDataById tests:---------------------
    @Test
    public void getImageDataById_success() {
        //mock
        when(imageService.getByIdData(any(UUID.class), any(String.class))).thenReturn(testImageDataResponse);

        //act
        ResponseEntity<byte[]> responseEntity = imageController.getImageDataById(imageDataId, uid);
        byte[] bytesResponse = responseEntity.getBody();

        //assert
        assertTrue(responseEntity.hasBody());
        assertNotNull(bytesResponse);
        assertEquals(data, bytesResponse);
    }


    //---------------------getImageDatasByImageInfoId tests:---------------------
    @Test
    public void getImageDatasByImageInfoId_success() {
        //mock
        when(imageService.getAllByImageInfoIdImageDatas(any(UUID.class), any(String.class))).thenReturn(testImageDataResponseList);

        //act
        ResponseEntity<List<ImageDataResponse>> responseEntity = imageController.getImageDatasByImageInfoId(imageInfoId, uid);

        //assert
        assertTrue(responseEntity.hasBody());
        List<ImageDataResponse> imageDataResponses = responseEntity.getBody();
        assertNotNull(imageDataResponses);
        assertFalse(imageDataResponses.isEmpty());
        ImageDataResponse imageDataResponse = imageDataResponses.get(0);
        assertNotNull(imageDataResponse);
        assertEquals(imageDataId, imageDataResponse.getId());
        assertEquals(data, imageDataResponse.getData());
        assertEquals(fileName, imageDataResponse.getFileName());
        assertEquals(index, imageDataResponse.getIndex());
    }

    //---------------------deleteImageDataById tests:---------------------
    @Test
    public void deleteImageDataById_success() {
        //assert
        imageController.deleteImageDataById(imageDataId, uid, true);
    }

    @Test
    public void deleteImageDataById_InvalidUid_fail() {
        //assert
        assertThrows(IllegalArgumentException.class, () -> imageController.deleteImageDataById(imageInfoId, null, true));
    }
}
