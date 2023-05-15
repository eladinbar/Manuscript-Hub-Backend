package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.custom.image.*;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageServiceTests {

    //test classes
    private ImageServiceImpl imageServiceImpl;
    private IRestMapper<ImageModel, ImageRequest> imageRequestMapper;
    private IRestMapper<ImageModel, ImageResponse> imageResponseMapper;
    private ICreateImage createImageUseCase;
    private IGetAllImages getAllImagesUseCase;
    private IGetByIdImage getByIdImageUseCase;
    private IUpdateImage updateImageUseCase;
    private IGetAllPublicImages getAllPublicImages;
    private ImageRequest imageRequest;
    private ImageResponse imageResponse;
    private ImageModel imageModel;
    private List<ImageModel> listImageModel;
    private Optional<ImageModel> optionalImageModel;
    private List<ImageResponse> imageResponseList;

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

    //test fail case data
    private String invalidUid;
    private Status invalidStatus;
    private UUID invalidImageId;

    @BeforeAll
    @SuppressWarnings("unchecked")
    public void setup(){
        //service setup
        imageRequestMapper = (IRestMapper<ImageModel, ImageRequest>) Mockito.mock(IRestMapper.class);
        imageResponseMapper = (IRestMapper<ImageModel, ImageResponse>) Mockito.mock(IRestMapper.class);
        createImageUseCase = Mockito.mock(ICreateImage.class);
        getAllImagesUseCase = Mockito.mock(IGetAllImages.class);
        getByIdImageUseCase = Mockito.mock(IGetByIdImage.class);
        updateImageUseCase = Mockito.mock(IUpdateImage.class);
        getAllPublicImages = Mockito.mock(IGetAllPublicImages.class);
        imageServiceImpl = new ImageServiceImpl(imageRequestMapper,imageResponseMapper,createImageUseCase,getAllImagesUseCase,getByIdImageUseCase,updateImageUseCase,getAllPublicImages);

        //data setup
        fileName = "fileName";
        status = Status.active;
        uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
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

        //failcase data setup
        invalidUid = "";
        invalidStatus = Status.inactive;
        invalidImageId = null;
    }

    @BeforeEach
    public void beforeEach() {
        //test model setup
        imageModel = ImageModel.builder()
                .id(imageId)
                .uid(uid)
                .fileName(fileName)
                .status(status)
                .privacy(privacy)
                .data(data)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();
        listImageModel = new ArrayList<>();
        listImageModel.add(imageModel);
        optionalImageModel = Optional.of(imageModel);

        //test request setup
        imageRequest = new ImageRequest(imageId,uid,fileName,status,privacy,data);

        //test response setup
        imageResponse = new ImageResponse(imageId,userId,uid,fileName,data,status,privacy,createdTime,updatedTime);
        imageResponseList = new ArrayList<>();
        imageResponseList.add(imageResponse);

        //mapper mocks
        when(imageRequestMapper.restToModel(any(ImageRequest.class))).thenReturn(imageModel);
        when(imageRequestMapper.modelToRest(any(ImageModel.class))).thenReturn(imageRequest);
        when(imageResponseMapper.restToModel(any(ImageResponse.class))).thenReturn(imageModel);
        when(imageResponseMapper.modelToRest(any(ImageModel.class))).thenReturn(imageResponse);

        //usecase mocks
        when(createImageUseCase.create(imageModel)).thenReturn(imageModel);
        when(getAllImagesUseCase.getAll()).thenReturn(listImageModel);
        when(getByIdImageUseCase.getById(any(UUID.class))).thenReturn(optionalImageModel);
        when(updateImageUseCase.update(any(ImageModel.class))).thenReturn(imageModel);
        when(getAllPublicImages.getAllPublicImages()).thenReturn(listImageModel);
    }

    @Test
    public void save_Success() {
        //act
        ImageResponse testImageResponse = imageServiceImpl.save(imageRequest);
        //assert
        assertNotNull(testImageResponse);
        assertEquals(uid,testImageResponse.getUid());
        assertEquals(fileName,testImageResponse.getFileName());
        assertEquals(data,testImageResponse.getData());
        assertEquals(status,testImageResponse.getStatus());
        assertEquals(privacy,testImageResponse.getPrivacy());
        assertTrue(testImageResponse.getCreatedTime().before(new Date()) || testImageResponse.getCreatedTime().equals(new Date()));
        assertTrue(testImageResponse.getUpdatedTime().after(createdTime) || testImageResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void update_Success() {
        //act
        ImageResponse testImageResponse = imageServiceImpl.update(imageRequest);
        //assert
        assertNotNull(testImageResponse);
        assertEquals(uid,testImageResponse.getUid());
        assertEquals(fileName,testImageResponse.getFileName());
        assertEquals(data,testImageResponse.getData());
        assertEquals(status,testImageResponse.getStatus());
        assertEquals(privacy,testImageResponse.getPrivacy());
        assertTrue(testImageResponse.getCreatedTime().before(new Date()) || testImageResponse.getCreatedTime().equals(new Date()));
        assertTrue(testImageResponse.getUpdatedTime().after(createdTime) || testImageResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getById_Success() {
        //act
        ImageResponse testImageResponse = imageServiceImpl.getById(imageId);
        //assert
        assertNotNull(testImageResponse);
        assertEquals(uid,testImageResponse.getUid());
        assertEquals(fileName,testImageResponse.getFileName());
        assertEquals(data,testImageResponse.getData());
        assertEquals(status,testImageResponse.getStatus());
        assertEquals(privacy,testImageResponse.getPrivacy());
        assertTrue(testImageResponse.getCreatedTime().before(new Date()) || testImageResponse.getCreatedTime().equals(new Date()));
        assertTrue(testImageResponse.getUpdatedTime().after(createdTime) || testImageResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getById_invalidImageId() {
        //assert
        assertThrows(Exception.class, () -> imageServiceImpl.getById(invalidImageId));
    }

    @Test
    public void update_invalidUserId() {
        //act
        imageRequest.setUid(invalidUid);
        //assert
        assertThrows(Exception.class, () -> imageServiceImpl.update(imageRequest));
    }

    @Test
    public void update_invalidStatus() {
        //act
        imageResponse.setStatus(invalidStatus);
        //assert
        assertThrows(Exception.class, () -> imageServiceImpl.update(imageRequest));
    }

    @Test
    public void getAll_Success() {
        //act
        List<ImageResponse> testImageResponseList = imageServiceImpl.getAll();
        ImageResponse testImageResponse = testImageResponseList.get(0);
        //assert
        assertEquals(testImageResponseList.size(),1);
        assertNotNull(testImageResponse);
        assertEquals(uid,testImageResponse.getUid());
        assertEquals(fileName,testImageResponse.getFileName());
        assertEquals(data,testImageResponse.getData());
        assertEquals(status,testImageResponse.getStatus());
        assertEquals(privacy,testImageResponse.getPrivacy());
        assertTrue(testImageResponse.getCreatedTime().before(new Date()) || testImageResponse.getCreatedTime().equals(new Date()));
        assertTrue(testImageResponse.getUpdatedTime().after(createdTime) || testImageResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getAllByUid_Success() {
        //act
        List<ImageResponse> testImageResponseList = imageServiceImpl.getAllByUid(uid);
        ImageResponse testImageResponse = testImageResponseList.get(0);
        //assert
        assertEquals(testImageResponseList.size(),1);
        assertNotNull(testImageResponse);
        assertEquals(uid,testImageResponse.getUid());
        assertEquals(fileName,testImageResponse.getFileName());
        assertEquals(data,testImageResponse.getData());
        assertEquals(status,testImageResponse.getStatus());
        assertEquals(privacy,testImageResponse.getPrivacy());
        assertTrue(testImageResponse.getCreatedTime().before(new Date()) || testImageResponse.getCreatedTime().equals(new Date()));
        assertTrue(testImageResponse.getUpdatedTime().after(createdTime) || testImageResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getAllByUid_invalidUid() {
        //act
        List<ImageResponse> testImageResponseList = imageServiceImpl.getAllByUid(invalidUid);
        //assert
        assertEquals(testImageResponseList.size(),0);
    }

    @Test
    public void getAllPublicImages_Success() {
        //act
        List<ImageResponse> testImageResponseList = imageServiceImpl.getAllPublicImages();
        ImageResponse testImageResponse = testImageResponseList.get(0);
        //assert
        assertEquals(testImageResponseList.size(),1);
        assertNotNull(testImageResponse);
        assertEquals(uid,testImageResponse.getUid());
        assertEquals(fileName,testImageResponse.getFileName());
        assertEquals(data,testImageResponse.getData());
        assertEquals(status,testImageResponse.getStatus());
        assertEquals(privacy,testImageResponse.getPrivacy());
        assertTrue(testImageResponse.getCreatedTime().before(new Date()) || testImageResponse.getCreatedTime().equals(new Date()));
        assertTrue(testImageResponse.getUpdatedTime().after(createdTime) || testImageResponse.getUpdatedTime().equals(createdTime));
    }
}
