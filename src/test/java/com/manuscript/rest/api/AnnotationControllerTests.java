package com.manuscript.rest.api;

import com.manuscript.rest.request.AnnotationRequest;
import com.manuscript.rest.response.AnnotationResponse;
import com.manuscript.rest.service.IAnnotationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnnotationControllerTests {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private IAnnotationService annotationService;
    private AnnotationController annotationController;
    private final UUID id = UUID.randomUUID();
    private final String userId = "uid";
    private final UUID imageId = UUID.randomUUID();
    private final UUID manualAlgorithmId = NIL;
    private final String content = "content";
    private final int startX = 0;
    private final int startY = 0;
    private final int endX = 5;
    private final int endY = 5;
    private Date createdTime;
    private Date updatedTime;
    private AnnotationRequest newAnnotationRequest;
    private AnnotationRequest annotationRequest;

    @BeforeAll
    public void setUp() {
        // set up time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        this.createdTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 2);
        this.updatedTime = cal.getTime();

        // set up controller and mocked service
        annotationService = Mockito.mock(IAnnotationService.class);
        annotationController = new AnnotationController(annotationService);
    }

    @BeforeEach
    public void beforeEach() {
        this.newAnnotationRequest = new AnnotationRequest(null, userId, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY);
        this.annotationRequest = new AnnotationRequest(id, userId, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY);
    }

    ////--------------------------------------- 'addAnnotation' tests

    @Test
    public void addAnnotationSuccess() {
        //set up
        AnnotationResponse newAnnotation = new AnnotationResponse(id, userId, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);

        ////mock service
        when(annotationService.create(any())).thenReturn(newAnnotation);

        //act
        ResponseEntity<AnnotationResponse> response = annotationController.addAnnotation(newAnnotationRequest);

        //assert
        assertTrue(response.hasBody());
        AnnotationResponse annotationResponse = response.getBody();
        assertNotNull(annotationResponse);
        assertEquals(userId, annotationResponse.getUid());
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
    public void addAnnotationNullUid() {
        //set up
        newAnnotationRequest.setUid(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.addAnnotation(newAnnotationRequest));
    }

    @Test
    public void addAnnotationNullDocumentId() {
        //set up
        newAnnotationRequest.setImageId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.addAnnotation(newAnnotationRequest));
    }

    @Test
    public void addAnnotationNullAlgorithmId() {
        //set up
        newAnnotationRequest.setAlgorithmId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.addAnnotation(newAnnotationRequest));
    }

    @Test
    public void addAnnotationNullContent() {
        //set up
        newAnnotationRequest.setContent(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.addAnnotation(newAnnotationRequest));
    }

    @ParameterizedTest
    @CsvSource({"-1, 0, 0, 0", "0, -1, 0, 0", "0, 0, -1, 0", "0, 0, 0, -1",
            "-1, -1, 0, 0", "-1, 0, -1, 0", "-1, 0, 0, -1", "0, -1, -1, 0", "0, -1, 0, -1", "0, 0, -1, -1",
            "-1, -1, -1, 0", "-1, -1, 0, -1", "-1, 0, -1, -1", "0, -1, -1, -1",
            "-1, -1, -1, -1"})
    public void addAnnotationNegativeCoordinates(int startX, int startY, int endX, int endY) {
        //set up
        newAnnotationRequest.setStartX(startX);
        newAnnotationRequest.setStartY(startY);
        newAnnotationRequest.setEndX(endX);
        newAnnotationRequest.setEndY(endY);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.addAnnotation(newAnnotationRequest));
    }

    ////--------------------------------------- 'updateAnnotation' tests

    @Test
    public void updateAnnotationSuccess() {
        //set up
        AnnotationResponse updatedAnnotation = new AnnotationResponse(id, userId, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);

        ////mock service
        when(annotationService.update(any())).thenReturn(updatedAnnotation);

        //act
        ResponseEntity<AnnotationResponse> response = annotationController.updateAnnotation(annotationRequest);

        //assert
        assertTrue(response.hasBody());
        AnnotationResponse annotationResponse = response.getBody();
        assertNotNull(annotationResponse);
        assertEquals(userId, annotationResponse.getUid());
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
    public void updateAnnotationNullId() {
        //set up
        annotationRequest.setId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.updateAnnotation(annotationRequest));
    }

    @Test
    public void updateAnnotationNullUid() {
        //set up
        annotationRequest.setUid(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.updateAnnotation(annotationRequest));
    }

    @Test
    public void updateAnnotationNullDocumentId() {
        //set up
        annotationRequest.setImageId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.updateAnnotation(annotationRequest));
    }

    @Test
    public void updateAnnotationNullAlgorithmId() {
        //set up
        annotationRequest.setAlgorithmId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.updateAnnotation(annotationRequest));
    }

    @Test
    public void updateAnnotationNullContent() {
        //set up
        annotationRequest.setContent(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.updateAnnotation(annotationRequest));
    }

    @ParameterizedTest
    @CsvSource({"-1, 0, 0, 0", "0, -1, 0, 0", "0, 0, -1, 0", "0, 0, 0, -1",
            "-1, -1, 0, 0", "-1, 0, -1, 0", "-1, 0, 0, -1", "0, -1, -1, 0", "0, -1, 0, -1", "0, 0, -1, -1",
            "-1, -1, -1, 0", "-1, -1, 0, -1", "-1, 0, -1, -1", "0, -1, -1, -1",
            "-1, -1, -1, -1"})
    public void updateAnnotationNegativeCoordinates(int startX, int startY, int endX, int endY) {
        //set up
        annotationRequest.setStartX(startX);
        annotationRequest.setStartY(startY);
        annotationRequest.setEndX(endX);
        annotationRequest.setEndY(endY);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationController.updateAnnotation(annotationRequest));
    }

    ////--------------------------------------- 'getAllAnnotationsByDocumentId' tests

    @Test
    public void getAllAnnotationsByDocumentIdSuccess() {
        //set up
        List<AnnotationResponse> annotationResponses = new ArrayList<>();

        ////mock service
        when(annotationService.getAllByImageId(any(UUID.class), any(String.class))).thenReturn(annotationResponses);

        //act
        ResponseEntity<List<AnnotationResponse>> responses =
                annotationController.getAllAnnotationsByDocumentId(annotationRequest.getImageId(), annotationRequest.getUid());

        //assert
        assertTrue(responses.hasBody());
        annotationResponses = responses.getBody();
        assertNotNull(annotationResponses);
    }

    @Test
    public void getAllAnnotationsByDocumentIdNullUid() {
        //set up
        annotationRequest.setUid(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class,
                () -> annotationController.getAllAnnotationsByDocumentId(annotationRequest.getImageId(), annotationRequest.getUid()));
    }

    @Test
    public void getAllAnnotationsByDocumentIdNullDocumentId() {
        //set up
        annotationRequest.setImageId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class,
                () -> annotationController.getAllAnnotationsByDocumentId(annotationRequest.getImageId(), annotationRequest.getUid()));
    }

    ////--------------------------------------- 'deleteAnnotation' tests

    @Test
    public void deleteAnnotationSuccess() {
        //act
        annotationController.deleteAnnotation(annotationRequest.getId(), annotationRequest.getImageId(), annotationRequest.getUid());
    }

    @Test
    public void deleteAnnotationNullId() {
        //set up
        annotationRequest.setId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () ->
                annotationController.deleteAnnotation(annotationRequest.getId(), annotationRequest.getImageId(),
                        annotationRequest.getUid()));
    }

    @Test
    public void deleteAnnotationNullUid() {
        //set up
        annotationRequest.setUid(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () ->
                annotationController.deleteAnnotation(annotationRequest.getId(), annotationRequest.getImageId(),
                        annotationRequest.getUid()));
    }

    @Test
    public void deleteAnnotationNullDocumentId() {
        //set up
        annotationRequest.setImageId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () ->
                annotationController.deleteAnnotation(annotationRequest.getId(), annotationRequest.getImageId(),
                        annotationRequest.getUid()));
    }
}
