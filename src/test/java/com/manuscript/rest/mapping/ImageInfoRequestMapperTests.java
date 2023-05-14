package com.manuscript.rest.mapping;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.mapping.request.ImageRequestMapperImpl;
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
public class ImageInfoRequestMapperTests {
    //test classes
    private ImageRequestMapperImpl imageRequestMapperImpl;
    private ImageInfoRequest imageInfoRequest;
    private ImageModel imageModel;

    //test data
    private String fileName;
    private String uid;
    private UUID imageId;
    private Status status;
    private Privacy privacy;
    private final byte[] data = {0};
    private Date createdTime;
    private Date updatedTime;


    @BeforeAll
    public void setup() {
        //mapper setup
        imageRequestMapperImpl = new ImageRequestMapperImpl();

        //data setup
        fileName = "fileName";
        uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
        imageId = UUID.randomUUID();
        status = Status.Enabled;
        privacy = Privacy.Public;

        //date setup
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        createdTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 3);
        updatedTime = cal.getTime();
    }

    @BeforeEach
    public void beforeEach() {
        //request setup
        imageInfoRequest = new ImageInfoRequest(imageId,uid,fileName,status,privacy,data);

        //model setup
        imageModel = ImageModel.builder()
                .id(imageId)
                .uid(uid)
                .fileName(fileName)
                .status(status)
                .privacy(privacy)
                .data(data)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();
    }

    @Test
    public void modelToRest_Success() {
        //act
        ImageInfoRequest testImageInfoRequest = imageRequestMapperImpl.modelToRest(imageModel);
        //assert
        assertNotNull(testImageInfoRequest);
        assertEquals(uid, testImageInfoRequest.getUid());
        assertEquals(fileName, testImageInfoRequest.getFileName());
        assertEquals(data, testImageInfoRequest.getData());
        assertEquals(status, testImageInfoRequest.getStatus());
        assertEquals(privacy, testImageInfoRequest.getPrivacy());
    }

    @Test
    public void restToModel_Success() {
        //act
        ImageModel testImageModel= imageRequestMapperImpl.restToModel(imageInfoRequest);
        //assert
        assertNotNull(testImageModel);
        assertEquals(uid,testImageModel.getUid());
        assertEquals(fileName,testImageModel.getFileName());
        assertEquals(data,testImageModel.getData());
        assertEquals(status,testImageModel.getStatus());
        assertEquals(privacy,testImageModel.getPrivacy());
    }
}
