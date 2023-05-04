package com.manuscript.rest.api;

import com.manuscript.rest.forms.request.AlgorithmRequest;
import com.manuscript.rest.forms.response.AlgorithmResponse;
import com.manuscript.rest.service.IAlgorithmService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlgorithmControllerTests {
    private IAlgorithmService algorithmService;
    private AlgorithmController algorithmController;
    private final UUID id = UUID.randomUUID();
    private final String uid = "uid";
    private final UUID imageId = UUID.randomUUID();
    private final UUID algorithmId = UUID.randomUUID();
    private final String url = "url";
    private Date createdTime;
    private Date updatedTime;
    private AlgorithmRequest newAlgorithmRequest;
    private AlgorithmRequest algorithmRequest;

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
        algorithmService = Mockito.mock(IAlgorithmService.class);
        algorithmController = new AlgorithmController(algorithmService);
    }

    @BeforeEach
    public void beforeEach() {
        this.algorithmRequest = new AlgorithmRequest(id, uid, imageId, url);
        this.newAlgorithmRequest = new AlgorithmRequest(null, uid, imageId, url);
    }

    ////--------------------------------------- 'uploadAlgorithm' tests

    @Test
    public void uploadAlgorithmSuccess() {
        //set up
        AlgorithmResponse newAlgorithm = new AlgorithmResponse(id, uid, url, createdTime, updatedTime);

        ////mock service
        when(algorithmService.create(any())).thenReturn(newAlgorithm);

        //act
        ResponseEntity<AlgorithmResponse> response = algorithmController.uploadAlgorithm(newAlgorithmRequest);

        //assert
        assertTrue(response.hasBody());
        AlgorithmResponse algorithmResponse = response.getBody();
        assertNotNull(algorithmResponse);
        assertEquals(uid, algorithmResponse.getUid());
        assertEquals(url, algorithmResponse.getUrl());
        assertTrue(algorithmResponse.getCreatedTime().before(new Date())
                || algorithmResponse.getCreatedTime().equals(new Date()));
        assertTrue(algorithmResponse.getUpdatedTime().after(createdTime)
                || algorithmResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void uploadAlgorithmNullUid() {
        //set up
        newAlgorithmRequest.setUid(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> algorithmController.uploadAlgorithm(newAlgorithmRequest));
    }

    @Test
    public void uploadAlgorithmNullDocumentId() {
        //set up
        newAlgorithmRequest.setImageId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> algorithmController.uploadAlgorithm(newAlgorithmRequest));
    }

    @Test
    public void uploadAlgorithmNullUrl() {
        //set up
        newAlgorithmRequest.setUrl(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> algorithmController.uploadAlgorithm(newAlgorithmRequest));
    }

    ////--------------------------------------- 'updateAlgorithm' tests

    @Test
    public void updateAlgorithmSuccess() {
        //set up
        AlgorithmResponse newAlgorithm = new AlgorithmResponse(id, uid, url, createdTime, updatedTime);

        ////mock service
        when(algorithmService.update(any())).thenReturn(newAlgorithm);

        //act
        ResponseEntity<AlgorithmResponse> response = algorithmController.updateAlgorithm(algorithmRequest);

        //assert
        assertTrue(response.hasBody());
        AlgorithmResponse algorithmResponse = response.getBody();
        assertNotNull(algorithmResponse);
        assertEquals(uid, algorithmResponse.getUid());
        assertEquals(url, algorithmResponse.getUrl());
        assertTrue(algorithmResponse.getCreatedTime().before(new Date())
                || algorithmResponse.getCreatedTime().equals(new Date()));
        assertTrue(algorithmResponse.getUpdatedTime().after(createdTime)
                || algorithmResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void updateAlgorithmNullId() {
        //set up
        algorithmRequest.setId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> algorithmController.updateAlgorithm(algorithmRequest));
    }

    @Test
    public void updateAlgorithmNullUid() {
        //set up
        algorithmRequest.setUid(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> algorithmController.updateAlgorithm(algorithmRequest));
    }

    @Test
    public void updateAlgorithmNullDocumentId() {
        //set up
        algorithmRequest.setImageId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> algorithmController.updateAlgorithm(algorithmRequest));
    }

    @Test
    public void updateAlgorithmNullUrl() {
        //set up
        algorithmRequest.setUrl(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> algorithmController.updateAlgorithm(algorithmRequest));
    }

    ////--------------------------------------- 'getAlgorithm' tests

    @Test
    public void getAlgorithmSuccess() {
        //set up
        AlgorithmResponse newAlgorithm = new AlgorithmResponse(id, uid, url, createdTime, updatedTime);

        ////mock service
        when(algorithmService.getById(any(UUID.class))).thenReturn(newAlgorithm);

        //act
        ResponseEntity<AlgorithmResponse> response = algorithmController.getAlgorithm(id, uid);

        //assert
        assertTrue(response.hasBody());
        AlgorithmResponse algorithmResponse = response.getBody();
        assertNotNull(algorithmResponse);
        assertEquals(uid, algorithmResponse.getUid());
        assertEquals(url, algorithmResponse.getUrl());
        assertTrue(algorithmResponse.getCreatedTime().before(new Date())
                || algorithmResponse.getCreatedTime().equals(new Date()));
        assertTrue(algorithmResponse.getUpdatedTime().after(createdTime)
                || algorithmResponse.getUpdatedTime().equals(createdTime));
    }

    @Test
    public void getAlgorithmNullId() {
        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> algorithmController.getAlgorithm(null, uid));
    }

    @Test
    public void getAlgorithmNullUid() {
        //act
        //assert
        assertThrows(IllegalArgumentException.class, () -> algorithmController.getAlgorithm(id, null));
    }

    ////--------------------------------------- 'deleteAlgorithm' tests

    @Test
    public void deleteAlgorithmSuccess() {
        //act
        algorithmController.deleteAlgorithm(algorithmRequest.getId(), algorithmRequest.getUid());
    }

    @Test
    public void deleteAlgorithmNullId() {
        //set up
        algorithmRequest.setId(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () ->
                algorithmController.deleteAlgorithm(algorithmRequest.getId(), algorithmRequest.getUid()));
    }

    @Test
    public void deleteAlgorithmNullUid() {
        //set up
        algorithmRequest.setUid(null);

        //act
        //assert
        assertThrows(IllegalArgumentException.class, () ->
                algorithmController.deleteAlgorithm(algorithmRequest.getId(), algorithmRequest.getUid()));
    }
}
