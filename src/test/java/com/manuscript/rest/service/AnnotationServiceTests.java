package com.manuscript.rest.service;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.exceptions.NoAlgorithmFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.annotation.*;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.forms.request.AnnotationRequest;
import com.manuscript.rest.forms.response.AlgorithmResponse;
import com.manuscript.rest.forms.response.AnnotationResponse;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnnotationServiceTests {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private AnnotationServiceImpl annotationService;
    private IImageService imageService;
    private IAlgorithmService algorithmService;
    private IRestMapper<AnnotationModel, AnnotationRequest> annotationRequestMapper;
    private IRestMapper<AnnotationModel, AnnotationResponse> annotationResponseMapper;
    private ICreateAnnotation createAnnotationUseCase;
    private IGetByIdAnnotation getByIdAnnotationsUseCase;
    private IGetAllByImageIdAnnotations getAllByImageIdAnnotationsUseCase;
    private IUpdateAnnotation updateAnnotationUseCase;
    private IDeleteByIdAnnotation deleteByIdAnnotationUseCase;
    private final UUID id = UUID.randomUUID();
    private final UUID invalidId = NIL;
    private final String uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
    private final String invalidUid = "uid";
    private final UUID imageId = UUID.randomUUID();
    private final UUID invalidImageId = NIL;
    private final UUID manualAlgorithmId = NIL;
    private final UUID invalidAlgorithmId = UUID.fromString("12f8f2bb-f9b0-4316-8ece-f9e84aa15603");
    private final String content = "content";
    private final int startX = 0;
    private final int startY = 0;
    private final int endX = 5;
    private final int endY = 5;
    private Date createdTime;
    private Date updatedTime;
    private AnnotationRequest newAnnotationRequest;
    private AnnotationRequest annotationRequest;
    private AnnotationModel annotationModel;
    private final UUID userId = UUID.randomUUID();
    private final String fileName = "filename";
    private final byte[] data = {0};
    private ImageInfoResponse imageInfoResponse;
    private final String url = "";
    private AlgorithmResponse algorithmResponse;

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

        // set up mocked services
        imageService = Mockito.mock(IImageService.class);
        algorithmService = Mockito.mock(IAlgorithmService.class);
        annotationRequestMapper = (IRestMapper<AnnotationModel, AnnotationRequest>) Mockito.mock(IRestMapper.class);
        annotationResponseMapper = (IRestMapper<AnnotationModel, AnnotationResponse>) Mockito.mock(IRestMapper.class);
        createAnnotationUseCase = Mockito.mock(ICreateAnnotation.class);
        getByIdAnnotationsUseCase = Mockito.mock(IGetByIdAnnotation.class);
        getAllByImageIdAnnotationsUseCase = Mockito.mock(IGetAllByImageIdAnnotations.class);
        updateAnnotationUseCase = Mockito.mock(IUpdateAnnotation.class);
        deleteByIdAnnotationUseCase = Mockito.mock(IDeleteByIdAnnotation.class);

        // set up controller
        annotationService = new AnnotationServiceImpl(imageService, algorithmService, annotationRequestMapper,
                annotationResponseMapper, createAnnotationUseCase, getByIdAnnotationsUseCase,
                getAllByImageIdAnnotationsUseCase, updateAnnotationUseCase, deleteByIdAnnotationUseCase);
    }

    @BeforeEach
    public void beforeEach() {
        // set up annotations requests
        this.newAnnotationRequest = new AnnotationRequest(null, uid, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY);
        this.annotationRequest = new AnnotationRequest(id, uid, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY);

        // set up annotation model
        this.annotationModel = AnnotationModel.builder()
                .id(id).uid(uid).imageId(imageId).algorithmId(manualAlgorithmId)
                .content(content)
                .startX(startX).startY(startY).endX(endX).endY(endY)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        // set up image and algorithm responses
        this.imageInfoResponse = new ImageInfoResponse(imageId, userId, uid, fileName, data, Status.Enabled, Privacy.Public,createdTime, updatedTime);
        this.algorithmResponse = new AlgorithmResponse(manualAlgorithmId, uid, url, createdTime, updatedTime);
    }

    ////--------------------------------------- 'create' tests

    @Test
    public void createSuccess() {
        //set up
        AnnotationResponse newAnnotation = new AnnotationResponse(id, uid, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);

        ////mock mappers, assisting services and create use case
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);
        when(algorithmService.getById(any(UUID.class))).thenReturn(algorithmResponse);
        when(annotationRequestMapper.restToModel(any(AnnotationRequest.class))).thenReturn(annotationModel);
        when(createAnnotationUseCase.create(any(AnnotationModel.class))).thenReturn(annotationModel);
        when(annotationResponseMapper.modelToRest(any(AnnotationModel.class))).thenReturn(newAnnotation);

        //act
        AnnotationResponse annotationResponse = annotationService.create(newAnnotationRequest);

        //assert
        assertNotNull(annotationResponse);
        assertEquals(uid, annotationResponse.getUid());
        assertEquals(imageId, annotationResponse.getImageId());
        assertEquals(manualAlgorithmId, annotationResponse.getAlgorithmId());
        assertEquals(content, annotationResponse.getContent());
        assertEquals(startX, annotationResponse.getStartX());
        assertEquals(startY, annotationResponse.getStartY());
        assertEquals(endX, annotationResponse.getEndX());
        assertEquals(endY, annotationResponse.getEndY());
        assertTrue(annotationResponse.getCreatedTime().before(new Date())
                || annotationResponse.getCreatedTime().equals(new Date()));
        assertTrue(annotationResponse.getUpdatedTime().after(createdTime)
                || annotationResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void createInvalidUid() {
        //set up
        newAnnotationRequest.setUid(invalidUid);

        ////mock image service
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);

        //act
        //assert
        assertThrows(UnauthorizedException.class, () -> annotationService.create(newAnnotationRequest));
    }

    @Test
    public void createInvalidDocumentId() {
        //set up
        newAnnotationRequest.setImageId(invalidImageId);

        ////mock image service
        when(imageService.getById(any(UUID.class))).thenThrow(NoSuchElementException.class);

        //act
        //assert
        assertThrows(NoSuchElementException.class, () -> annotationService.create(newAnnotationRequest));
    }

    @Test
    public void createInvalidAlgorithmId() {
        //set up
        newAnnotationRequest.setAlgorithmId(invalidAlgorithmId);

        ////mock image and algorithm services
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);
        when(algorithmService.getById(any(UUID.class))).thenThrow(NoAlgorithmFoundException.class);

        //act
        //assert
        assertThrows(NoAlgorithmFoundException.class, () -> annotationService.create(newAnnotationRequest));
    }


    ////--------------------------------------- 'update' tests

    @Test
    public void updateSuccess() {
        //set up
        AnnotationResponse updatedAnnotation = new AnnotationResponse(id, uid, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);

        ////mock mappers, assisting services and create use case
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);
        when(algorithmService.getById(any(UUID.class))).thenReturn(algorithmResponse);
        when(annotationRequestMapper.restToModel(any(AnnotationRequest.class))).thenReturn(annotationModel);
        when(updateAnnotationUseCase.update(any(AnnotationModel.class))).thenReturn(annotationModel);
        when(annotationResponseMapper.modelToRest(any(AnnotationModel.class))).thenReturn(updatedAnnotation);

        //act
        AnnotationResponse annotationResponse = annotationService.update(annotationRequest);

        //assert
        assertNotNull(annotationResponse);
        assertEquals(uid, annotationResponse.getUid());
        assertEquals(imageId, annotationResponse.getImageId());
        assertEquals(manualAlgorithmId, annotationResponse.getAlgorithmId());
        assertEquals(content, annotationResponse.getContent());
        assertEquals(startX, annotationResponse.getStartX());
        assertEquals(startY, annotationResponse.getStartY());
        assertEquals(endX, annotationResponse.getEndX());
        assertEquals(endY, annotationResponse.getEndY());
        assertTrue(annotationResponse.getCreatedTime().before(new Date())
                || annotationResponse.getCreatedTime().equals(new Date()));
        assertTrue(annotationResponse.getUpdatedTime().after(createdTime));
    }

    @Test
    public void updateInvalidUid() {
        //set up
        annotationRequest.setUid(invalidUid);

        ////mock image service
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);

        //act
        //assert
        assertThrows(UnauthorizedException.class, () -> annotationService.update(annotationRequest));
    }

    @Test
    public void updateInvalidDocumentId() {
        //set up
        annotationRequest.setImageId(invalidImageId);

        ////mock image service
        when(imageService.getById(any(UUID.class))).thenThrow(NoSuchElementException.class);

        //act
        //assert
        assertThrows(NoSuchElementException.class, () -> annotationService.update(annotationRequest));
    }

    @Test
    public void updateInvalidAlgorithmId() {
        //set up
        annotationRequest.setAlgorithmId(invalidAlgorithmId);

        ////mock image and algorithm services
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);
        when(algorithmService.getById(any(UUID.class))).thenThrow(NoAlgorithmFoundException.class);

        //act
        //assert
        assertThrows(NoAlgorithmFoundException.class, () -> annotationService.update(annotationRequest));
    }

    ////--------------------------------------- 'getAllByImageId' tests

    @Test
    public void getAllByImageIdSuccess() {
        //set up
        AnnotationResponse annotationResponse = new AnnotationResponse(id, uid, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);
        List<AnnotationModel> annotationModels = new ArrayList<>();

        ////mock mappers, assisting services and create use case
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);
        when(getAllByImageIdAnnotationsUseCase.getAllByImageId(any(UUID.class))).thenReturn(annotationModels);
        when(annotationResponseMapper.modelToRest(any(AnnotationModel.class))).thenReturn(annotationResponse);

        //act
        List<AnnotationResponse> annotationResponses =
                annotationService.getAllByImageId(annotationRequest.getImageId(), annotationRequest.getUid());

        //assert
        assertNotNull(annotationResponses);
    }

    @Test
    public void getAllByImageIdInvalidUid() {
        //set up
        annotationRequest.setUid(invalidUid);

        /////mock image service
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);

        //act
        //assert
        assertThrows(UnauthorizedException.class, () ->
                annotationService.getAllByImageId(annotationRequest.getImageId(), annotationRequest.getUid()));
    }

    @Test
    public void getAllByImageIdInvalidDocumentId() {
        //set up
        annotationRequest.setImageId(invalidImageId);

        ////mock image service
        when(imageService.getById(any(UUID.class))).thenThrow(NoSuchElementException.class);

        //act
        //assert
        assertThrows(NoSuchElementException.class, () ->
                annotationService.getAllByImageId(annotationRequest.getImageId(), annotationRequest.getUid()));
    }

    ////--------------------------------------- 'delete' tests

    @Test
    public void deleteSuccess() {
        //set up
        ////mock image service
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);

        //act
        annotationService.delete(annotationRequest.getId(), annotationRequest.getImageId(), annotationRequest.getUid());
    }

    @Test
    //Still expect this to not throw any exception
    public void deleteInvalidIdSuccess() {
        //set up
        annotationRequest.setId(invalidId);

        ////mock image service
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);

        //act
        annotationService.delete(annotationRequest.getId(), annotationRequest.getImageId(), annotationRequest.getUid());
    }

    @Test
    public void deleteInvalidUid() {
        //set up
        annotationRequest.setUid(invalidUid);

        ////mock image service
        when(imageService.getById(any(UUID.class))).thenReturn(imageInfoResponse);

        //act
        //assert
        assertThrows(UnauthorizedException.class, () ->
                annotationService.delete(annotationRequest.getId(), annotationRequest.getImageId(),
                        annotationRequest.getUid()));
    }

    @Test
    public void deleteInvalidDocumentId() {
        //set up
        annotationRequest.setImageId(invalidImageId);

        ////mock image service
        when(imageService.getById(any(UUID.class))).thenThrow(NoSuchElementException.class);

        //act
        //assert
        assertThrows(NoSuchElementException.class, () ->
                annotationService.delete(annotationRequest.getId(), annotationRequest.getImageId(),
                        annotationRequest.getUid()));
    }
}
