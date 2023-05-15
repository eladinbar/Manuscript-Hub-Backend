package com.manuscript.rest.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.rest.forms.response.AnnotationResponse;
import com.manuscript.rest.mapping.response.AnnotationResponseMapperImpl;
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
public class AnnotationResponseMapperTests {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private final AnnotationResponseMapperImpl annotationResponseMapper = new AnnotationResponseMapperImpl();
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
    private AnnotationResponse annotationResponse;
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
        // set up annotation response
        this.annotationResponse = new AnnotationResponse(id, uid, imageId, manualAlgorithmId,
                content, startX, startY, endX, endY, createdTime, updatedTime);

        // set up annotation model
        this.annotationModel = AnnotationModel.builder()
                .id(id).uid(uid).imageDataId(imageId).algorithmId(manualAlgorithmId)
                .content(content)
                .startX(startX).startY(startY).endX(endX).endY(endY)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
    }

    ////--------------------------------------- 'modelToRest' tests

    @Test
    public void modelToRestSuccess() {
        //act
        AnnotationResponse annotationResponse = annotationResponseMapper.modelToRest(annotationModel);

        //assert
        assertNotNull(annotationResponse);
        assertEquals(id, annotationResponse.getId());
        assertEquals(uid, annotationResponse.getUid());
        assertEquals(imageId, annotationResponse.getImageDataId());
        assertEquals(manualAlgorithmId, annotationResponse.getAlgorithmId());
        assertEquals(content, annotationResponse.getContent());
        assertEquals(startX, annotationResponse.getStartX());
        assertEquals(startY, annotationResponse.getStartY());
        assertEquals(endX, annotationResponse.getEndX());
        assertEquals(endY, annotationResponse.getEndY());
        assertTrue(annotationModel.getCreatedTime().before(new Date())
                || annotationModel.getCreatedTime().equals(new Date()));
        assertTrue(annotationModel.getUpdatedTime().after(createdTime)
                || annotationModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void modelToRestNullId() {
        //set up
        annotationModel.setId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationResponseMapper.modelToRest(annotationModel));
    }

    @Test
    public void modelToRestNullContentSuccess() {
        //set up
        annotationModel.setContent(null);

        //act
        AnnotationResponse annotationResponse = annotationResponseMapper.modelToRest(annotationModel);

        //assert
        assertNotNull(annotationResponse);
        assertEquals(id, annotationResponse.getId());
        assertEquals(uid, annotationResponse.getUid());
        assertEquals(imageId, annotationResponse.getImageDataId());
        assertEquals(manualAlgorithmId, annotationResponse.getAlgorithmId());
        assertEquals(startX, annotationResponse.getStartX());
        assertEquals(startY, annotationResponse.getStartY());
        assertEquals(endX, annotationResponse.getEndX());
        assertEquals(endY, annotationResponse.getEndY());
        assertTrue(annotationModel.getCreatedTime().before(new Date())
                || annotationModel.getCreatedTime().equals(new Date()));
        assertTrue(annotationModel.getUpdatedTime().after(createdTime)
                || annotationModel.getUpdatedTime().equals(createdTime));
    }

    ////--------------------------------------- 'restToModel' tests

    @Test
    public void restToModelSuccess() {
        //act
        AnnotationModel annotationModel = annotationResponseMapper.restToModel(annotationResponse);

        //assert
        assertNotNull(annotationModel);
        assertEquals(id, annotationModel.getId());
        assertEquals(uid, annotationModel.getUid());
        assertEquals(imageId, annotationModel.getImageDataId());
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
    public void restToModelNullContentSuccess() {
        //set up
        annotationResponse.setContent(null);

        //act
        AnnotationModel annotationModel = annotationResponseMapper.restToModel(annotationResponse);

        //assert
        assertNotNull(annotationModel);
        assertEquals(id, annotationModel.getId());
        assertEquals(uid, annotationModel.getUid());
        assertEquals(imageId, annotationModel.getImageDataId());
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
