package com.manuscript.rest.service;

import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.usecase.custom.image.*;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageServiceTests {

    //test classes
    private ImageServiceImpl imageServiceImpl;
    private IRestMapper<ImageInfoModel, ImageInfoRequest> imageRequestMapper;
    private IRestMapper<ImageInfoModel, ImageInfoResponse> imageResponseMapper;
    private ICreateImage createImageUseCase;
    private IGetAllImages getAllImagesUseCase;
    private IGetByIdImageInfo getByIdImageUseCase;
    private IUpdateImage updateImageUseCase;
    private IGetAllPublicImages getAllPublicImages;
    private ImageInfoRequest imageInfoRequest;
    private ImageInfoResponse imageInfoResponse;
    private ImageInfoModel imageInfoModel;

    //test data


    @BeforeAll
    @SuppressWarnings("unchecked")
    public void setup(){
        //service setup
        imageRequestMapper = (IRestMapper<ImageInfoModel, ImageInfoRequest>) Mockito.mock(IRestMapper.class);
        imageResponseMapper = (IRestMapper<ImageInfoModel, ImageInfoResponse>) Mockito.mock(IRestMapper.class);
        createImageUseCase = Mockito.mock(ICreateImage.class);
        getAllImagesUseCase = Mockito.mock(IGetAllImages.class);
        getByIdImageUseCase = Mockito.mock(IGetByIdImageInfo.class);
        updateImageUseCase = Mockito.mock(IUpdateImage.class);
        getAllPublicImages = Mockito.mock(IGetAllPublicImages.class);
        imageServiceImpl = new ImageServiceImpl(imageRequestMapper,imageResponseMapper,createImageUseCase,getAllImagesUseCase,getByIdImageUseCase,updateImageUseCase,getAllPublicImages);

        //data setup

        //date setup
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date createdTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 3);
        Date updatedTime = cal.getTime();

        //failcase data setup

        //mapper mocks
        when(imageRequestMapper.restToModel(any(ImageInfoRequest.class))).thenReturn(imageInfoModel);
        when(imageRequestMapper.modelToRest(any(ImageInfoModel.class))).thenReturn(imageInfoRequest);
        when(imageResponseMapper.restToModel(any(ImageInfoResponse.class))).thenReturn(imageInfoModel);
        when(imageResponseMapper.modelToRest(any(ImageInfoModel.class))).thenReturn(imageInfoResponse);

        //mock return data
        List<ImageInfoModel> listImageInfoModel = new ArrayList<>();
        listImageInfoModel.add(imageInfoModel);
        Optional<ImageInfoModel> optionalImageModel = Optional.of(imageInfoModel);

        //usecase mocks
        when(createImageUseCase.create(any(ImageInfoModel.class))).thenReturn(imageInfoModel);
        when(getAllImagesUseCase.getAll()).thenReturn(listImageInfoModel);
        when(getByIdImageUseCase.getById(any(UUID.class))).thenReturn(optionalImageModel);
        when(updateImageUseCase.update(any(ImageInfoModel.class))).thenReturn(imageInfoModel);
        when(getAllPublicImages.getAllPublicImages()).thenReturn(listImageInfoModel);
    }

//    @BeforeEach
//    public void beforeEach() {
//        //test model setup
//        imageModel = new ImageModel(imageId,uid,fileName,status,data);
//
//        //test request setup
//        imageRequest = new ImageResponse(imageId,userId,uid,fileName,data,status,createdTime,updatedTime);
//
//        //test response setup
//        imageResponse = new ImageResponse(imageId,userId,uid,fileName,data,status,createdTime,updatedTime);
//    }

    @Test
    public void uploadDocument_Success() {}
}
