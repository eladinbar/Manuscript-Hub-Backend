package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
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
public class AlgorithmEntityMapperTests {
    private final AlgorithmEntityMapperImpl algorithmEntityMapper = new AlgorithmEntityMapperImpl();
    private final UUID id = UUID.randomUUID();
    private final String uid = "uid";
    private final String url = "url";
    private Date createdTime;
    private Date updatedTime;
    private AlgorithmEntity algorithmEntity;
    private AlgorithmModel algorithmModel;
    private final UUID userId = UUID.randomUUID();
    private final String email = "email@gmail.com";
    private final String name = "name";
    private final String phoneNumber = "0541234567";
    private final String status = "active";
    private final Role role = Role.User;
    private UserEntity user;


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
        this.algorithmEntity = AlgorithmEntity.builder()
                .id(id)
                .user(user)
                .url(url)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();


        // set up algorithm model
        this.algorithmModel = AlgorithmModel.builder()
                .id(id).uid(uid)
                .url(url)
                .createdTime(createdTime).updatedTime(updatedTime)
                .build();
    }

    ////--------------------------------------- 'modelToEntity' tests

    @Test
    public void modelToEntitySuccess() {
        //act
        AlgorithmEntity algorithmEntity = algorithmEntityMapper.modelToEntity(algorithmModel);

        //assert
        assertNotNull(algorithmEntity);
        assertEquals(uid, algorithmEntity.getUser().getUid());
        assertEquals(url, algorithmEntity.getUrl());
        assertTrue(algorithmEntity.getCreatedTime().before(new Date())
                || algorithmEntity.getCreatedTime().equals(new Date()));
        assertTrue(algorithmEntity.getUpdatedTime().after(createdTime)
                || algorithmEntity.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void modelToEntityNullIdSuccess() {
        //set up
        algorithmModel.setId(null);

        //act
        AlgorithmEntity algorithmEntity = algorithmEntityMapper.modelToEntity(algorithmModel);

        //assert
        assertNotNull(algorithmEntity);
        assertEquals(uid, algorithmEntity.getUser().getUid());
        assertEquals(url, algorithmEntity.getUrl());
        assertTrue(algorithmEntity.getCreatedTime().before(new Date())
                || algorithmEntity.getCreatedTime().equals(new Date()));
        assertTrue(algorithmEntity.getUpdatedTime().after(createdTime)
                || algorithmEntity.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void modelToEntityNullUrlSuccess() {
        //set up
        algorithmModel.setUrl(null);

        //act
        AlgorithmEntity algorithmEntity = algorithmEntityMapper.modelToEntity(algorithmModel);

        //assert
        assertNotNull(algorithmEntity);
        assertEquals(uid, algorithmEntity.getUser().getUid());
        assertTrue(algorithmEntity.getCreatedTime().before(new Date())
                || algorithmEntity.getCreatedTime().equals(new Date()));
        assertTrue(algorithmEntity.getUpdatedTime().after(createdTime)
                || algorithmEntity.getUpdatedTime().equals(createdTime));
    }

    ////--------------------------------------- 'entityToModel' tests

    @Test
    public void entityToModelSuccess() {
        //act
        AlgorithmModel algorithmModel = algorithmEntityMapper.entityToModel(algorithmEntity);

        //assert
        assertNotNull(algorithmModel);
        assertEquals(id, algorithmModel.getId());
        assertEquals(uid, algorithmModel.getUid());
        assertEquals(url, algorithmModel.getUrl());
        assertTrue(algorithmModel.getCreatedTime().before(new Date())
                || algorithmModel.getCreatedTime().equals(new Date()));
        assertTrue(algorithmModel.getUpdatedTime().after(createdTime)
                || algorithmModel.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void entityToModelNullId() {
        //set up
        algorithmEntity.setId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> algorithmEntityMapper.entityToModel(algorithmEntity));
    }

    @Test
    public void entityToModelNullUrlSuccess() {
        //set up
        algorithmEntity.setUrl(null);

        //act
        AlgorithmModel algorithmModel = algorithmEntityMapper.entityToModel(algorithmEntity);

        //assert
        assertNotNull(algorithmModel);
        assertEquals(id, algorithmModel.getId());
        assertEquals(uid, algorithmModel.getUid());
        assertTrue(algorithmModel.getCreatedTime().before(new Date())
                || algorithmModel.getCreatedTime().equals(new Date()));
        assertTrue(algorithmModel.getUpdatedTime().after(createdTime)
                || algorithmModel.getUpdatedTime().equals(createdTime));
    }
}
