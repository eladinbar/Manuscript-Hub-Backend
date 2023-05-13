package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.enums.*;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.domain.user.repository.IUserRepositoryService;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IAlgorithmRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlgorithmServiceSqlTests {
    private AlgorithmServiceSqlImpl repoService;
    private IUserRepositoryService userRepoService;
    private IAlgorithmRepo repo;
    private IRepositoryEntityMapper<AlgorithmModel, AlgorithmEntity> mapper;
    private final UUID id = UUID.randomUUID();
    private final UUID invalidId = UUID.randomUUID();
    private final String uid = "uid";
    private final String invalidUid = "invalid uid";
    private final String url = "https://www.algorithm.com";
    private final String invalidUrl = "http:/w..a.com";
    private Date createdTime;
    private Date updatedTime;
    private AlgorithmEntity newAlgorithmEntity;
    private AlgorithmEntity algorithmEntity;
    private AlgorithmModel newAlgorithmModel;
    private AlgorithmModel algorithmModel;
    private final UUID userId = UUID.randomUUID();
    private final String email = "email@gmail.com";
    private final String name = "name";
    private final String phoneNumber = "0541234567";
    private final Status status = Status.Enabled;
    private final Role role = Role.User;
    private UserEntity user;
    private UserModel userModel;

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
        repo = Mockito.mock(IAlgorithmRepo.class);
        mapper = (IRepositoryEntityMapper<AlgorithmModel, AlgorithmEntity>) Mockito.mock(IRepositoryEntityMapper.class);

        // set up controller
        repoService = new AlgorithmServiceSqlImpl(userRepoService, repo, mapper);
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
                .id(id).uid(uid)
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
        this.algorithmEntity = AlgorithmEntity.builder()
                .id(id)
                .user(user)
                .url(url)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
    }

    ////--------------------------------------- 'save' tests

    @Test
    public void saveSuccess() {
        //set up
        AlgorithmEntity newAlgorithm = AlgorithmEntity.builder()
                .id(id).user(user)
                .url(url)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        Optional<UserModel> optUserModel = Optional.of(userModel);

        ////mock mappers, assisting services and repository
        when(mapper.modelToEntity(any(AlgorithmModel.class))).thenReturn(newAlgorithm);
        when(userRepoService.getByUid(any(String.class))).thenReturn(optUserModel);
        when(repo.save(any(AlgorithmEntity.class))).thenReturn(newAlgorithm);
        when(mapper.entityToModel(any(AlgorithmEntity.class))).thenReturn(algorithmModel);

        //act
        AlgorithmModel algorithmModel = repoService.save(this.algorithmModel);

        //assert
        assertNotNull(algorithmModel);
        assertEquals(uid, algorithmModel.getUid());
        assertEquals(url, algorithmModel.getUrl());
        assertTrue(algorithmModel.getCreatedTime().before(new Date())
                || algorithmModel.getCreatedTime().equals(new Date()));
        assertTrue(algorithmModel.getUpdatedTime().after(createdTime)
                || algorithmModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void saveInvalidUid() {
        //set up
        algorithmModel.setUid(invalidUid);

        AlgorithmEntity newAlgorithm = AlgorithmEntity.builder()
                .id(id).user(user)
                .url(url)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();

        Optional<UserModel> optUserModel = Optional.empty();

        ////mock mapper and user repo service
        when(mapper.modelToEntity(any(AlgorithmModel.class))).thenReturn(newAlgorithm);
        when(userRepoService.getByUid(any(String.class))).thenReturn(optUserModel);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> repoService.save(algorithmModel));
    }


    ////--------------------------------------- 'update' tests

    @Test
    public void updateSuccess() {
        //set up
        String newUrl = "https://www.bla.com";
        Date newUpdateTime = new Date();

        Optional<AlgorithmEntity> optAlgorithmEntity = Optional.of(algorithmEntity);

        ////set up new algorithm entity
        AlgorithmEntity newAlgorithm = AlgorithmEntity.builder()
                .id(id).user(user)
                .url(newUrl)
                .createdTime(createdTime).updatedTime(newUpdateTime)
                .build();

        ////set up new annotation model
        AlgorithmModel newAlgorithmModel = AlgorithmModel.builder()
                .id(id).uid(uid)
                .url(newUrl)
                .createdTime(createdTime).updatedTime(newUpdateTime)
                .build();

        ////mock mappers, assisting services and repository
        when(repo.findById(any(UUID.class))).thenReturn(optAlgorithmEntity);
        when(mapper.modelToEntity(any(AlgorithmModel.class))).thenReturn(algorithmEntity);
        when(repo.save(any(AlgorithmEntity.class))).thenReturn(newAlgorithm);
        when(mapper.entityToModel(any(AlgorithmEntity.class))).thenReturn(newAlgorithmModel);

        //act
        newAlgorithmModel = repoService.update(this.algorithmModel);

        //assert
        assertNotNull(newAlgorithmModel);
        assertEquals(uid, newAlgorithmModel.getUid());
        assertEquals(newUrl, newAlgorithmModel.getUrl());
        assertTrue(newAlgorithmModel.getCreatedTime().before(new Date())
                || newAlgorithmModel.getCreatedTime().equals(new Date()));
        assertTrue(newAlgorithmModel.getUpdatedTime().after(updatedTime));
    }

    @Test
    public void updateInvalidId() {
        //set up
        algorithmModel.setId(invalidId);

        Optional<AlgorithmEntity> optAlgorithmEntity = Optional.empty();

        ////mock repo
        when(repo.findById(any(UUID.class))).thenReturn(optAlgorithmEntity);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> repoService.update(algorithmModel));
    }


    ////--------------------------------------- 'getById' tests

    @Test
    public void getByIdSuccess() {
        //set up
        Optional<AlgorithmEntity> optAlgorithmEntity = Optional.of(algorithmEntity);

        ////mock mapper and repository
        when(repo.findById(any(UUID.class))).thenReturn(optAlgorithmEntity);
        when(mapper.entityToModel(any(AlgorithmEntity.class))).thenReturn(algorithmModel);

        //act
        Optional<AlgorithmModel> optionalAlgorithmModel = repoService.getById(id);

        //assert
        assertNotNull(optionalAlgorithmModel);
        assertTrue(optionalAlgorithmModel.isPresent());
        AlgorithmModel algorithmModel = optionalAlgorithmModel.get();
        assertEquals(id, algorithmModel.getId());
    }

    @Test
    public void getByIdInvalidId() {
        //set up
        ////mock mapper and repository
        when(repo.findById(any(UUID.class))).thenThrow(IllegalArgumentException.class);
        when(mapper.entityToModel(any(AlgorithmEntity.class))).thenReturn(algorithmModel);

        //assert
        assertThrows(IllegalArgumentException.class, () -> repoService.getById(invalidId));
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
