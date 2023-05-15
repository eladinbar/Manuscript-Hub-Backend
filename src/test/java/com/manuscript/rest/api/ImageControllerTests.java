package com.manuscript.rest.api;

import com.google.cloud.ByteArray;
import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.api.ImageController;
import com.manuscript.rest.response.AnnotationResponse;
import com.manuscript.rest.response.ImageResponse;
import com.manuscript.rest.service.IImageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.BaseStubbing;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageControllerTests {

    //test classes
    private IImageService imageService;
    private ImageController imageController;
    private ImageRequest imageRequest;
    private ImageResponse testImageResponse;

    //test data
    private UUID imageId;
    private UUID userId;
    private String uid;
    private String fileName;
    private Status status;
    private final byte[] data = {0};
    private Privacy privacy;
    private Date createdTime;
    private Date updatedTime;
    private MockMultipartFile multipartFile;
    private List<ImageResponse> testImageResponseList;

    //test fail case data
    private UUID invalidImageId;
    private UUID invalidUserId;
    private String invalidUid;
    private String invalidFileName;
    private byte[] invalidData;

    @BeforeAll
    public void setup(){
        //controller setup
        imageService = Mockito.mock(IImageService.class);
        imageController = new ImageController(imageService);

        //data setup
        fileName = "fileName";
        status = Status.active;
        uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
        multipartFile = new MockMultipartFile(fileName,data);
        imageId = UUID.randomUUID();
        userId = UUID.randomUUID();
        privacy = Privacy.Public;

        //date setup
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        createdTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 3);
        updatedTime = cal.getTime();

        //fail case data setup
        invalidImageId = null;
        invalidUserId = null;
        invalidUid = "";
        invalidFileName = null;
        invalidData = null;
    }

    @BeforeEach
    public void beforeEach() {
        //request setup
        imageRequest = new ImageRequest(imageId,uid,fileName,status,privacy,data);

        //test response setup
        testImageResponse = new ImageResponse(imageId,userId,uid,fileName,data,status,privacy,createdTime,updatedTime);

        testImageResponseList = new ArrayList<>();
        testImageResponseList.add(testImageResponse);
    }


//---------------------uploadDocument tests:---------------------
    @Test
    public void uploadDocument_Success() {
        //mocks
        when(imageService.save(imageRequest)).thenReturn(testImageResponse);
        try{
            //act
            ResponseEntity<ImageResponse> responseEntity = imageController.uploadDocument(multipartFile,uid);
            ImageResponse imageResponse = responseEntity.getBody();
            //assert
            assertTrue(responseEntity.hasBody());
            assertNotNull(imageResponse);
            assertEquals(uid,imageResponse.getUid());
            assertEquals(fileName,imageResponse.getFileName());
            assertEquals(data,imageResponse.getData());
            assertEquals(status,imageResponse.getStatus());
            assertEquals(privacy,imageResponse.getPrivacy());
            assertTrue(imageResponse.getCreatedTime().before(new Date()) || imageResponse.getCreatedTime().equals(new Date()));
            assertTrue(imageResponse.getUpdatedTime().after(createdTime) || imageResponse.getUpdatedTime().equals(createdTime));
        }
        catch (Exception e){
            fail("exception detected:" + e);
        }
    }

    @Test
    public void uploadDocument_InvalidData() {
        //mocks
        when(imageService.save(imageRequest)).thenReturn(testImageResponse);
        MockMultipartFile invalidMultipartFile = new MockMultipartFile(fileName,invalidData);
        //act
        imageRequest.setData(invalidData);
        //assert
        assertThrows(Exception.class, () -> imageController.uploadDocument(invalidMultipartFile,uid));
    }

    @Test
    public void uploadDocument_InvalidFilename() {
        //mocks
        when(imageService.save(imageRequest)).thenReturn(testImageResponse);
        MockMultipartFile invalidMultipartFile = new MockMultipartFile(invalidFileName,data);
        //act
        imageRequest.setFileName(invalidFileName);
        //assert
        assertThrows(Exception.class, () -> imageController.uploadDocument(invalidMultipartFile,uid));
    }

    @Test
    public void uploadDocumentInvalidUid() {
        imageRequest.setData(invalidData);
        assertThrows(Exception.class, () -> imageController.uploadDocument(multipartFile,invalidUid));
    }


