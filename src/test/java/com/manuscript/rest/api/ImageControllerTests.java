//package com.manuscript.rest.api;
//
//import com.manuscript.core.domain.common.enums.Privacy;
//import com.manuscript.core.domain.common.enums.Status;
//import com.manuscript.core.exceptions.UnauthorizedException;
//import com.manuscript.rest.forms.request.ImageInfoRequest;
//import com.manuscript.rest.forms.response.ImageInfoResponse;
//import com.manuscript.rest.service.IImageService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.mockito.Mockito;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//
//import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class ImageControllerTests {
//
//    //test classes
//    private IImageService imageService;
//    private ImageController imageController;
//    private ImageInfoRequest imageInfoRequest;
//    private ImageInfoResponse testImageInfoResponse;
//
//    //test data
//    private UUID imageId;
//    private UUID userId;
//    private String uid;
//    private String fileName;
//    private Status status;
//    private final byte[] data = {0};
//    private Privacy privacy;
//    private Date createdTime;
//    private Date updatedTime;
//    private MockMultipartFile multipartFile;
//    private List<ImageResponse> testImageResponseList;
//
//    //test fail case data
//    private UUID invalidImageId;
//    private UUID invalidUserId;
//    private String invalidUid;
//    private String invalidEmptyUid;
//    private String invalidFileName;
//    private byte[] invalidData;
//    private MockMultipartFile invalidMultipartFile;
//
//    @BeforeAll
//    public void setup(){
//        //controller setup
//        imageService = Mockito.mock(IImageService.class);
//        imageController = new ImageController(imageService);
//
//        //data setup
//        fileName = "fileName";
//        status = Status.Enabled;
//        uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
//        multipartFile = new MockMultipartFile(fileName,data);
//        imageId = UUID.randomUUID();
//        userId = UUID.randomUUID();
//        privacy = Privacy.Public;
//
//        //date setup
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, 2023);
//        cal.set(Calendar.MONTH, Calendar.JANUARY);
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//        createdTime = cal.getTime();
//        cal.set(Calendar.DAY_OF_MONTH, 3);
//        updatedTime = cal.getTime();
//
//        //fail case data setup
//        invalidImageId = null;
//        invalidUserId = null;
//        invalidUid = "";
//        invalidEmptyUid = null;
//        invalidFileName = null;
//        invalidData = null;
//
//        //mocks
//        when(imageService.updateInfo(any())).thenReturn(testImageInfoResponse);
//        invalidMultipartFile = null;
//    }
//
//    @BeforeEach
//    public void beforeEach() {
//        //request setup
//        imageInfoRequest = new ImageInfoRequest(imageId,uid,fileName,status,privacy,data);
//
//        //test response setup
//        testImageResponse = ImageResponse.builder()
//                .documentId(imageId)
//                .userId(userId)
//                .uid(uid)
//                .fileName(fileName)
//                .data(data)
//                .status(status)
//                .privacy(privacy)
//                .createdTime(createdTime)
//                .updatedTime(updatedTime)
//                .build();
//        testImageResponseList = new ArrayList<>();
//        testImageResponseList.add(testImageResponse);
//    }
//
//
////---------------------uploadDocument tests:---------------------
//    @Test
//    public void uploadDocument_Success() {
//        //mocks
//        when(imageService.save(any())).thenReturn(testImageResponse);
//        try{
//            //act
//            ResponseEntity<ImageInfoResponse> responseEntity = imageController.uploadImageInfo(multipartFile,uid);
//            ImageInfoResponse imageInfoResponse = responseEntity.getBody();
//            //assert
//            assertTrue(responseEntity.hasBody());
//            assertNotNull(imageInfoResponse);
//            assertEquals(uid, imageInfoResponse.getUid());
//            assertEquals(fileName, imageInfoResponse.getFileName());
//            assertEquals(data, imageInfoResponse.getData());
//            assertEquals(status, imageInfoResponse.getStatus());
//            assertEquals(privacy, imageInfoResponse.getPrivacy());
//            assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) || imageInfoResponse.getCreatedTime().equals(new Date()));
//            assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) || imageInfoResponse.getUpdatedTime().equals(createdTime));
//        }
//        catch (Exception e){
//            fail("exception detected:" + e);
//        }
//    }
//
//    @Test
//    public void uploadDocument_invalidMultipartFile() {
//        //mocks
//        when(imageService.save(imageRequest)).thenReturn(testImageResponse);
//        //assert
//        assertThrows(Exception.class, () -> imageController.uploadDocument(invalidMultipartFile,uid));
//    }
//
//    @Test
//    public void uploadDocumentInvalidUid() {
//        imageRequest.setData(invalidData);
//        assertThrows(Exception.class, () -> imageController.uploadDocument(multipartFile,invalidEmptyUid));
//    }
//
//
////---------------------updateDocument tests:---------------------
//    @Test
//    public void updateDocument_Success() {
//        //mock
//        when(imageService.update(imageRequest)).thenReturn(testImageResponse);
//        //act
//        ResponseEntity<ImageInfoResponse> responseEntity = imageController.updateImageInfo(imageInfoRequest);
//        ImageInfoResponse imageInfoResponse = responseEntity.getBody();
//        //assert
//        assertTrue(responseEntity.hasBody());
//        assertNotNull(imageInfoResponse);
//        assertEquals(uid, imageInfoResponse.getUid());
//        assertEquals(fileName, imageInfoResponse.getFileName());
//        assertEquals(data, imageInfoResponse.getData());
//        assertEquals(status, imageInfoResponse.getStatus());
//        assertEquals(privacy, imageInfoResponse.getPrivacy());
//        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) || imageInfoResponse.getCreatedTime().equals(new Date()));
//        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) || imageInfoResponse.getUpdatedTime().equals(createdTime));
//    }
//
//    @Test
//    public void updateDocument_InvalidId() {
//        //mock
//        when(imageService.update(imageRequest)).thenReturn(testImageResponse);
//        //act
//        imageRequest.setDocumentId(invalidImageId);
//        //assert
//        assertThrows(Exception.class, () -> imageController.updateDocument(imageRequest));
//    }
//
//    @Test
//    public void updateDocument_InvalidData() {
//        //mock
//        when(imageService.update(imageRequest)).thenReturn(testImageResponse);
//        //act
//        imageRequest.setData(invalidData);
//        //assert
//        assertThrows(Exception.class, () -> imageController.updateDocument(imageRequest));
//    }
//
//
////---------------------deleteDocumentById tests:---------------------
//    @Test
//    public void deleteDocumentById_Success() {
//        //mock
//        doNothing().when(imageService).deleteById(imageId);
//        //assert
//        imageController.deleteDocumentById(imageId);
//    }
//
//    @Test
//    public void deleteDocumentById_InvalidUserId() {
//        //mock
//        doNothing().when(imageService).deleteById(imageId);
//        //assert
//        assertThrows(Exception.class, () -> imageController.deleteDocumentById(invalidUserId));
//    }
//
//
////---------------------getDocumentById tests:---------------------
//    @Test
//    public void getDocumentById_Success() {
//        //mock
//        when(imageService.getById(userId)).thenReturn(testImageResponse);
//        //act
//        ResponseEntity<byte[]> responseEntity = imageController.getImageInfoById(userId);
//        byte[] bytesResponse = responseEntity.getBody();
//        //assert
//        assertTrue(responseEntity.hasBody());
//        assertNotNull(bytesResponse);
//        assertEquals(data,bytesResponse);
//    }
//
//    @Test
//    public void getDocumentById_InvalidUserId() {
//        //mock
//        when(imageService.getById(userId)).thenReturn(testImageResponse);
//        //assert
//        assertThrows(Exception.class, () -> imageController.getDocumentById(invalidUserId));
//    }
//
//
////---------------------getDocumentById tests:---------------------
//    @Test
//    public void getAllDocumentsByUid_Success() {
//        //mock
//        when(imageService.getAllByUid(uid)).thenReturn(testImageResponseList);
//        //act
//        ResponseEntity<List<ImageInfoResponse>> responseEntity = imageController.getAllImageInfosByUid(uid);
//        List<ImageInfoResponse> imageInfoResponseList = responseEntity.getBody();
//        ImageInfoResponse imageInfoResponse = imageInfoResponseList.get(0);
//        //assert
//        assertTrue(responseEntity.hasBody());
//        assertEquals(imageInfoResponseList.size(),1);
//        assertNotNull(imageInfoResponse);
//        assertEquals(uid, imageInfoResponse.getUid());
//        assertEquals(fileName, imageInfoResponse.getFileName());
//        assertEquals(data, imageInfoResponse.getData());
//        assertEquals(status, imageInfoResponse.getStatus());
//        assertEquals(privacy, imageInfoResponse.getPrivacy());
//        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) || imageInfoResponse.getCreatedTime().equals(new Date()));
//        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) || imageInfoResponse.getUpdatedTime().equals(createdTime));
//    }
//
//    @Test
//    public void getAllDocumentsByUid_InvalidUserId() {
//        //mock
//        when(imageService.getAllByUid(uid)).thenReturn(testImageResponseList);
//        //act
//        invalidUid = null;
//        //assert
//        assertThrows(Exception.class, () -> imageController.getAllDocumentsByUid(invalidUid));
//    }
//
//
////---------------------getAllPublicImages tests:---------------------
//    @Test
//    public void getAllPublicImages_Success() {
//        //mock
//        when(imageService.getAllPublicImages()).thenReturn(testImageResponseList);
//        //act
//        ResponseEntity<List<ImageInfoResponse>> responseEntity = imageController.getAllPublicImages();
//        List<ImageInfoResponse> imageInfoResponseList = responseEntity.getBody();
//        ImageInfoResponse imageInfoResponse = imageInfoResponseList.get(0);
//        //assert
//        assertTrue(responseEntity.hasBody());
//        assertEquals(imageInfoResponseList.size(), 1);
//        assertNotNull(imageInfoResponse);
//        assertEquals(uid, imageInfoResponse.getUid());
//        assertEquals(fileName, imageInfoResponse.getFileName());
//        assertEquals(data, imageInfoResponse.getData());
//        assertEquals(status, imageInfoResponse.getStatus());
//        assertEquals(privacy, imageInfoResponse.getPrivacy());
//        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) || imageInfoResponse.getCreatedTime().equals(new Date()));
//        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) || imageInfoResponse.getUpdatedTime().equals(createdTime));
//    }
//}
