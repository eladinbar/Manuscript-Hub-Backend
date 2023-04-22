package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.domain.user.repository.IUserRepositoryService;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IAnnotationRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnnotationServiceSqlTests {
    private final UUID NIL = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private AnnotationServiceSqlImpl repoService;
    private IUserRepositoryService userRepoService;
    private IAnnotationRepo repo;
    private IRepositoryEntityMapper<AnnotationModel, AnnotationEntity> mapper;
    private final UUID id = UUID.randomUUID();
    private final UUID invalidId = NIL;
    private final String uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
    private final String invalidUid = "uid";
    private final UUID imageId = UUID.randomUUID();
    private final UUID invalidImageId = NIL;
    private final UUID manualAlgorithmId = NIL;
    private final UUID algorithmId = UUID.randomUUID();
    private final UUID invalidAlgorithmId = UUID.fromString("12f8f2bb-f9b0-4316-8ece-f9e84aa15603");
    private final String content = "content";
    private final int startX = 0;
    private final int startY = 0;
    private final int endX = 5;
    private final int endY = 5;
    private Date createdTime;
    private Date updatedTime;
    private AnnotationModel annotationModel;
    private AnnotationEntity annotationEntity;
    private final UUID userId = UUID.randomUUID();
    private final String email = "email@gmail.com";
    private final String name = "name";
    private final String phoneNumber = "0541234567";
    private final String status = "active";
    private final Role role = Role.User;
    private UserEntity user;
    private final String url = "url";
    private AlgorithmEntity algorithm;
    private UserModel userModel;
    private AlgorithmModel algorithmModel;

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
        userRepoService = Mockito.mock(IUserRepositoryService.class);
        repo = Mockito.mock(IAnnotationRepo.class);
        mapper = (IRepositoryEntityMapper<AnnotationModel, AnnotationEntity>) Mockito.mock(IRepositoryEntityMapper.class);

        // set up controller
        repoService = new AnnotationServiceSqlImpl(userRepoService, repo, mapper);
    }

    @BeforeEach
    public void beforeEach() {
        // set up user model
        this.userModel = UserModel.builder()
                .id(userId).uid(uid)
                .email(email).name(name).phoneNumber(phoneNumber)
                .status(status).role(role)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        // set up algorithm model
        this.algorithmModel = AlgorithmModel.builder()
                .id(algorithmId)
                .uid(uid)
                .url(url)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

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
                .id(id).uid(uid).imageId(imageId).algorithmId(manualAlgorithmId)
                .content(content)
                .startX(startX).startY(startY).endX(endX).endY(endY)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
    }

    ////--------------------------------------- 'save' tests

    @Test
    public void saveSuccess() {
        //set up
        AnnotationEntity newAnnotation = AnnotationEntity.builder()
                .id(id).user(user).imageId(imageId).algorithm(algorithm)
                .content(content)
                .startX(startX).startY(startY).endX(endX).endY(endY)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        Optional<UserModel> optUserModel = Optional.of(userModel);

        ////mock mappers, assisting services and repository
        when(mapper.modelToEntity(any(AnnotationModel.class))).thenReturn(newAnnotation);
        when(userRepoService.getByUid(any(String.class))).thenReturn(optUserModel);
        when(repo.save(any(AnnotationEntity.class))).thenReturn(newAnnotation);
        when(mapper.entityToModel(any(AnnotationEntity.class))).thenReturn(annotationModel);

        //act
        AnnotationModel annotationModel = repoService.save(this.annotationModel);

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
    public void saveInvalidUid() {
        //set up
        annotationModel.setUid(invalidUid);

        AnnotationEntity newAnnotation = AnnotationEntity.builder()
                .id(id).user(user).imageId(imageId).algorithm(algorithm)
                .content(content)
                .startX(startX).startY(startY).endX(endX).endY(endY)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        Optional<UserModel> optUserModel = Optional.empty();

        ////mock mapper and user repo service
        when(mapper.modelToEntity(any(AnnotationModel.class))).thenReturn(newAnnotation);
        when(userRepoService.getByUid(any(String.class))).thenReturn(optUserModel);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> repoService.save(annotationModel));
    }

    @Test
    public void saveInvalidAlgorithmId() {
        //set up
        AnnotationEntity newAnnotation = AnnotationEntity.builder()
                .id(id).user(user).imageId(imageId).algorithm(algorithm)
                .content(content)
                .startX(startX).startY(startY).endX(endX).endY(endY)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        annotationModel.setAlgorithmId(invalidAlgorithmId);

        Optional<UserModel> optUserModel = Optional.of(userModel);

        ////mock user repo service and repo
        when(mapper.modelToEntity(any(AnnotationModel.class))).thenReturn(newAnnotation);
        when(userRepoService.getByUid(any(String.class))).thenReturn(optUserModel);
        when(repo.save(any(AnnotationEntity.class))).thenThrow(DataIntegrityViolationException.class);

        //act
        //assert
        assertThrows(DataIntegrityViolationException.class, () -> repoService.save(annotationModel));
    }


    ////--------------------------------------- 'update' tests

    @Test
    public void updateSuccess() {
        //set up
        String newContent = "new content";
        int newStartX = 1;
        int newStartY = 1;
        int newEndX = 6;
        int newEndY = 6;
        Date newUpdateTime = new Date();

        Optional<AnnotationEntity> optAnnotationEntity = Optional.of(annotationEntity);

        ////set up new annotation entity
        AnnotationEntity newAnnotation = AnnotationEntity.builder()
                .id(id).user(user).imageId(imageId).algorithm(algorithm)
                .content(newContent)
                .startX(newStartX).startY(newStartY).endX(newEndX).endY(newEndY)
                .createdTime(createdTime).updatedTime(newUpdateTime)
                .build();

        ////set up new annotation model
        AnnotationModel newAnnotationModel = AnnotationModel.builder()
                .id(id).uid(uid).imageId(imageId).algorithmId(algorithmId)
                .content(newContent)
                .startX(newStartX).startY(newStartY).endX(newEndX).endY(newEndY)
                .createdTime(createdTime).updatedTime(newUpdateTime)
                .build();

        ////mock mappers, assisting services and repository
        when(repo.findById(any(UUID.class))).thenReturn(optAnnotationEntity);
        when(mapper.modelToEntity(any(AnnotationModel.class))).thenReturn(annotationEntity);
        when(repo.save(any(AnnotationEntity.class))).thenReturn(newAnnotation);
        when(mapper.entityToModel(any(AnnotationEntity.class))).thenReturn(newAnnotationModel);

        //act
        newAnnotationModel = repoService.update(this.annotationModel);

        //assert
        assertNotNull(newAnnotationModel);
        assertEquals(uid, newAnnotationModel.getUid());
        assertEquals(imageId, newAnnotationModel.getImageId());
        assertEquals(algorithmId, newAnnotationModel.getAlgorithmId());
        assertEquals(newContent, newAnnotationModel.getContent());
        assertEquals(newStartX, newAnnotationModel.getStartX());
        assertEquals(newStartY, newAnnotationModel.getStartY());
        assertEquals(newEndX, newAnnotationModel.getEndX());
        assertEquals(newEndY, newAnnotationModel.getEndY());
        assertTrue(newAnnotationModel.getCreatedTime().before(new Date())
                || newAnnotationModel.getCreatedTime().equals(new Date()));
        assertTrue(newAnnotationModel.getUpdatedTime().after(updatedTime));
    }

    @Test
    public void updateInvalidId() {
        //set up
        annotationModel.setId(invalidId);

        Optional<AnnotationEntity> optAnnotationEntity = Optional.empty();

        ////mock repo
        when(repo.findById(any(UUID.class))).thenReturn(optAnnotationEntity);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> repoService.update(annotationModel));
    }


    @Test
    public void updateInvalidAlgorithmId() {
        //set up
        annotationModel.setAlgorithmId(invalidAlgorithmId);

        Optional<AnnotationEntity> optAnnotationEntity = Optional.of(annotationEntity);

        ////mock repo
        when(repo.findById(any(UUID.class))).thenReturn(optAnnotationEntity);
        when(repo.save(any(AnnotationEntity.class))).thenThrow(DataIntegrityViolationException.class);

        //act
        //assert
        assertThrows(DataIntegrityViolationException.class, () -> repoService.update(annotationModel));
    }

    ////--------------------------------------- 'getAllByImageId' tests

    @Test
    public void getAllByImageIdSuccess() {
        //set up
        List<AnnotationEntity> annotationEntities = new ArrayList<>();
        annotationEntities.add(annotationEntity);

        ////mock mappers, assisting services and repository
        when(repo.findAllByImageId(any(UUID.class))).thenReturn(annotationEntities);
        when(mapper.entityToModel(any(AnnotationEntity.class))).thenReturn(annotationModel);

        //act
        List<AnnotationModel> annotationModels = repoService.getAllByImageId(imageId);

        //assert
        assertNotNull(annotationModels);
        assertFalse(annotationModels.isEmpty());
    }

    @Test
    public void getAllByImageIdEmptyArraySuccess() {
        //set up
        List<AnnotationEntity> annotationEntities = new ArrayList<>();

        ////mock mappers, assisting services and repository
        when(repo.findAllByImageId(any(UUID.class))).thenReturn(annotationEntities);
        when(mapper.entityToModel(any(AnnotationEntity.class))).thenReturn(annotationModel);

        //act
        List<AnnotationModel> annotationModels = repoService.getAllByImageId(imageId);

        //assert
        assertNotNull(annotationModels);
        assertTrue(annotationModels.isEmpty());
    }

    ////--------------------------------------- 'deleteById' tests

    @Test
    public void deleteByIdSuccess() {
        //act
        repoService.deleteById(id);
    }

    @Test
    //Still expect this to not throw any exception
    public void deleteByIdInvalidIdSuccess() {
        //act
        repoService.deleteById(invalidId);
    }
}
