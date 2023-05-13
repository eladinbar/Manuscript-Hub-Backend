package com.manuscript.rest.mapping;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.rest.forms.response.ImageResponse;
import com.manuscript.rest.mapping.response.ImageResponseMapperImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageResponseMapperTests {
    //test classes
    private ImageResponseMapperImpl imageResponseMapperImpl;
    private ImageResponse imageResponse;
    private ImageModel imageModel;

    //test data
    private String fileName;
    private String uid;
    private UUID imageId;
    private UUID userId;
    private Status status;
    private Privacy privacy;
    private final byte[] data = {0};
    private Date createdTime;
    private Date updatedTime;


    @BeforeAll
    public void setup() {
        //mapper setup
        imageResponseMapperImpl = new ImageResponseMapperImpl();

        //data setup
        fileName = "fileName";
        uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
        imageId = UUID.randomUUID();
        userId = UUID.randomUUID();
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
        imageResponse = new ImageResponse(imageId, userId, uid, fileName, data, status, privacy, createdTime, updatedTime);

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
        ImageResponse testImageResponse = imageResponseMapperImpl.modelToRest(imageModel);
        //assert
        assertNotNull(testImageResponse);
        assertEquals(uid, testImageResponse.getUid());
        assertEquals(fileName, testImageResponse.getFileName());
        assertEquals(data, testImageResponse.getData());
        assertEquals(status, testImageResponse.getStatus());
        assertEquals(privacy, testImageResponse.getPrivacy());
    }

    @Test
    public void restToModel_Success() {
        //act
        ImageModel testImageModel = imageResponseMapperImpl.restToModel(imageResponse);
        //assert
        assertNotNull(testImageModel);
        assertEquals(uid, testImageModel.getUid());
        assertEquals(fileName, testImageModel.getFileName());
        assertEquals(data, testImageModel.getData());
        assertEquals(status, testImageModel.getStatus());
        assertEquals(privacy, testImageModel.getPrivacy());
    }
}
