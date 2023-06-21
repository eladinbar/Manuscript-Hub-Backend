package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.image.*;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import com.manuscript.rest.forms.response.UserResponse;
import com.manuscript.rest.mapping.IRestMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageServiceTests {
    //test classes
    private ImageServiceImpl imageService;
    private IUserService userService;
    private IAnnotationService annotationService;
    private IRestMapper<ImageInfoModel, ImageInfoRequest> imageInfoRequestMapper;
    private IRestMapper<ImageInfoModel, ImageInfoResponse> imageInfoResponseMapper;
    private IRestMapper<ImageDataModel, ImageDataRequest> imageDataRequestMapper;
    private IRestMapper<ImageDataModel, ImageDataResponse> imageDataResponseMapper;
    private ICreateImage createImageInfoUseCase;
    private ICreateImageData createImageDataUseCase;
    private IUpdateImage updateImageUseCase;
    private IGetByIdImageInfo getByIdImageInfoUseCase;
    private IGetByIdImageData getByIdImageDataUseCase;
    private IGetAllByImageIdImageDatas getAllByImageIdImagesDataUseCase;
    private IGetAllByUidImages getAllByUidImagesUseCase;
    private IGetAllPublicImages getAllPublicImagesUseCase;
    private IGetAllSharedImages getAllSharedImagesUseCase;
    private IDeleteByIdImageInfo deleteByIdImageInfoUseCase;
    private IDeleteByIdImageData deleteByIdImageDataUseCase;
    private IGetImageInfoByTextSearch getImageInfoByTextSearchUseCase;
    private ITransferOwnership transferOwnershipUseCase;
    private IGetAllEmailsByImageInfoId getAllEmailsByImageInfoIdUseCase;
    private ImageInfoRequest imageInfoRequest;
    private ImageInfoResponse imageInfoResponse;
    private ImageDataRequest imageDataRequest;
    private ImageDataResponse imageDataResponse;
    private ImageInfoModel imageInfoModel;
    private ImageDataModel imageDataModel;
    private List<ImageInfoModel> imageInfoModels;
    private List<ImageDataModel> imageDataModels;
    private List<ImageInfoResponse> imageInfoResponses;
    private List<ImageDataResponse> imageDataResponses;

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
    private final Privacy privacy = Privacy.Private;
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
    @SuppressWarnings("unchecked")
    public void setup() {
        //service setup
        userService = Mockito.mock(IUserService.class);
        annotationService = Mockito.mock(IAnnotationService.class);
        imageInfoRequestMapper = (IRestMapper<ImageInfoModel, ImageInfoRequest>) Mockito.mock(IRestMapper.class);
        imageInfoResponseMapper = (IRestMapper<ImageInfoModel, ImageInfoResponse>) Mockito.mock(IRestMapper.class);
        imageDataRequestMapper = (IRestMapper<ImageDataModel, ImageDataRequest>) Mockito.mock(IRestMapper.class);
        imageDataResponseMapper = (IRestMapper<ImageDataModel, ImageDataResponse>) Mockito.mock(IRestMapper.class);
        createImageInfoUseCase = Mockito.mock(ICreateImage.class);
        createImageDataUseCase = Mockito.mock(ICreateImageData.class);
        updateImageUseCase = Mockito.mock(IUpdateImage.class);
        getByIdImageInfoUseCase = Mockito.mock(IGetByIdImageInfo.class);
        getByIdImageDataUseCase = Mockito.mock(IGetByIdImageData.class);
        getAllByImageIdImagesDataUseCase = Mockito.mock(IGetAllByImageIdImageDatas.class);
        getAllByUidImagesUseCase = Mockito.mock(IGetAllByUidImages.class);
        getAllPublicImagesUseCase = Mockito.mock(IGetAllPublicImages.class);
        getAllSharedImagesUseCase = Mockito.mock(IGetAllSharedImages.class);
        getAllEmailsByImageInfoIdUseCase = Mockito.mock(IGetAllEmailsByImageInfoId.class);
        deleteByIdImageInfoUseCase = Mockito.mock(IDeleteByIdImageInfo.class);
        deleteByIdImageDataUseCase = Mockito.mock(IDeleteByIdImageData.class);
        getImageInfoByTextSearchUseCase = Mockito.mock(IGetImageInfoByTextSearch.class);
        transferOwnershipUseCase = Mockito.mock(ITransferOwnership.class);

        imageService = new ImageServiceImpl(imageInfoRequestMapper, imageInfoResponseMapper, imageDataRequestMapper,
                imageDataResponseMapper, userService, annotationService,
                createImageInfoUseCase, createImageDataUseCase, updateImageUseCase, getByIdImageInfoUseCase,
                getByIdImageDataUseCase, getAllByImageIdImagesDataUseCase, getAllByUidImagesUseCase,
                getAllPublicImagesUseCase, getAllSharedImagesUseCase, deleteByIdImageInfoUseCase, deleteByIdImageDataUseCase,
                getImageInfoByTextSearchUseCase, transferOwnershipUseCase, getAllEmailsByImageInfoIdUseCase);

        //date setup
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        createdTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 3);
        updatedTime = cal.getTime();
    }

    @BeforeEach
    public void beforeEach() {
        //request setup
        imageInfoRequest = new ImageInfoRequest(imageInfoId, uid, title, author, publicationDate, description,
                tags, sharedUserIds, status, privacy);

        imageDataRequest = new ImageDataRequest(imageDataId, imageInfoId, fileName, data, index);

        //test response setup
        imageInfoResponse = ImageInfoResponse.builder()
                .id(imageInfoId)
                .uid(uid)
                .title(title).author(author).publicationDate(publicationDate).description(description)
                .tags(tags).sharedUserIds(sharedUserIds)
                .status(status).privacy(privacy)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
        testImageInfoResponseList = new ArrayList<>();
        testImageInfoResponseList.add(imageInfoResponse);

        imageDataResponse = ImageDataResponse.builder()
                .id(imageDataId)
                .infoId(imageInfoId)
                .fileName(fileName)
                .data(data)
                .index(index)
                .build();
        testImageDataResponseList = new ArrayList<>();
        testImageDataResponseList.add(imageDataResponse);

        //model setup
        imageInfoModel = ImageInfoModel.builder()
                .id(imageInfoId)
                .uid(uid)
                .title(title)
                .author(author)
                .description(description)
                .publicationDate(publicationDate)
                .status(status)
                .privacy(privacy)
                .tags(tags)
                .sharedUserIds(sharedUserIds)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();
        imageInfoModels = new ArrayList<>();
        imageInfoModels.add(imageInfoModel);

        imageDataModel = ImageDataModel.builder()
                .id(imageDataId)
                .imageId(imageInfoId)
                .fileName(fileName)
                .data(data)
                .index(index)
                .build();
        imageDataModels = new ArrayList<>();
        imageDataModels.add(imageDataModel);

        //set up user response
        UserResponse userResponse = UserResponse.builder()
                .email(email)
                .uid(uid)
                .name("name")
                .phoneNumber("0541234567")
                .role(Role.User).build();

        //test data
        sharedUserIds.add(uid);
        sharedUserEmails[0] = email;

        List<String> sharedUserEmails = new ArrayList<>();
        sharedUserEmails.add(email);

        //mapper mocks
        when(imageInfoRequestMapper.restToModel(any(ImageInfoRequest.class))).thenReturn(imageInfoModel);
        when(imageInfoRequestMapper.modelToRest(any(ImageInfoModel.class))).thenReturn(imageInfoRequest);
        when(imageInfoResponseMapper.restToModel(any(ImageInfoResponse.class))).thenReturn(imageInfoModel);
        when(imageInfoResponseMapper.modelToRest(any(ImageInfoModel.class))).thenReturn(imageInfoResponse);

        when(imageDataRequestMapper.restToModel(any(ImageDataRequest.class))).thenReturn(imageDataModel);
        when(imageDataRequestMapper.modelToRest(any(ImageDataModel.class))).thenReturn(imageDataRequest);
        when(imageDataResponseMapper.restToModel(any(ImageDataResponse.class))).thenReturn(imageDataModel);
        when(imageDataResponseMapper.modelToRest(any(ImageDataModel.class))).thenReturn(imageDataResponse);

        //use case mocks
        when(createImageInfoUseCase.create(any(ImageInfoModel.class))).thenReturn(imageInfoModel);
        when(createImageDataUseCase.create(any(ImageDataModel.class))).thenReturn(imageDataModel);
        when(updateImageUseCase.update(any(ImageInfoModel.class))).thenReturn(imageInfoModel);
        when(getByIdImageInfoUseCase.getById(any(UUID.class))).thenReturn(Optional.of(imageInfoModel));
        when(getByIdImageDataUseCase.getById(any(UUID.class))).thenReturn(Optional.of(imageDataModel));
        when(getAllByImageIdImagesDataUseCase.getAllByImageId(any(UUID.class))).thenReturn(imageDataModels);
        when(getAllByUidImagesUseCase.getAllByUidImageInfos(any(String.class))).thenReturn(imageInfoModels);
        when(getAllPublicImagesUseCase.getAllPublicImages()).thenReturn(imageInfoModels);
        when(getAllSharedImagesUseCase.getAllSharedImages(any(String.class))).thenReturn(imageInfoModels);
        when(getAllEmailsByImageInfoIdUseCase.getAllEmailsByImageInfoIdImpl(any(UUID.class), any(String.class))).thenReturn(sharedUserEmails);

        //service mocks
        when(userService.getByEmail(any(String.class))).thenReturn(userResponse);
    }

    /////////--------------ImageInfo tests----------------------------

    //---------------------saveInfo tests:---------------------
    @Test
    public void saveInfo_success() {
        //act
        ImageInfoResponse testImageResponse = imageService.saveInfo(imageInfoRequest);

        //assert
        assertNotNull(testImageResponse);
        assertEquals(uid, testImageResponse.getUid());
        assertEquals(title, testImageResponse.getTitle());
        assertEquals(author, testImageResponse.getAuthor());
        assertEquals(publicationDate, testImageResponse.getPublicationDate());
        assertEquals(description, testImageResponse.getDescription());
        assertEquals(tags, testImageResponse.getTags());
        assertEquals(sharedUserIds, testImageResponse.getSharedUserIds());
        assertEquals(status, testImageResponse.getStatus());
        assertEquals(privacy, testImageResponse.getPrivacy());
        assertTrue(testImageResponse.getCreatedTime().before(new Date()) ||
                testImageResponse.getCreatedTime().equals(new Date()));
        assertTrue(testImageResponse.getUpdatedTime().after(createdTime) ||
                testImageResponse.getUpdatedTime().equals(createdTime));
    }

    //---------------------updateInfo tests:---------------------
    @Test
    public void updateInfo_Success() {
        //act
        ImageInfoResponse testImageResponse = imageService.updateInfo(imageInfoRequest);

        //assert
        assertNotNull(testImageResponse);
        assertEquals(uid, testImageResponse.getUid());
        assertEquals(title, testImageResponse.getTitle());
        assertEquals(author, testImageResponse.getAuthor());
        assertEquals(publicationDate, testImageResponse.getPublicationDate());
        assertEquals(description, testImageResponse.getDescription());
        assertEquals(tags, testImageResponse.getTags());
        assertEquals(sharedUserIds, testImageResponse.getSharedUserIds());
        assertEquals(status, testImageResponse.getStatus());
        assertEquals(privacy, testImageResponse.getPrivacy());
        assertTrue(testImageResponse.getCreatedTime().before(new Date()) ||
                testImageResponse.getCreatedTime().equals(new Date()));
        assertTrue(testImageResponse.getUpdatedTime().after(createdTime) ||
                testImageResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void updateInfo_invalidUserId() {
        //act
        imageInfoRequest.setUid("");

        //assert
        assertThrows(Exception.class, () -> imageService.updateInfo(imageInfoRequest));
    }

    @Test
    public void updateInfo_invalidStatus() {
        //act
        imageInfoResponse.setStatus(Status.Disabled);

        //assert
        assertThrows(Exception.class, () -> imageService.updateInfo(imageInfoRequest));
    }

    //---------------------shareImage tests:---------------------

    @Test
    public void shareImage_success() {
        //act
        ImageInfoResponse testImageResponse = imageService.shareImage(imageInfoRequest, sharedUserEmails);

        //assert
        assertNotNull(testImageResponse);
        assertEquals(uid, testImageResponse.getUid());
        assertEquals(title, testImageResponse.getTitle());
        assertEquals(author, testImageResponse.getAuthor());
        assertEquals(publicationDate, testImageResponse.getPublicationDate());
        assertEquals(description, testImageResponse.getDescription());
        assertEquals(tags, testImageResponse.getTags());
        assertEquals(sharedUserIds, testImageResponse.getSharedUserIds());
        assertEquals(status, testImageResponse.getStatus());
        assertEquals(privacy, testImageResponse.getPrivacy());
        assertTrue(testImageResponse.getCreatedTime().before(new Date()) ||
                testImageResponse.getCreatedTime().equals(new Date()));
        assertTrue(testImageResponse.getUpdatedTime().after(createdTime) ||
                testImageResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void shareImage_invalidUserId() {
        //act
        imageInfoRequest.setUid("");
        //assert
        assertThrows(Exception.class, () -> imageService.updateInfo(imageInfoRequest));
    }

    @Test
    public void shareImage_invalidStatus() {
        //act
        imageInfoResponse.setStatus(Status.Disabled);
        //assert
        assertThrows(Exception.class, () -> imageService.updateInfo(imageInfoRequest));
    }

    //---------------------getAllSharedEmailsByImageInfoId tests:---------------------

    @Test
    public void getAllSharedEmailsByImageInfoId_success() {
        //set up
        List<String> sharedUserEmails = new ArrayList<>();
        sharedUserEmails.add(email);

        //act
        List<String> returnedEmails = imageService.getAllSharedEmailsByImageInfoId(imageInfoId, uid);

        //assert
        assertEquals(sharedUserEmails, returnedEmails);
    }

    //---------------------getByIdInfo tests:---------------------
    @Test
    public void getImageInfoById_success() {
        //act
        ImageInfoResponse imageInfoResponse = imageService.getByIdInfo(imageInfoId, uid);

        //assert
        assertNotNull(imageInfoResponse);
        assertEquals(uid, imageInfoResponse.getUid());
        assertEquals(title, imageInfoResponse.getTitle());
        assertEquals(author, imageInfoResponse.getAuthor());
        assertEquals(publicationDate, imageInfoResponse.getPublicationDate());
        assertEquals(description, imageInfoResponse.getDescription());
        assertEquals(tags, imageInfoResponse.getTags());
        assertEquals(sharedUserIds, imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getImageInfoById_invalidImageId() {
        //mock
        when(getByIdImageInfoUseCase.getById(any(UUID.class))).thenReturn(Optional.empty());

        //assert
        assertThrows(NoImageFoundException.class, () -> imageService.getByIdInfo(UUID.randomUUID(), uid));
    }

    @Test
    public void getImageInfoById_invalidUid() {
        //assert
        assertThrows(UnauthorizedException.class, () -> imageService.getByIdInfo(imageInfoId, ""));
    }

    //---------------------getAllByUidImageInfos tests:---------------------
    @Test
    public void getAllByUid_Success() {
        //act
        List<ImageInfoResponse> imageInfoResponses = imageService.getAllByUidImageInfos(uid);

        //assert
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
        assertEquals(sharedUserIds, imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(privacy, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getAllByUid_invalidUid() {
        //mock
        when(getAllByUidImagesUseCase.getAllByUidImageInfos(any(String.class))).thenReturn(new ArrayList<>());

        //act
        List<ImageInfoResponse> testImageResponseList = imageService.getAllByUidImageInfos("");

        //assert
        assertEquals(testImageResponseList.size(), 0);
    }

    //---------------------getAllPublicImages tests:---------------------

    @Test
    public void getAllPublicImages_Success() {
        //set up
        testImageInfoResponseList.get(0).setPrivacy(Privacy.Public);

        //act
        List<ImageInfoResponse> imageInfoResponses = imageService.getAllPublicImages();

        //assert
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
        assertEquals(sharedUserIds, imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(Privacy.Public, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    //---------------------getAllSharedImages tests:---------------------
    @Test
    public void getAllSharedImageInfosByUid_success() {
        //set up
        testImageInfoResponseList.get(0).setPrivacy(Privacy.Shared);

        //act
        List<ImageInfoResponse> imageInfoResponses = imageService.getAllSharedImages(uid);

        //assert
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
        assertEquals(sharedUserIds, imageInfoResponse.getSharedUserIds());
        assertEquals(status, imageInfoResponse.getStatus());
        assertEquals(Privacy.Shared, imageInfoResponse.getPrivacy());
        assertTrue(imageInfoResponse.getCreatedTime().before(new Date()) ||
                imageInfoResponse.getCreatedTime().equals(new Date()));
        assertTrue(imageInfoResponse.getUpdatedTime().after(createdTime) ||
                imageInfoResponse.getUpdatedTime().equals(createdTime));
    }

    //---------------------deleteByIdImageInfo tests:---------------------
    @Test
    public void deleteImageInfoById_success() {
        //assert
        imageService.deleteByIdImageInfo(imageInfoId, uid);
    }

    @Test
    public void deleteImageInfoById_InvalidUid_fail() {
        //assert
        assertThrows(UnauthorizedException.class, () -> imageService.deleteByIdImageInfo(imageInfoId, ""));
    }

    /////////--------------ImageData tests----------------------------

    //---------------------getImageDataById tests:---------------------
    @Test
    public void getImageDataById_success() {
        //act
        ImageDataResponse imageDataResponse = imageService.getByIdData(imageDataId, uid);

        //assert
        assertNotNull(imageDataResponse);
        assertEquals(imageDataId, imageDataResponse.getId());
        assertEquals(data, imageDataResponse.getData());
        assertEquals(fileName, imageDataResponse.getFileName());
        assertEquals(index, imageDataResponse.getIndex());
    }


    //---------------------getImageDatasByImageInfoId tests:---------------------
    @Test
    public void getImageDatasByImageInfoId_success() {
        //act
        List<ImageDataResponse> imageDataResponses = imageService.getAllByImageInfoIdImageDatas(imageInfoId, uid);

        //assert
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
        imageService.deleteByIdImageData(imageDataId, uid, true);
    }

    @Test
    public void deleteImageDataById_InvalidUid_fail() {
        //assert
        assertThrows(UnauthorizedException.class, () -> imageService.deleteByIdImageData(imageInfoId, "", true));
    }
}
