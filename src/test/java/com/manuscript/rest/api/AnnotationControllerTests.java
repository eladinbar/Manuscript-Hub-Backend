package com.manuscript.rest.api;

import com.manuscript.rest.request.AnnotationRequest;
import com.manuscript.rest.response.AnnotationResponse;
import com.manuscript.rest.service.IAnnotationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class AnnotationControllerTests {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @BeforeEach
    public void beforeEach() {

    }

    ////--------------------------------------- addAnnotation tests

    @Test
    public void addAnnotationSuccess() {
        //set up
        ////create an annotation request
        String userId = "uid";
        UUID imageId = UUID.randomUUID();
        UUID algorithmId = NIL;
        String content = "content";
        int startX = 0;
        int startY = 0;
        int endX = 5;
        int endY = 5;
        AnnotationRequest annotationRequest = new AnnotationRequest(null, userId, imageId, algorithmId,
                content, startX, startY, endX, endY);

        ////mock service
        IAnnotationService annotationService = Mockito.mock(IAnnotationService.class);
        UUID annotationId = UUID.randomUUID();
        Date createdTime = new Date();
        Date updatedTime = new Date();
        AnnotationResponse newAnnotation = new AnnotationResponse(annotationId, userId, imageId, algorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);
        when(annotationService.create(any())).thenReturn(newAnnotation);

        ////set up controller
        AnnotationController annotationController = new AnnotationController(annotationService);

        //act
        ResponseEntity<AnnotationResponse> response = annotationController.addAnnotation(annotationRequest);

        //assert
        assertTrue(response.hasBody());
        AnnotationResponse annotationResponse = response.getBody();
        assertNotNull(annotationResponse);
        assertEquals(userId, annotationResponse.getUid());
        assertEquals(imageId, annotationResponse.getImageId());
        assertEquals(algorithmId, annotationResponse.getAlgorithmId());
        assertEquals(content, annotationResponse.getContent());
        assertEquals(startX, annotationResponse.getStartX());
        assertEquals(startY, annotationResponse.getStartY());
        assertEquals(endX, annotationResponse.getEndX());
        assertEquals(endY, annotationResponse.getEndY());
    }

    @Test
    public void addAnnotationNullUid() {

    }

    @Test
    public void addAnnotationNullDocumentId() {

    }

    @Test
    public void addAnnotationNullAlgorithmId() {

    }

    @Test
    public void addAnnotationNullContent() {

    }

    @ParameterizedTest
    @CsvSource({"-1, 0, 0, 0", "0, -1, 0, 0", "0, 0, -1, 0", "0, 0, 0, -1",
            "-1, -1, 0, 0", "-1, 0, -1, 0", "-1, 0, 0, -1", "0, -1, -1, 0", "0, -1, 0, -1", "0, 0, -1, -1",
            "-1, -1, -1, 0", "-1, -1, 0, -1", "-1, 0, -1, -1", "0, -1, -1, -1",
            "-1, -1, -1, -1"})
    public void addAnnotationNegativeCoordinates(int startX, int startY, int endX, int endY) {

    }

    ////--------------------------------------- updateAnnotation tests

    @Test
    public void updateAnnotationSuccess() {

    }

    @Test
    public void updateAnnotationNullId() {

    }

    @Test
    public void updateAnnotationNullUid() {

    }

    @Test
    public void updateAnnotationNullDocumentId() {

    }

    @Test
    public void updateAnnotationNullAlgorithmId() {

    }

    @Test
    public void updateAnnotationNullContent() {

    }

    @ParameterizedTest
    @CsvSource({"-1, 0, 0, 0", "0, -1, 0, 0", "0, 0, -1, 0", "0, 0, 0, -1",
            "-1, -1, 0, 0", "-1, 0, -1, 0", "-1, 0, 0, -1", "0, -1, -1, 0", "0, -1, 0, -1", "0, 0, -1, -1",
            "-1, -1, -1, 0", "-1, -1, 0, -1", "-1, 0, -1, -1", "0, -1, -1, -1",
            "-1, -1, -1, -1"})
    public void updateAnnotationNegativeCoordinates(int startX, int startY, int endX, int endY) {

    }

    ////--------------------------------------- getAllAnnotationsByDocumentId tests

    @Test
    public void getAllAnnotationsByDocumentIdSuccess() {

    }

    @Test
    public void getAllAnnotationsByDocumentIdNullUid() {

    }

    @Test
    public void getAllAnnotationsByDocumentIdNullDocumentId() {

    }

    ////--------------------------------------- deleteAnnotation tests

    @Test
    public void deleteAnnotationSuccess() {

    }

    @Test
    public void deleteAnnotationNullId() {

    }

    @Test
    public void deleteAnnotationNullUid() {

    }

    @Test
    public void deleteAnnotationNullDocumentId() {

    }
}
