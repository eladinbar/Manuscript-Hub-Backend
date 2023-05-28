//package com.manuscript.rest.mapping;
//
//import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
//import com.manuscript.rest.forms.response.AlgorithmResponse;
//import com.manuscript.rest.mapping.response.AlgorithmResponseMapperImpl;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class AlgorithmResponseMapperTests {
//    private final AlgorithmResponseMapperImpl algorithmResponseMapper = new AlgorithmResponseMapperImpl();
//    private final UUID id = UUID.randomUUID();
//    private final String uid = "uid";
//    private final String url = "url";
//    private Date createdTime;
//    private Date updatedTime;
//    private AlgorithmResponse algorithmResponse;
//    private AlgorithmModel algorithmModel;
//
//    @BeforeAll
//    public void setUp() {
//        // set up time
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, 2023);
//        cal.set(Calendar.MONTH, Calendar.JANUARY);
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//        this.createdTime = cal.getTime();
//        cal.set(Calendar.DAY_OF_MONTH, 2);
//        this.updatedTime = cal.getTime();
//    }
//
//    @BeforeEach
//    public void beforeEach() {
//        // set up algorithm response
//        this.algorithmResponse = new AlgorithmResponse(id, uid, url, createdTime, updatedTime);
//
//        // set up algorithm model
//        this.algorithmModel = AlgorithmModel.builder()
//                .id(id).uid(uid)
//                .url(url)
//                .createdTime(createdTime).updatedTime(updatedTime)
//                .build();
//    }
//
//    ////--------------------------------------- 'modelToRest' tests
//
//    @Test
//    public void modelToRestSuccess() {
//        //act
//        AlgorithmResponse algorithmResponse = algorithmResponseMapper.modelToRest(algorithmModel);
//
//        //assert
//        assertNotNull(algorithmResponse);
//        assertEquals(id, algorithmResponse.getId());
//        assertEquals(uid, algorithmResponse.getUid());
//        assertEquals(url, algorithmResponse.getUrl());
//        assertTrue(algorithmModel.getCreatedTime().before(new Date())
//                || algorithmModel.getCreatedTime().equals(new Date()));
//        assertTrue(algorithmModel.getUpdatedTime().after(createdTime)
//                || algorithmModel.getUpdatedTime().equals(createdTime));
//    }
//
//    @Test
//    public void modelToRestNullId() {
//        //set up
//        algorithmModel.setId(null);
//
//        //act
//        //assert
//        assertThrows(IllegalArgumentException.class, () -> algorithmResponseMapper.modelToRest(algorithmModel));
//    }
//
//    @Test
//    public void modelToRestNullUrlSuccess() {
//        //set up
//        algorithmModel.setUrl(null);
//
//        //act
//        AlgorithmResponse algorithmResponse = algorithmResponseMapper.modelToRest(algorithmModel);
//
//        //assert
//        assertNotNull(algorithmResponse);
//        assertEquals(id, algorithmResponse.getId());
//        assertEquals(uid, algorithmResponse.getUid());
//        assertTrue(algorithmModel.getCreatedTime().before(new Date())
//                || algorithmModel.getCreatedTime().equals(new Date()));
//        assertTrue(algorithmModel.getUpdatedTime().after(createdTime)
//                || algorithmModel.getUpdatedTime().equals(createdTime));
//    }
//
//    ////--------------------------------------- 'restToModel' tests
//
//    @Test
//    public void restToModelSuccess() {
//        //act
//        AlgorithmModel algorithmModel = algorithmResponseMapper.restToModel(algorithmResponse);
//
//        //assert
//        assertNotNull(algorithmModel);
//        assertEquals(id, algorithmModel.getId());
//        assertEquals(uid, algorithmModel.getUid());
//        assertEquals(url, algorithmModel.getUrl());
//        assertTrue(algorithmModel.getCreatedTime().before(new Date())
//                || algorithmModel.getCreatedTime().equals(new Date()));
//        assertTrue(algorithmModel.getUpdatedTime().after(createdTime)
//                || algorithmModel.getUpdatedTime().equals(createdTime));
//    }
//
//    @Test
//    public void restToModelNullUrlSuccess() {
//        //set up
//        algorithmResponse.setUrl(null);
//
//        //act
//        AlgorithmModel algorithmModel = algorithmResponseMapper.restToModel(algorithmResponse);
//
//        //assert
//        assertNotNull(algorithmModel);
//        assertEquals(id, algorithmModel.getId());
//        assertEquals(uid, algorithmModel.getUid());
//        assertTrue(algorithmModel.getCreatedTime().before(new Date())
//                || algorithmModel.getCreatedTime().equals(new Date()));
//        assertTrue(algorithmModel.getUpdatedTime().after(createdTime)
//                || algorithmModel.getUpdatedTime().equals(createdTime));
//    }
//}
