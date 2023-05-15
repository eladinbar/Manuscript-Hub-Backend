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

        //failcase data setup

        //mapper mocks
        when(imageRequestMapper.restToModel(any(ImageRequest.class))).thenReturn(imageModel);
        when(imageRequestMapper.modelToRest(any(ImageModel.class))).thenReturn(imageRequest);
        when(imageResponseMapper.restToModel(any(ImageResponse.class))).thenReturn(imageModel);
        when(imageResponseMapper.modelToRest(any(ImageModel.class))).thenReturn(imageResponse);

        //usecase mocks
        when(createImageUseCase.create(any(ImageModel.class))).thenReturn(imageModel);
        when(getAllImagesUseCase.getAll()).thenReturn(listImageModel);
        when(getByIdImageUseCase.getById(any(UUID.class))).thenReturn(optionalImageModel);
        when(updateImageUseCase.update(any(ImageModel.class))).thenReturn(imageModel);
        when(getAllPublicImages.getAllPublicImages()).thenReturn(listImageModel);
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
    }

    @Test
    public void save_Success() {

    }
}
