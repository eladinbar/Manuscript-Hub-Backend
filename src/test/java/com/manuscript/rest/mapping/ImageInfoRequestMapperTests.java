package com.manuscript.rest.mapping;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.mapping.request.ImageRequestMapperImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageInfoRequestMapperTests {
    //test classes
    private ImageRequestMapperImpl imageRequestMapperImpl;
    private ImageInfoRequest imageInfoRequest;
    private ImageInfoModel imageInfoModel;

    //test data
    private final String uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
    private final String title = "title";
    private final String author = "author";
    private final String description = "description";
    private final Date publicationDate = new Date();
    private final List<String> tags = new ArrayList<>();
    private final List<String> sharedUserIds = new ArrayList<>();
    private final UUID imageId = UUID.randomUUID();
    private final Status status = Status.Enabled;
    private final Privacy privacy = Privacy.Public;
    private Date createdTime;
    private Date updatedTime;


    @BeforeAll
    public void setup() {
        //mapper setup
        imageRequestMapperImpl = new ImageRequestMapperImpl();

        //data setup
        tags.add("tag");
        sharedUserIds.add("2UYxH92SpBQfkRgEeN75EBdvM9r2");

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
        imageInfoRequest = new ImageInfoRequest(imageId, uid, title, author, publicationDate, description, tags, sharedUserIds, status, privacy);

        //model setup
        imageInfoModel = ImageInfoModel.builder()
                .id(imageId)
                .uid(uid)
                .title(title)
                .author(author)
                .description(description)
                .publicationDate(publicationDate)
                .status(status)
                .privacy(privacy)
                .tags(tags)
                .sharedUserIds(sharedUserIds)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();
    }

    @Test
    public void modelToRest_Success() {
        //act
        ImageInfoRequest testImageInfoRequest = imageRequestMapperImpl.modelToRest(imageInfoModel);

        //assert
        assertNotNull(testImageInfoRequest);
        assertEquals(uid, testImageInfoRequest.getUid());
        assertEquals(title, testImageInfoRequest.getTitle());
        assertEquals(author, testImageInfoRequest.getAuthor());
        assertEquals(description, testImageInfoRequest.getDescription());
        assertEquals(publicationDate, testImageInfoRequest.getPublicationDate());
        assertEquals(status, testImageInfoRequest.getStatus());
        assertEquals(privacy, testImageInfoRequest.getPrivacy());
        assertEquals(tags, testImageInfoRequest.getTags());
        assertEquals(sharedUserIds, testImageInfoRequest.getSharedUserIds());
        assertEquals(status, testImageInfoRequest.getStatus());
        assertEquals(privacy, testImageInfoRequest.getPrivacy());
    }

    @Test
    public void restToModel_Success() {
        //act
        ImageInfoModel testImageInfoModel = imageRequestMapperImpl.restToModel(imageInfoRequest);

        //assert
        assertNotNull(testImageInfoModel);
        assertEquals(uid, testImageInfoModel.getUid());
        assertEquals(title, testImageInfoModel.getTitle());
        assertEquals(author, testImageInfoModel.getAuthor());
        assertEquals(description, testImageInfoModel.getDescription());
        assertEquals(publicationDate, testImageInfoModel.getPublicationDate());
        assertEquals(status, testImageInfoModel.getStatus());
        assertEquals(privacy, testImageInfoModel.getPrivacy());
        assertEquals(tags, testImageInfoModel.getTags());
        assertEquals(sharedUserIds, testImageInfoModel.getSharedUserIds());
        assertEquals(status, testImageInfoModel.getStatus());
        assertEquals(privacy, testImageInfoModel.getPrivacy());
    }
}
