package com.manuscript.rest.service;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.exceptions.NoAlgorithmFoundException;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.algorithm.ICreateAlgorithm;
import com.manuscript.core.usecase.custom.algorithm.IDeleteByIdAlgorithm;
import com.manuscript.core.usecase.custom.algorithm.IGetByIdAlgorithm;
import com.manuscript.core.usecase.custom.algorithm.IUpdateAlgorithm;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.AlgorithmRequest;
import com.manuscript.rest.response.AlgorithmResponse;
import com.manuscript.rest.response.UserResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlgorithmServiceTests {
    private AlgorithmServiceImpl algorithmService;
    private IUserService userService;
    private IImageService imageService;
    private IRestMapper<AlgorithmModel, AlgorithmRequest> algorithmRequestMapper;
    private IRestMapper<AlgorithmModel, AlgorithmResponse> algorithmResponseMapper;
    private ICreateAlgorithm createAlgorithmUseCase;
    private IUpdateAlgorithm updateAlgorithmUseCase;
    private IGetByIdAlgorithm getByIdAlgorithmUseCase;
    private IDeleteByIdAlgorithm deleteByIdAlgorithmUseCase;
    private final UUID id = UUID.randomUUID();
    private final UUID invalidId = UUID.randomUUID();
    private final String uid = "uid";
    private final String invalidUid = "invalid uid";
    private final UUID imageId = UUID.randomUUID();
    private final UUID invalidImageId = UUID.randomUUID();
    private final String url = "https://www.algorithm.com";
    private final String invalidUrl = "http:/w..a.com";
    private Date createdTime;
    private Date updatedTime;
    private AlgorithmRequest newAlgorithmRequest;
    private AlgorithmRequest algorithmRequest;
    private AlgorithmModel newAlgorithmModel;
    private AlgorithmModel algorithmModel;
    private AlgorithmResponse algorithmResponse;
    private final String email = "email@gmail.com";
    private final String name = "bob";
    private final String phoneNumber = "0541234567";
    private final String status = "active";
    private final Role userRole = Role.User;
    private final Role developerRole = Role.Developer;
    private UserResponse userResponse;
    private UserResponse developerUserResponse;

    @BeforeAll
    @SuppressWarnings("unchecked")
    public void setUp() {
        // set up time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        this.createdTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 2);
        this.updatedTime = cal.getTime();

        // set up service and mocked assisting services
        userService = Mockito.mock(IUserService.class);
        imageService = Mockito.mock(IImageService.class);
        algorithmRequestMapper = (IRestMapper<AlgorithmModel, AlgorithmRequest>) Mockito.mock(IRestMapper.class);
        algorithmResponseMapper = (IRestMapper<AlgorithmModel, AlgorithmResponse>) Mockito.mock(IRestMapper.class);
        createAlgorithmUseCase = Mockito.mock(ICreateAlgorithm.class);
        updateAlgorithmUseCase = Mockito.mock(IUpdateAlgorithm.class);
        getByIdAlgorithmUseCase = Mockito.mock(IGetByIdAlgorithm.class);
        deleteByIdAlgorithmUseCase = Mockito.mock(IDeleteByIdAlgorithm.class);
        algorithmService = new AlgorithmServiceImpl(userService, imageService, algorithmRequestMapper, algorithmResponseMapper,
                createAlgorithmUseCase, updateAlgorithmUseCase, getByIdAlgorithmUseCase, deleteByIdAlgorithmUseCase);
    }

    @BeforeEach
    public void beforeEach() {
        // set up algorithm requests
        this.newAlgorithmRequest = new AlgorithmRequest(null, uid, imageId, url);
        this.algorithmRequest = new AlgorithmRequest(id, uid, imageId, url);

        // set up algorithm response
        this.algorithmResponse = new AlgorithmResponse(id, uid, url, createdTime, updatedTime);

        // set up algorithm models
        this.newAlgorithmModel = AlgorithmModel.builder()
                .id(null).uid(uid)
                .url(url)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
        this.algorithmModel = AlgorithmModel.builder()
                .id(id).uid(uid)
                .url(url)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        // set up user responses
        this.userResponse = new UserResponse(id, uid, email, name, phoneNumber, status, userRole,
                createdTime, updatedTime);
        this.developerUserResponse = new UserResponse(id, uid, email, name, phoneNumber, status, developerRole,
                createdTime, updatedTime);
    }

    ////--------------------------------------- 'create' tests

    @Test
    public void createSuccess() {
        //set up
        AlgorithmResponse newAlgorithm = new AlgorithmResponse(id, uid, url, createdTime, updatedTime);

        ////mock mappers, assisting services and use cases
        when(userService.getByUid(any(String.class))).thenReturn(developerUserResponse);
        when(algorithmRequestMapper.restToModel(any(AlgorithmRequest.class))).thenReturn(newAlgorithmModel);
        when(createAlgorithmUseCase.create(any(AlgorithmModel.class))).thenReturn(algorithmModel);
        when(algorithmResponseMapper.modelToRest(any(AlgorithmModel.class))).thenReturn(newAlgorithm);

        //act
        AlgorithmResponse algorithmResponse = algorithmService.create(newAlgorithmRequest);

        //assert
        assertNotNull(algorithmResponse);
        assertEquals(uid, algorithmResponse.getUid());
        assertEquals(url, algorithmResponse.getUrl());
        assertTrue(algorithmResponse.getCreatedTime().before(new Date())
                || algorithmResponse.getCreatedTime().equals(new Date()));
        assertTrue(algorithmResponse.getUpdatedTime().after(createdTime)
                || algorithmResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void createInvalidUid() {
        //set up
        newAlgorithmRequest.setUid(invalidUid);

        ////mock user service
        when(userService.getByUid(any(String.class))).thenThrow(NoUserFoundException.class);

        //act
        //assert
        assertThrows(NoUserFoundException.class, () -> algorithmService.create(newAlgorithmRequest));
    }

    @Test
    public void createUnauthorizedUserRole() {
        //set up
        ////mock user service
        when(userService.getByUid(any(String.class))).thenReturn(userResponse);

        //act
        //assert
        assertThrows(UnauthorizedException.class, () -> algorithmService.create(newAlgorithmRequest));
    }

    @Test
    public void createInvalidUrl() {
        //TODO
    }


    ////--------------------------------------- 'update' tests

    @Test
    public void updateSuccess() {
        //set up
        AlgorithmResponse updatedAlgorithm = new AlgorithmResponse(id, uid, url, createdTime, updatedTime);

        Optional<AlgorithmModel> optAlgorithmModel = Optional.of(algorithmModel);

        ////mock mappers, assisting services and use cases
        when(getByIdAlgorithmUseCase.getById(any(UUID.class))).thenReturn(optAlgorithmModel);
        when(userService.getByUid(any(String.class))).thenReturn(developerUserResponse);
        when(algorithmRequestMapper.restToModel(any(AlgorithmRequest.class))).thenReturn(algorithmModel);
        when(updateAlgorithmUseCase.update(any(AlgorithmModel.class))).thenReturn(algorithmModel);
        when(algorithmResponseMapper.modelToRest(any(AlgorithmModel.class))).thenReturn(updatedAlgorithm);

        //act
        AlgorithmResponse algorithmResponse = algorithmService.update(algorithmRequest);

        //assert
        assertNotNull(algorithmResponse);
        assertEquals(uid, algorithmResponse.getUid());
        assertEquals(url, algorithmResponse.getUrl());
        assertTrue(algorithmResponse.getCreatedTime().before(new Date())
                || algorithmResponse.getCreatedTime().equals(new Date()));
        assertTrue(algorithmResponse.getUpdatedTime().after(createdTime)
                || algorithmResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void updateInvalidId() {
        //set up
        algorithmRequest.setId(invalidId);

        Optional<AlgorithmModel> optAlgorithmModel = Optional.empty();

        ////mock use case
        when(getByIdAlgorithmUseCase.getById(any(UUID.class))).thenReturn(optAlgorithmModel);

        //act
        //assert
        assertThrows(NoAlgorithmFoundException.class, () -> algorithmService.update(algorithmRequest));
    }

    @Test
    public void updateUnauthorizedUid() {
        //set up
        algorithmRequest.setUid(invalidUid);

        Optional<AlgorithmModel> optAlgorithmModel = Optional.of(algorithmModel);

        ////mock use case, user service and mapper
        when(getByIdAlgorithmUseCase.getById(any(UUID.class))).thenReturn(optAlgorithmModel);
        when(userService.getByUid(any(String.class))).thenReturn(developerUserResponse);
        when(algorithmResponseMapper.modelToRest(any(AlgorithmModel.class))).thenReturn(algorithmResponse);

        //act
        //assert
        assertThrows(UnauthorizedException.class, () -> algorithmService.update(algorithmRequest));
    }

    @Test
    public void updateUnauthorizedUserRole() {
        //set up
        Optional<AlgorithmModel> optAlgorithmModel = Optional.of(algorithmModel);

        ////mock use case, user service and mapper
        when(getByIdAlgorithmUseCase.getById(any(UUID.class))).thenReturn(optAlgorithmModel);
        when(userService.getByUid(any(String.class))).thenReturn(userResponse);
        when(algorithmResponseMapper.modelToRest(any(AlgorithmModel.class))).thenReturn(algorithmResponse);

        //act
        //assert
        assertThrows(UnauthorizedException.class, () -> algorithmService.update(algorithmRequest));
    }

    @Test
    public void updateInvalidUrl() {
        //TODO
    }

    ////--------------------------------------- 'getById' tests

    @Test
    public void getByIdSuccess() {
        //set up
        Optional<AlgorithmModel> optionalModel = Optional.of(algorithmModel);

        ////mock mappers and assisting services
        when(getByIdAlgorithmUseCase.getById(any(UUID.class))).thenReturn(optionalModel);
        when(algorithmResponseMapper.modelToRest(any(AlgorithmModel.class))).thenReturn(algorithmResponse);

        //act
        AlgorithmResponse algorithmResponse = algorithmService.getById(id);

        //assert
        assertNotNull(algorithmResponse);
        assertEquals(algorithmModel.getId(), algorithmResponse.getId());
        assertEquals(algorithmModel.getUid(), algorithmResponse.getUid());
        assertEquals(algorithmModel.getUrl(), algorithmResponse.getUrl());
        assertTrue(algorithmResponse.getCreatedTime().before(new Date())
                || algorithmResponse.getCreatedTime().equals(new Date()));
        assertTrue(algorithmResponse.getUpdatedTime().after(createdTime)
                || algorithmResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getByIdInvalidId() {
        //set up
        Optional<AlgorithmModel> optionalModel = Optional.empty();

        ////mock get by id use case
        when(getByIdAlgorithmUseCase.getById(any(UUID.class))).thenReturn(optionalModel);

        //act
        //assert
        assertThrows(NoAlgorithmFoundException.class, () -> algorithmService.getById(invalidId));
    }

    ////--------------------------------------- 'delete' tests

    @Test
    public void deleteSuccess() {
        //set up
        Optional<AlgorithmModel> optAlgorithmModel = Optional.of(algorithmModel);

        ////mock image service
        when(getByIdAlgorithmUseCase.getById(any(UUID.class))).thenReturn(optAlgorithmModel);
        when(userService.getByUid(any(String.class))).thenReturn(developerUserResponse);

        //act
        algorithmService.delete(id, uid);
    }

    @Test
    public void deleteInvalidId() {
        //set up
        Optional<AlgorithmModel> optAlgorithmModel = Optional.empty();

        ////mock image service
        when(getByIdAlgorithmUseCase.getById(any(UUID.class))).thenReturn(optAlgorithmModel);
        when(userService.getByUid(any(String.class))).thenReturn(developerUserResponse);

        //act
        assertThrows(NoAlgorithmFoundException.class, () -> algorithmService.delete(invalidId, uid));
    }

    @Test
    public void deleteInvalidUid() {
        //set up
        Optional<AlgorithmModel> optAlgorithmModel = Optional.of(algorithmModel);

        ////mock image service
        when(getByIdAlgorithmUseCase.getById(any(UUID.class))).thenReturn(optAlgorithmModel);
        when(userService.getByUid(any(String.class))).thenReturn(developerUserResponse);

        //act
        assertThrows(UnauthorizedException.class, () -> algorithmService.delete(id, invalidUid));
    }

    @Test
    public void deleteUnauthorizedRole() {
        //set up
        Optional<AlgorithmModel> optAlgorithmModel = Optional.of(algorithmModel);

        ////mock image service
        when(getByIdAlgorithmUseCase.getById(any(UUID.class))).thenReturn(optAlgorithmModel);
        when(userService.getByUid(any(String.class))).thenReturn(userResponse);

        //act
        assertThrows(UnauthorizedException.class, () -> algorithmService.delete(id, uid));
    }
}
