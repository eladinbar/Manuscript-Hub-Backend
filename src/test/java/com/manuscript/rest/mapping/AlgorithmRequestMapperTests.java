//package com.manuscript.rest.mapping;
//
//import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
//import com.manuscript.rest.forms.request.AlgorithmRequest;
//import com.manuscript.rest.mapping.request.AlgorithmRequestMapperImpl;
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
//public class AlgorithmRequestMapperTests {
//    private final AlgorithmRequestMapperImpl algorithmRequestMapper = new AlgorithmRequestMapperImpl();
//    private final UUID id = UUID.randomUUID();
//    private final String uid = "uid";
//    private final UUID imageId = UUID.randomUUID();
//    private final String url = "url";
//    private Date createdTime;
//    private Date updatedTime;
//    private AlgorithmRequest algorithmRequest;
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
//        // set up algorithm request
//        this.algorithmRequest = new AlgorithmRequest(id, uid, imageId, url);
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
//        AlgorithmRequest algorithmRequest = algorithmRequestMapper.modelToRest(algorithmModel);
//
//        //assert
//        assertNotNull(algorithmRequest);
//        assertEquals(id, algorithmRequest.getId());
//        assertEquals(uid, algorithmRequest.getUid());
//        assertEquals(url, algorithmRequest.getUrl());
//    }
//
//    @Test
//    public void modelToRestNullIdSuccess() {
//        //set up
//        algorithmModel.setId(null);
//
//        //act
//        AlgorithmRequest algorithmRequest = algorithmRequestMapper.modelToRest(algorithmModel);
//
//        //assert
//        assertNotNull(algorithmRequest);
//        assertEquals(uid, algorithmRequest.getUid());
//        assertEquals(url, algorithmRequest.getUrl());
//    }
//
//    @Test
//    public void modelToRestNullUrlSuccess() {
//        //set up
//        algorithmModel.setUrl(null);
//
//        //act
//        AlgorithmRequest algorithmRequest = algorithmRequestMapper.modelToRest(algorithmModel);
//
//        //assert
//        assertNotNull(algorithmRequest);
//        assertEquals(id, algorithmRequest.getId());
//        assertEquals(uid, algorithmRequest.getUid());
//    }
//
//    ////--------------------------------------- 'restToModel' tests
//
//    @Test
//    public void restToModelSuccess() {
//        //act
//        AlgorithmModel algorithmModel = algorithmRequestMapper.restToModel(algorithmRequest);
//
//        //assert
//        assertNotNull(algorithmModel);
//        assertEquals(id, algorithmModel.getId());
//        assertEquals(uid, algorithmModel.getUid());
//        assertEquals(url, algorithmModel.getUrl());
//        assertTrue(algorithmModel.getCreatedTime().before(new Date()));
//        assertTrue(algorithmModel.getUpdatedTime().after(createdTime)
//                || algorithmModel.getUpdatedTime().equals(createdTime));
//    }
//
//    @Test
//    public void restToModelNullIdSuccess() {
//        //set up
//        algorithmRequest.setId(null);
//
//        //act
//        AlgorithmModel algorithmModel = algorithmRequestMapper.restToModel(algorithmRequest);
//
//        //assert
//        assertNotNull(algorithmModel);
//        assertEquals(uid, algorithmModel.getUid());
//        assertEquals(url, algorithmModel.getUrl());
//        assertTrue(algorithmModel.getCreatedTime().before(new Date())
//                || algorithmModel.getCreatedTime().equals(new Date()));
//        assertTrue(algorithmModel.getUpdatedTime().after(createdTime)
//                || algorithmModel.getUpdatedTime().equals(createdTime));
//    }
//
//    @Test
//    public void restToModelNullUid() {
//        //set up
//        algorithmRequest.setUid(null);
//
//        //act
//        //assert
//        assertThrows(IllegalArgumentException.class, () -> algorithmRequestMapper.restToModel(algorithmRequest));
//    }
//
//    @Test
//    public void restToModelNullImageIdSuccess() {
//        //set up
//        algorithmRequest.setImageId(null);
//
//        //act
//        AlgorithmModel algorithmModel = algorithmRequestMapper.restToModel(algorithmRequest);
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
//        algorithmRequest.setUrl(null);
//
//        //act
//        AlgorithmModel algorithmModel = algorithmRequestMapper.restToModel(algorithmRequest);
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
