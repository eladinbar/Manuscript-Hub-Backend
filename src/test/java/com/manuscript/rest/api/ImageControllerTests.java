package com.manuscript.rest.api;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.rest.forms.request.ImageRequest;
import com.manuscript.rest.forms.response.ImageResponse;
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
        when(imageService.update(any())).thenReturn(testImageResponse);
    }

    @BeforeEach
    public void beforeEach() {
        //request setup
        imageRequest = new ImageRequest(imageId,uid,fileName,status,privacy,data);

        //test response setup
        testImageResponse = new ImageResponse(imageId,userId,uid,fileName,data,status,privacy,createdTime,updatedTime);
    }


//---------------------uploadDocument tests:---------------------
    @Test
    public void uploadDocument_Success() {
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
        MockMultipartFile invalidMultipartFile = new MockMultipartFile(fileName,invalidData);
        imageRequest.setData(invalidData);
        assertThrows(UnauthorizedException.class, () -> imageController.uploadDocument(invalidMultipartFile,uid));
    }

    @Test
    public void uploadDocument_InvalidFilename() {
        MockMultipartFile invalidMultipartFile = new MockMultipartFile(invalidFileName,data);
        imageRequest.setData(invalidData);
        assertThrows(UnauthorizedException.class, () -> imageController.uploadDocument(invalidMultipartFile,uid));
    }

    @Test
    public void uploadDocumentInvalidUid() {
        imageRequest.setData(invalidData);
        assertThrows(UnauthorizedException.class, () -> imageController.uploadDocument(multipartFile,invalidUid));
    }


//---------------------updateDocument tests:---------------------
    @Test
    public void updateDocument_Success() {
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
        imageRequest.setDocumentId(invalidImageId);
        assertThrows(UnauthorizedException.class, () -> imageController.updateDocument(imageRequest));
    }

    @Test
    public void updateDocument_InvalidUid() {
        imageRequest.setUid(invalidUid);
        assertThrows(UnauthorizedException.class, () -> imageController.updateDocument(imageRequest));
    }

    @Test
    public void updateDocument_InvalidFileName() {
        imageRequest.setFileName(invalidFileName);
        assertThrows(UnauthorizedException.class, () -> imageController.updateDocument(imageRequest));
    }

    @Test
    public void updateDocument_InvalidData() {
        imageRequest.setData(invalidData);
        assertThrows(UnauthorizedException.class, () -> imageController.updateDocument(imageRequest));
    }


//---------------------deleteDocumentById tests:---------------------
    @Test
    public void deleteDocumentById_Success() {
        imageController.deleteDocumentById(userId);
    }

    @Test
    public void deleteDocumentById_InvalidUserId() {
        assertThrows(UnauthorizedException.class, () -> imageController.deleteDocumentById(invalidUserId));
    }


//---------------------getDocumentById tests:---------------------
    @Test
    public void getDocumentById_Success() {
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
        assertThrows(UnauthorizedException.class, () -> imageController.getDocumentById(invalidUserId));
    }


//---------------------getDocumentById tests:---------------------
    @Test
    public void getAllDocumentsByUid_Success() {
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
        assertThrows(UnauthorizedException.class, () -> imageController.getAllDocumentsByUid(invalidUid));
    }


//---------------------getAllPublicImages tests:---------------------
    @Test
    public void getAllPublicImages_Success() {
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
