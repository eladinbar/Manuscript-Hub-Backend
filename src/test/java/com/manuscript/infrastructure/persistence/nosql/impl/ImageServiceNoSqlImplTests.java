package com.manuscript.infrastructure.persistence.nosql.impl;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.custom.image.*;
import com.manuscript.infrastructure.persistence.nosql.documents.ImageDocument;
import com.manuscript.infrastructure.persistence.nosql.repositories.IImageRepo;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;
import com.manuscript.rest.service.ImageServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageServiceNoSqlImplTests {
    //test classes
    private ImageServiceNoSqlImpl imageServiceNoSqlImpl;
    private IImageRepo repo;
    private IRepositoryEntityMapper<ImageModel, ImageDocument> imageEntityMapper;
    private ImageDocument imageDocument;
    private List<ImageDocument> listImageDocument;
    private Optional<ImageDocument> optionalImageDocument;
    private ImageModel imageModel;
    private List<ImageModel> listImageModel;
    private Optional<ImageModel> optionalImageModel;

    //test data
    private UUID imageId;
    private UUID userId;
    private String uid;
    private String fileName;
    private Status status;
    private final byte[] data = {0};
    private final String stringData = Base64.getEncoder().encodeToString(data);
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
        repo = Mockito.mock(IImageRepo.class);
        imageEntityMapper = (IRepositoryEntityMapper<ImageModel, ImageDocument>) Mockito.mock(IRepositoryEntityMapper.class);
        imageServiceNoSqlImpl = new ImageServiceNoSqlImpl(repo,imageEntityMapper);

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
        imageDocument = new ImageDocument(fileName,status,privacy,uid,stringData);
        listImageDocument = new ArrayList<>();
        listImageDocument.add(imageDocument);
        optionalImageDocument = Optional.of(imageDocument);

        //mapper mocks
        when(imageEntityMapper.entityToModel(any(ImageDocument.class))).thenReturn(imageModel);
        when(imageEntityMapper.modelToEntity(any(ImageModel.class))).thenReturn(imageDocument);

        //usecase mocks
        when(repo.save(imageDocument)).thenReturn(imageDocument);
        when(repo.findAll()).thenReturn(listImageDocument);
        when(repo.getImagesByPrivacy(Privacy.Public)).thenReturn(listImageDocument);
        when(repo.findById(imageId)).thenReturn(optionalImageDocument);
        when(repo.existsById(imageId)).thenReturn(true);
        when(repo.existsById(invalidImageId)).thenReturn(false);
    }

    @Test
    public void save_Success() {
        //act
        ImageModel testImageModel = imageServiceNoSqlImpl.save(imageModel);
        //assert
        assertNotNull(testImageModel);
        assertEquals(uid,testImageModel.getUid());
        assertEquals(fileName,testImageModel.getFileName());
        assertEquals(data,testImageModel.getData());
        assertEquals(status,testImageModel.getStatus());
        assertEquals(privacy,testImageModel.getPrivacy());
        assertTrue(testImageModel.getCreatedTime().before(new Date()) || testImageModel.getCreatedTime().equals(new Date()));
        assertTrue(testImageModel.getUpdatedTime().after(createdTime) || testImageModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getAll_Success() {
        //act
        List<ImageModel> testImageModelList = imageServiceNoSqlImpl.getAll();
        ImageModel testImageModel = testImageModelList.get(0);
        //assert
        assertEquals(testImageModelList.size(),1);
        assertNotNull(testImageModel);
        assertEquals(uid,testImageModel.getUid());
        assertEquals(fileName,testImageModel.getFileName());
        assertEquals(data,testImageModel.getData());
        assertEquals(status,testImageModel.getStatus());
        assertEquals(privacy,testImageModel.getPrivacy());
        assertTrue(testImageModel.getCreatedTime().before(new Date()) || testImageModel.getCreatedTime().equals(new Date()));
        assertTrue(testImageModel.getUpdatedTime().after(createdTime) || testImageModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getAllPublicImages_Success() {
        //act
        List<ImageModel> testImageModelList = imageServiceNoSqlImpl.getAllPublicImages();
        ImageModel testImageModel = testImageModelList.get(0);
        //assert
        assertEquals(testImageModelList.size(),1);
        assertNotNull(testImageModel);
        assertEquals(uid,testImageModel.getUid());
        assertEquals(fileName,testImageModel.getFileName());
        assertEquals(data,testImageModel.getData());
        assertEquals(status,testImageModel.getStatus());
        assertEquals(privacy,testImageModel.getPrivacy());
        assertTrue(testImageModel.getCreatedTime().before(new Date()) || testImageModel.getCreatedTime().equals(new Date()));
        assertTrue(testImageModel.getUpdatedTime().after(createdTime) || testImageModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getById_Success() {
        //act
        Optional<ImageModel> testImageModelOptional = imageServiceNoSqlImpl.getById(imageId);
        ImageModel testImageModel = testImageModelOptional.get();
        //assert
        assertTrue(testImageModelOptional.isPresent());
        assertNotNull(testImageModel);
        assertEquals(uid,testImageModel.getUid());
        assertEquals(fileName,testImageModel.getFileName());
        assertEquals(data,testImageModel.getData());
        assertEquals(status,testImageModel.getStatus());
        assertEquals(privacy,testImageModel.getPrivacy());
        assertTrue(testImageModel.getCreatedTime().before(new Date()) || testImageModel.getCreatedTime().equals(new Date()));
        assertTrue(testImageModel.getUpdatedTime().after(createdTime) || testImageModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getById_invalidImageId() {
        //act
        Optional<ImageModel> testImageModelOptional = imageServiceNoSqlImpl.getById(invalidImageId);
        //assert
        assertFalse(testImageModelOptional.isPresent());
    }

    @Test
    public void existsById_Success() {
        //assert
        assertTrue(imageServiceNoSqlImpl.existsById(imageId));
    }

    @Test
    public void existsById_invalidImageId() {
        //assert
        assertFalse(imageServiceNoSqlImpl.existsById(invalidImageId));
    }
}
