package com.manuscript.rest.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.rest.request.AnnotationRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnnotationRequestMapperTests {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private final AnnotationRequestMapperImpl annotationRequestMapper = new AnnotationRequestMapperImpl();
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
    private AnnotationRequest annotationRequest;
    private AnnotationModel annotationModel;

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
    }

    @BeforeEach
    public void beforeEach() {
        // set up annotation request
        this.annotationRequest = new AnnotationRequest(id, uid, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY);

        // set up annotation model
        this.annotationModel = AnnotationModel.builder()
                .id(id).uid(uid).imageId(imageId).algorithmId(manualAlgorithmId)
                .content(content)
                .startX(startX).startY(startY).endX(endX).endY(endY)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
    }

    ////--------------------------------------- 'modelToRest' tests

    @Test
    public void modelToRestSuccess() {
        //act
        AnnotationRequest annotationRequest = annotationRequestMapper.modelToRest(annotationModel);

        //assert
        assertNotNull(annotationRequest);
        assertEquals(id, annotationRequest.getId());
        assertEquals(uid, annotationRequest.getUid());
        assertEquals(imageId, annotationRequest.getImageId());
        assertEquals(manualAlgorithmId, annotationRequest.getAlgorithmId());
        assertEquals(content, annotationRequest.getContent());
        assertEquals(startX, annotationRequest.getStartX());
        assertEquals(startY, annotationRequest.getStartY());
        assertEquals(endX, annotationRequest.getEndX());
        assertEquals(endY, annotationRequest.getEndY());
    }

    @Test
    public void modelToRestNullIdSuccess() {
        //set up
        annotationModel.setId(null);

        //act
        AnnotationRequest annotationRequest = annotationRequestMapper.modelToRest(annotationModel);

        //assert
        assertNotNull(annotationRequest);
        assertEquals(uid, annotationRequest.getUid());
        assertEquals(imageId, annotationRequest.getImageId());
        assertEquals(manualAlgorithmId, annotationRequest.getAlgorithmId());
        assertEquals(content, annotationRequest.getContent());
        assertEquals(startX, annotationRequest.getStartX());
        assertEquals(startY, annotationRequest.getStartY());
        assertEquals(endX, annotationRequest.getEndX());
        assertEquals(endY, annotationRequest.getEndY());
    }

    @Test
    public void modelToRestNullContentSuccess() {
        //set up
        annotationModel.setContent(null);

        //act
        AnnotationRequest annotationRequest = annotationRequestMapper.modelToRest(annotationModel);

        //assert
        assertNotNull(annotationRequest);
        assertEquals(id, annotationRequest.getId());
        assertEquals(uid, annotationRequest.getUid());
        assertEquals(imageId, annotationRequest.getImageId());
        assertEquals(manualAlgorithmId, annotationRequest.getAlgorithmId());
        assertEquals(startX, annotationRequest.getStartX());
        assertEquals(startY, annotationRequest.getStartY());
        assertEquals(endX, annotationRequest.getEndX());
        assertEquals(endY, annotationRequest.getEndY());
    }

    ////--------------------------------------- 'restToModel' tests

    @Test
    public void restToModelSuccess() {
        //act
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);

        //assert
        assertNotNull(annotationModel);
        assertEquals(id, annotationModel.getId());
        assertEquals(uid, annotationModel.getUid());
        assertEquals(imageId, annotationModel.getImageId());
        assertEquals(manualAlgorithmId, annotationModel.getAlgorithmId());
        assertEquals(content, annotationModel.getContent());
        assertEquals(startX, annotationModel.getStartX());
        assertEquals(startY, annotationModel.getStartY());
        assertEquals(endX, annotationModel.getEndX());
        assertEquals(endY, annotationModel.getEndY());
        assertTrue(annotationModel.getCreatedTime().before(new Date())
                || annotationModel.getCreatedTime().equals(new Date()));
        assertTrue(annotationModel.getUpdatedTime().after(createdTime)
                || annotationModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void restToModelNullIdSuccess() {
        //set up
        annotationRequest.setId(null);

        //act
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);

        //assert
        assertNotNull(annotationModel);
        assertEquals(uid, annotationModel.getUid());
        assertEquals(imageId, annotationModel.getImageId());
        assertEquals(manualAlgorithmId, annotationModel.getAlgorithmId());
        assertEquals(content, annotationModel.getContent());
        assertEquals(startX, annotationModel.getStartX());
        assertEquals(startY, annotationModel.getStartY());
        assertEquals(endX, annotationModel.getEndX());
        assertEquals(endY, annotationModel.getEndY());
        assertTrue(annotationModel.getCreatedTime().before(new Date())
                || annotationModel.getCreatedTime().equals(new Date()));
        assertTrue(annotationModel.getUpdatedTime().after(createdTime)
                || annotationModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void restToModelNullUid() {
        //set up
        annotationRequest.setUid(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationRequestMapper.restToModel(annotationRequest));
    }

    @Test
    public void restToModelNullImageId() {
        //set up
        annotationRequest.setImageId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationRequestMapper.restToModel(annotationRequest));
    }

    @Test
    public void restToModelNullAlgorithmId() {
        //set up
        annotationRequest.setAlgorithmId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationRequestMapper.restToModel(annotationRequest));
    }

    @Test
    public void restToModelNullContentSuccess() {
        //set up
        annotationRequest.setContent(null);

        //act
        AnnotationModel annotationModel = annotationRequestMapper.restToModel(annotationRequest);

        //assert
        assertNotNull(annotationModel);
        assertEquals(id, annotationModel.getId());
        assertEquals(uid, annotationModel.getUid());
        assertEquals(imageId, annotationModel.getImageId());
        assertEquals(manualAlgorithmId, annotationModel.getAlgorithmId());
        assertEquals(startX, annotationModel.getStartX());
        assertEquals(startY, annotationModel.getStartY());
        assertEquals(endX, annotationModel.getEndX());
        assertEquals(endY, annotationModel.getEndY());
        assertTrue(annotationModel.getCreatedTime().before(new Date())
                || annotationModel.getCreatedTime().equals(new Date()));
        assertTrue(annotationModel.getUpdatedTime().after(createdTime)
                || annotationModel.getUpdatedTime().equals(createdTime));
    }
}
