package com.manuscript.rest.api;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import com.manuscript.rest.service.IImageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageControllerTests {

    //test classes
    private IImageService imageService;
    private ImageController imageController;
    private ImageInfoRequest imageInfoRequest;
    private ImageInfoResponse testImageInfoResponse;

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
        status = Status.Enabled;
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
        invalidUid = null;
        invalidFileName = null;
        invalidData = null;

        //mocks
        when(imageService.updateInfo(any())).thenReturn(testImageInfoResponse);
    }

    @BeforeEach
    public void beforeEach() {
        //request setup
        imageInfoRequest = new ImageInfoRequest(imageId,uid,fileName,status,privacy,data);

        //test response setup
        testImageInfoResponse = new ImageInfoResponse(imageId,userId,uid,fileName,data,status,privacy,createdTime,updatedTime);
    }


//---------------------uploadDocument tests:---------------------
    @Test
    public void uploadDocument_Success() {
        try{
            //act
            ResponseEntity<ImageInfoResponse> responseEntity = imageController.uploadImageInfo(multipartFile,uid);
            ImageInfoResponse imageInfoResponse = responseEntity.getBody();
            //assert
            assertTrue(responseEntity.hasBody());
            assertNotNull(imageInfoResponse);
            assertEquals(uid, imageInfoResponse.getUid());
            assertEquals(fileName, imageInfoResponse.getFileName());
            assertEquals(data, imageInfoResponse.getData());
            assertEquals(status, imageInfoResponse.getStatus());
            assertEquals(privacy, imageInfoResponse.getPrivacy());
            assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) || imageInfoResponse.getCreatedTime().equals(new Date()));
            assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) || imageInfoResponse.getUpdatedTime().equals(createdTime));
        }
        catch (Exception e){
            fail("exception detected:" + e);
        }
    }

    @Test
    public void uploadDocument_InvalidData() {
        MockMultipartFile invalidMultipartFile = new MockMultipartFile(fileName,invalidData);
        imageInfoRequest.setData(invalidData);
        assertThrows(UnauthorizedException.class, () -> imageController.uploadImageInfo(invalidMultipartFile,uid));
    }

    @Test
    public void uploadDocument_InvalidFilename() {
        MockMultipartFile invalidMultipartFile = new MockMultipartFile(invalidFileName,data);
        imageInfoRequest.setData(invalidData);
        assertThrows(UnauthorizedException.class, () -> imageController.uploadImageInfo(invalidMultipartFile,uid));
    }

    @Test
    public void uploadDocumentInvalidUid() {
        imageInfoRequest.setData(invalidData);
        assertThrows(UnauthorizedException.class, () -> imageController.uploadImageInfo(multipartFile,invalidUid));
    }


//---------------------updateDocument tests:---------------------
    @Test
    public void updateDocument_Success() {
        //act
        ResponseEntity<ImageInfoResponse> responseEntity = imageController.updateImageInfo(imageInfoRequest);
        ImageInfoResponse imageInfoResponse = responseEntity.getBody();
        //assert
        assertTrue(responseEntity.hasBody());
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(fileName, imageInfoResponse.getFileName());
        assertEquals(data, imageInfoResponse.getData());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) || imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) || imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void updateDocument_InvalidId() {
        imageInfoRequest.setDocumentId(invalidImageId);
        assertThrows(UnauthorizedException.class, () -> imageController.updateImageInfo(imageInfoRequest));
    }

    @Test
    public void updateDocument_InvalidUid() {
        imageInfoRequest.setUid(invalidUid);
        assertThrows(UnauthorizedException.class, () -> imageController.updateImageInfo(imageInfoRequest));
    }

    @Test
    public void updateDocument_InvalidFileName() {
        imageInfoRequest.setFileName(invalidFileName);
        assertThrows(UnauthorizedException.class, () -> imageController.updateImageInfo(imageInfoRequest));
    }

    @Test
    public void updateDocument_InvalidData() {
        imageInfoRequest.setData(invalidData);
        assertThrows(UnauthorizedException.class, () -> imageController.updateImageInfo(imageInfoRequest));
    }


//---------------------deleteDocumentById tests:---------------------
    @Test
    public void deleteDocumentById_Success() {
        imageController.deleteImageInfoById(userId);
    }

    @Test
    public void deleteDocumentById_InvalidUserId() {
        assertThrows(UnauthorizedException.class, () -> imageController.deleteImageInfoById(invalidUserId));
    }


//---------------------getDocumentById tests:---------------------
    @Test
    public void getDocumentById_Success() {
        //act
        ResponseEntity<byte[]> responseEntity = imageController.getImageInfoById(userId);
        byte[] bytesResponse = responseEntity.getBody();
        //assert
        assertTrue(responseEntity.hasBody());
        assertNotNull(bytesResponse);
        assertEquals(data,bytesResponse);
    }

    @Test
    public void getDocumentById_InvalidUserId() {
        assertThrows(UnauthorizedException.class, () -> imageController.getImageInfoById(invalidUserId));
    }


//---------------------getDocumentById tests:---------------------
    @Test
    public void getAllDocumentsByUid_Success() {
        //act
        ResponseEntity<List<ImageInfoResponse>> responseEntity = imageController.getAllImageInfosByUid(uid);
        List<ImageInfoResponse> imageInfoResponseList = responseEntity.getBody();
        ImageInfoResponse imageInfoResponse = imageInfoResponseList.get(0);
        //assert
        assertTrue(responseEntity.hasBody());
        assertEquals(imageInfoResponseList.size(),1);
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(fileName, imageInfoResponse.getFileName());
        assertEquals(data, imageInfoResponse.getData());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) || imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) || imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getAllDocumentsByUid_InvalidUserId() {
        assertThrows(UnauthorizedException.class, () -> imageController.getAllImageInfosByUid(invalidUid));
    }


//---------------------getAllPublicImages tests:---------------------
    @Test
    public void getAllPublicImages_Success() {
        //act
        ResponseEntity<List<ImageInfoResponse>> responseEntity = imageController.getAllPublicImages();
        List<ImageInfoResponse> imageInfoResponseList = responseEntity.getBody();
        ImageInfoResponse imageInfoResponse = imageInfoResponseList.get(0);
        //assert
        assertTrue(responseEntity.hasBody());
        assertEquals(imageInfoResponseList.size(), 1);
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(fileName, imageInfoResponse.getFileName());
        assertEquals(data, imageInfoResponse.getData());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) || imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) || imageInfoResponse.getUpdatedTime().equals(createdTime));
    }
}