//---------------------updateDocument tests:---------------------
    @Test
    public void updateDocument_Success() {
        //mock
        when(imageService.update(imageRequest)).thenReturn(testImageResponse);
        //act
        ResponseEntity<ImageResponse> responseEntity = imageController.updateDocument(imageRequest);
        ImageResponse imageResponse = responseEntity.getBody();
        //assert
        assertTrue(responseEntity.hasBody());
        assertNotNull(imageResponse);
        assertEquals(uid,imageResponse.getUid());
        assertEquals(fileName,imageResponse.getFileName());
        assertEquals(data,imageResponse.getData());
        assertEquals(status,imageResponse.getStatus());
        assertEquals(privacy,imageResponse.getPrivacy());
        assertTrue(imageResponse.getCreatedTime().before(new Date()) || imageResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageResponse.getUpdatedTime().after(createdTime) || imageResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void updateDocument_InvalidId() {
        //mock
        when(imageService.update(imageRequest)).thenReturn(testImageResponse);
        //act
        imageRequest.setDocumentId(invalidImageId);
        //assert
        assertThrows(Exception.class, () -> imageController.updateDocument(imageRequest));
    }

    @Test
    public void updateDocument_InvalidData() {
        //mock
        when(imageService.update(imageRequest)).thenReturn(testImageResponse);
        //act
        imageRequest.setData(invalidData);
        //assert
        assertThrows(Exception.class, () -> imageController.updateDocument(imageRequest));
    }


//---------------------deleteDocumentById tests:---------------------
    @Test
    public void deleteDocumentById_Success() {
        //mock
        doNothing().when(imageService).deleteById(imageId);
        //assert
        imageController.deleteDocumentById(imageId);
    }

    @Test
    public void deleteDocumentById_InvalidUserId() {
        //mock
        doNothing().when(imageService).deleteById(imageId);
        //assert
        assertThrows(Exception.class, () -> imageController.deleteDocumentById(invalidUserId));
    }


//---------------------getDocumentById tests:---------------------
    @Test
    public void getDocumentById_Success() {
        //mock
        when(imageService.getById(userId)).thenReturn(testImageResponse);
        //act
        ResponseEntity<byte[]> responseEntity = imageController.getDocumentById(userId);
        byte[] bytesResponse = responseEntity.getBody();
        //assert
        assertTrue(responseEntity.hasBody());
        assertNotNull(bytesResponse);
        assertEquals(data,bytesResponse);
    }

    @Test
    public void getDocumentById_InvalidUserId() {
        //mock
        when(imageService.getById(userId)).thenReturn(testImageResponse);
        //assert
        assertThrows(Exception.class, () -> imageController.getDocumentById(invalidUserId));
    }


//---------------------getDocumentById tests:---------------------
    @Test
    public void getAllDocumentsByUid_Success() {
        //mock
        when(imageService.getAllByUid(uid)).thenReturn(testImageResponseList);
        //act
        ResponseEntity<List<ImageResponse>> responseEntity = imageController.getAllDocumentsByUid(uid);
        List<ImageResponse> imageResponseList = responseEntity.getBody();
        ImageResponse imageResponse = imageResponseList.get(0);
        //assert
        assertTrue(responseEntity.hasBody());
        assertEquals(imageResponseList.size(),1);
        assertNotNull(imageResponse);
        assertEquals(uid,imageResponse.getUid());
        assertEquals(fileName,imageResponse.getFileName());
        assertEquals(data,imageResponse.getData());
        assertEquals(status,imageResponse.getStatus());
        assertEquals(privacy,imageResponse.getPrivacy());
        assertTrue(imageResponse.getCreatedTime().before(new Date()) || imageResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageResponse.getUpdatedTime().after(createdTime) || imageResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getAllDocumentsByUid_InvalidUserId() {
        //mock
        when(imageService.getAllByUid(uid)).thenReturn(testImageResponseList);
        //act
        invalidUid = null;
        //assert
        assertThrows(Exception.class, () -> imageController.getAllDocumentsByUid(invalidUid));
    }


//---------------------getAllPublicImages tests:---------------------
    @Test
    public void getAllPublicImages_Success() {
        //mock
        when(imageService.getAllPublicImages()).thenReturn(testImageResponseList);
        //act
        ResponseEntity<List<ImageResponse>> responseEntity = imageController.getAllPublicImages();
        List<ImageResponse> imageResponseList = responseEntity.getBody();
        ImageResponse imageResponse = imageResponseList.get(0);
        //assert
        assertTrue(responseEntity.hasBody());
        assertEquals(imageResponseList.size(), 1);
        assertNotNull(imageResponse);
        assertEquals(uid, imageResponse.getUid());
        assertEquals(fileName, imageResponse.getFileName());
        assertEquals(data, imageResponse.getData());
        assertEquals(status, imageResponse.getStatus());
        assertEquals(privacy,imageResponse.getPrivacy());
        assertTrue(imageResponse.getCreatedTime().before(new Date()) || imageResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageResponse.getUpdatedTime().after(createdTime) || imageResponse.getUpdatedTime().equals(createdTime));
    }
}
