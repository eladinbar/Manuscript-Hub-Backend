package com.manuscript.rest.api;

import com.manuscript.rest.request.AnnotationRequest;
import com.manuscript.rest.response.AnnotationResponse;
import com.manuscript.rest.service.IAnnotationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Date;
import java.util.UUID;

public class AnnotationControllerTests {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    public void addAnnotationSuccess() {
        //set up
        ////create an annotation request
        UUID userId = UUID.randomUUID();
        UUID imageId = UUID.randomUUID();
        UUID algorithmId = NIL;
        String content = "content";
        int startX = 0;
        int startY = 0;
        int endX = 5;
        int endY = 5;
        AnnotationRequest annotationRequest = new AnnotationRequest(NIL, userId, imageId, algorithmId,
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

    }
}
