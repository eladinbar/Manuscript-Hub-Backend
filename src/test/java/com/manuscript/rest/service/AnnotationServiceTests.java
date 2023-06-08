package com.manuscript.rest.service;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.usecase.custom.annotation.*;
import com.manuscript.rest.forms.request.AnnotationRequest;
import com.manuscript.rest.forms.response.AnnotationResponse;
import com.manuscript.rest.mapping.IRestMapper;
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
    private IRestMapper<AnnotationModel, AnnotationRequest> annotationRequestMapper;
    private IRestMapper<AnnotationModel, AnnotationResponse> annotationResponseMapper;
    private ICreateAnnotation createAnnotationUseCase;
    private IUpdateAnnotation updateAnnotationUseCase;
    private IGetByIdAnnotation getByIdAnnotationsUseCase;
    private IGetAllByImageIdAnnotations getAllByImageIdAnnotationsUseCase;
    private IDeleteByIdAnnotation deleteByIdAnnotationUseCase;
    private IDeleteAllByImageDataIdAnnotations deleteAllByImageDataIdAnnotationsUseCase;
    private final UUID id = UUID.randomUUID();
    private final UUID invalidId = NIL;
    private final String uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
    private final String invalidUid = "uid";
    private final UUID imageInfoId = UUID.randomUUID();
    private final UUID imageDataId = UUID.randomUUID();
    private final UUID invalidImageInfoId = NIL;
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
    private final String title = "title";
    private final byte[] data = {0};
    private final int index = 0;

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
        annotationRequestMapper = (IRestMapper<AnnotationModel, AnnotationRequest>) Mockito.mock(IRestMapper.class);
        annotationResponseMapper = (IRestMapper<AnnotationModel, AnnotationResponse>) Mockito.mock(IRestMapper.class);
        createAnnotationUseCase = Mockito.mock(ICreateAnnotation.class);
        updateAnnotationUseCase = Mockito.mock(IUpdateAnnotation.class);
        getByIdAnnotationsUseCase = Mockito.mock(IGetByIdAnnotation.class);
        getAllByImageIdAnnotationsUseCase = Mockito.mock(IGetAllByImageIdAnnotations.class);
        deleteByIdAnnotationUseCase = Mockito.mock(IDeleteByIdAnnotation.class);
        deleteAllByImageDataIdAnnotationsUseCase = Mockito.mock(IDeleteAllByImageDataIdAnnotations.class);

        // set up service
        annotationService = new AnnotationServiceImpl(annotationRequestMapper, annotationResponseMapper,
                createAnnotationUseCase, updateAnnotationUseCase, getByIdAnnotationsUseCase,
                getAllByImageIdAnnotationsUseCase, deleteByIdAnnotationUseCase, deleteAllByImageDataIdAnnotationsUseCase);
    }

    @BeforeEach
    public void beforeEach() {
        // set up annotations requests
        this.newAnnotationRequest = new AnnotationRequest(null, uid, imageInfoId, manualAlgorithmId,
                content, startX, startY, endX, endY);
        this.annotationRequest = new AnnotationRequest(id, uid, imageInfoId, manualAlgorithmId,
                content, startX, startY, endX, endY);

        // set up annotation model
        this.annotationModel = AnnotationModel.builder()
                .id(id).uid(uid).imageDataId(imageInfoId).algorithmId(manualAlgorithmId)
                .content(content)
                .startX(startX).startY(startY).endX(endX).endY(endY)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
    }

    ////--------------------------------------- 'create' tests

    @Test
    public void createSuccess() {
        //set up
        AnnotationResponse newAnnotation = new AnnotationResponse(id, uid, imageInfoId, manualAlgorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);

        ////mock mappers, assisting services and create use case
        when(annotationRequestMapper.restToModel(any(AnnotationRequest.class))).thenReturn(annotationModel);
        when(createAnnotationUseCase.create(any(AnnotationModel.class))).thenReturn(annotationModel);
        when(annotationResponseMapper.modelToRest(any(AnnotationModel.class))).thenReturn(newAnnotation);

        //act
        AnnotationResponse annotationResponse = annotationService.create(newAnnotationRequest);

        //assert
        assertNotNull(annotationResponse);
        assertEquals(uid, annotationResponse.getUid());
        assertEquals(imageInfoId, annotationResponse.getImageDataId());
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

    ////--------------------------------------- 'update' tests

    @Test
    public void updateSuccess() {
        //set up
        AnnotationResponse updatedAnnotation = new AnnotationResponse(id, uid, imageInfoId, manualAlgorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);

        ////mock mappers and update use case
        when(annotationRequestMapper.restToModel(any(AnnotationRequest.class))).thenReturn(annotationModel);
        when(updateAnnotationUseCase.update(any(AnnotationModel.class))).thenReturn(annotationModel);
        when(annotationResponseMapper.modelToRest(any(AnnotationModel.class))).thenReturn(updatedAnnotation);

        //act
        AnnotationResponse annotationResponse = annotationService.update(annotationRequest);

        //assert
        assertNotNull(annotationResponse);
        assertEquals(uid, annotationResponse.getUid());
        assertEquals(imageInfoId, annotationResponse.getImageDataId());
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

    ////--------------------------------------- 'getAllByImageId' tests

    @Test
    public void getAllByImageIdSuccess() {
        //set up
        AnnotationResponse annotationResponse = new AnnotationResponse(id, uid, imageInfoId, manualAlgorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);
        List<AnnotationModel> annotationModels = new ArrayList<>();

        ////mock mappers and getAllByImageId use case
        when(getAllByImageIdAnnotationsUseCase.getAllByImageId(any(UUID.class))).thenReturn(annotationModels);
        when(annotationResponseMapper.modelToRest(any(AnnotationModel.class))).thenReturn(annotationResponse);

        //act
        List<AnnotationResponse> annotationResponses =
                annotationService.getAllByImageDataId(annotationRequest.getImageDataId(), annotationRequest.getUid());

        //assert
        assertNotNull(annotationResponses);
    }

    ////--------------------------------------- 'delete' tests

    @Test
    public void deleteSuccess() {
        //act
        annotationService.deleteById(annotationRequest.getId());
    }

    @Test
    //Still expect this to not throw any exception
    public void deleteInvalidIdSuccess() {
        //set up
        annotationRequest.setId(invalidId);

        //act
        annotationService.deleteById(annotationRequest.getId());
    }

    ////--------------------------------------- 'delete' tests

    @Test
    public void deleteAllByImageDataSuccess() {
        //act
        annotationService.deleteAllByImageDataId(annotationRequest.getImageDataId());
    }
}
