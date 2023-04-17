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

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

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
    private final AnnotationRequest validNewAnnotationRequest = new AnnotationRequest(null, userId, imageId, manualAlgorithmId,
            content, startX, startY, endX, endY);
    private final AnnotationRequest validAnnotationRequest = new AnnotationRequest(id, userId, imageId, manualAlgorithmId,
            content, startX, startY, endX, endY);

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

    }

    ////--------------------------------------- addAnnotation tests

    @Test
    public void addAnnotationSuccess() {
        //set up
        ////mock service
        AnnotationResponse newAnnotation = new AnnotationResponse(id, userId, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);
        when(annotationService.create(any())).thenReturn(newAnnotation);

        //act
        ResponseEntity<AnnotationResponse> response = annotationController.addAnnotation(validNewAnnotationRequest);

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
        assertTrue(annotationResponse.getCreatedTime().before(new Date()));
        assertTrue(annotationResponse.getUpdatedTime().after(createdTime)
                || annotationResponse.getUpdatedTime().equals(createdTime));
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
