package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnnotationEntityMapperTests {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private final AnnotationEntityMapperImpl annotationEntityMapper = new AnnotationEntityMapperImpl();
    private final UUID id = UUID.randomUUID();
    private final String uid = "uid";
    private final UUID imageId = UUID.randomUUID();
    private final UUID manualAlgorithmId = NIL;
    private final UUID algorithmId = UUID.randomUUID();
    private final String content = "content";
    private final int startX = 0;
    private final int startY = 0;
    private final int endX = 5;
    private final int endY = 5;
    private Date createdTime;
    private Date updatedTime;
    private AnnotationEntity annotationEntity;
    private AnnotationModel annotationModel;
    private final UUID userId = UUID.randomUUID();
    private final String email = "email@gmail.com";
    private final String name = "name";
    private final String phoneNumber = "0541234567";
    private final String status = "active";
    private final Role role = Role.User;
    private UserEntity user;
    private final String url = "url";
    private AlgorithmEntity algorithm;


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
        // set up user entity
        this.user = UserEntity.builder()
                .id(userId).uid(uid)
                .email(email).name(name).phoneNumber(phoneNumber)
                .status(status).role(role)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        // set up algorithm entity
        this.algorithm = AlgorithmEntity.builder()
                .id(algorithmId)
                .user(user)
                .url(url)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        // set up annotation entity
        this.annotationEntity = AnnotationEntity.builder()
                .id(id).user(user).imageId(imageId).algorithm(algorithm)
                .content(content)
                .startX(startX).startY(startY).endX(endX).endY(endY)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();


        // set up annotation model
        this.annotationModel = AnnotationModel.builder()
                .id(id).uid(uid).imageId(imageId).algorithmId(algorithmId)
                .content(content)
                .startX(startX).startY(startY).endX(endX).endY(endY)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
    }

    ////--------------------------------------- 'modelToEntity' tests

    @Test
    public void modelToEntitySuccess() {
        //act
        AnnotationEntity annotationEntity = annotationEntityMapper.modelToEntity(annotationModel);

        //assert
        assertNotNull(annotationEntity);
        assertEquals(uid, annotationEntity.getUser().getUid());
        assertEquals(imageId, annotationEntity.getImageId());
        assertEquals(algorithmId, annotationEntity.getAlgorithm().getId());
        assertEquals(content, annotationEntity.getContent());
        assertEquals(startX, annotationEntity.getStartX());
        assertEquals(startY, annotationEntity.getStartY());
        assertEquals(endX, annotationEntity.getEndX());
        assertEquals(endY, annotationEntity.getEndY());
        assertTrue(annotationModel.getCreatedTime().before(new Date())
                || annotationModel.getCreatedTime().equals(new Date()));
        assertTrue(annotationModel.getUpdatedTime().after(createdTime)
                || annotationModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void modelToEntityNullIdSuccess() {
        //set up
        annotationModel.setId(null);

        //act
        AnnotationEntity annotationEntity = annotationEntityMapper.modelToEntity(annotationModel);

        //assert
        assertNotNull(annotationEntity);
        assertEquals(uid, annotationEntity.getUser().getUid());
        assertEquals(imageId, annotationEntity.getImageId());
        assertEquals(algorithmId, annotationEntity.getAlgorithm().getId());
        assertEquals(content, annotationEntity.getContent());
        assertEquals(startX, annotationEntity.getStartX());
        assertEquals(startY, annotationEntity.getStartY());
        assertEquals(endX, annotationEntity.getEndX());
        assertEquals(endY, annotationEntity.getEndY());
        assertTrue(annotationModel.getCreatedTime().before(new Date())
                || annotationModel.getCreatedTime().equals(new Date()));
        assertTrue(annotationModel.getUpdatedTime().after(createdTime)
                || annotationModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void modelToEntityNILAlgorithmIdSuccess() {
        //set up
        annotationModel.setAlgorithmId(NIL);

        //act
        AnnotationEntity annotationEntity = annotationEntityMapper.modelToEntity(annotationModel);

        //assert
        assertNotNull(annotationEntity);
        assertEquals(uid, annotationEntity.getUser().getUid());
        assertEquals(imageId, annotationEntity.getImageId());
        assertNull(annotationEntity.getAlgorithm());
        assertEquals(content, annotationEntity.getContent());
        assertEquals(startX, annotationEntity.getStartX());
        assertEquals(startY, annotationEntity.getStartY());
        assertEquals(endX, annotationEntity.getEndX());
        assertEquals(endY, annotationEntity.getEndY());
        assertTrue(annotationModel.getCreatedTime().before(new Date())
                || annotationModel.getCreatedTime().equals(new Date()));
        assertTrue(annotationModel.getUpdatedTime().after(createdTime)
                || annotationModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void modelToEntityNullContentSuccess() {
        //set up
        annotationModel.setContent(null);

        //act
        AnnotationEntity annotationEntity = annotationEntityMapper.modelToEntity(annotationModel);

        //assert
        assertNotNull(annotationEntity);
        assertEquals(uid, annotationEntity.getUser().getUid());
        assertEquals(imageId, annotationEntity.getImageId());
        assertEquals(algorithmId, annotationEntity.getAlgorithm().getId());
        assertEquals(startX, annotationEntity.getStartX());
        assertEquals(startY, annotationEntity.getStartY());
        assertEquals(endX, annotationEntity.getEndX());
        assertEquals(endY, annotationEntity.getEndY());
        assertTrue(annotationModel.getCreatedTime().before(new Date())
                || annotationModel.getCreatedTime().equals(new Date()));
        assertTrue(annotationModel.getUpdatedTime().after(createdTime)
                || annotationModel.getUpdatedTime().equals(createdTime));
    }

    ////--------------------------------------- 'entityToModel' tests

    @Test
    public void entityToModelSuccess() {
        //act
        AnnotationModel annotationModel = annotationEntityMapper.entityToModel(annotationEntity);

        //assert
        assertNotNull(annotationModel);
        assertEquals(id, annotationModel.getId());
        assertEquals(uid, annotationModel.getUid());
        assertEquals(imageId, annotationModel.getImageId());
        assertEquals(algorithmId, annotationModel.getAlgorithmId());
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
    public void entityToModelNullId() {
        //set up
        annotationEntity.setId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> annotationEntityMapper.entityToModel(annotationEntity));
    }

    @Test
    public void entityToModelNullContentSuccess() {
        //set up
        annotationEntity.setContent(null);

        //act
        AnnotationModel annotationModel = annotationEntityMapper.entityToModel(annotationEntity);

        //assert
        assertNotNull(annotationModel);
        assertEquals(id, annotationModel.getId());
        assertEquals(uid, annotationModel.getUid());
        assertEquals(imageId, annotationModel.getImageId());
        assertEquals(algorithmId, annotationModel.getAlgorithmId());
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
