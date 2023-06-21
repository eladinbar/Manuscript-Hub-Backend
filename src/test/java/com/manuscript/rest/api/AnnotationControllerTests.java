package com.manuscript.rest.api;

import com.manuscript.rest.forms.request.AnnotationRequest;
import com.manuscript.rest.forms.response.AnnotationResponse;
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
    private final String uid = "uid";
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
        this.newAnnotationRequest = new AnnotationRequest(null, uid, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY);
        this.annotationRequest = new AnnotationRequest(id, uid, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY);
    }

    ////--------------------------------------- 'getAllAnnotationsByDocumentId' tests

    @Test
    public void getAllAnnotationsByDocumentIdSuccess() {
        //set up
        List<AnnotationResponse> annotationResponses = new ArrayList<>();

        ////mock service
        when(annotationService.getAllByImageDataId(any(UUID.class), any(String.class))).thenReturn(annotationResponses);

        //act
        ResponseEntity<List<AnnotationResponse>> responses =
                annotationController.getAllAnnotationsByImageDataId(annotationRequest.getImageDataId(), annotationRequest.getUid());

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
                () -> annotationController.getAllAnnotationsByImageDataId(annotationRequest.getImageDataId(), annotationRequest.getUid()));
    }

    @Test
    public void getAllAnnotationsByDocumentIdNullDocumentId() {
        //set up
        annotationRequest.setImageDataId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class,
                () -> annotationController.getAllAnnotationsByImageDataId(annotationRequest.getImageDataId(), annotationRequest.getUid()));
    }

    ////--------------------------------------- 'deleteAllAnnotationsByImageDataId' tests

    @Test
    public void deleteAllAnnotationsByImageDataIdSuccess() {
        //act
        annotationController.deleteAllAnnotationsByImageDataId(annotationRequest.getImageDataId());
    }

    @Test
    public void deleteAllAnnotationsByImageDataIdNullImageDataId() {
        //set up
        annotationRequest.setImageDataId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () ->
                annotationController.deleteAllAnnotationsByImageDataId(annotationRequest.getImageDataId()));
    }
}
