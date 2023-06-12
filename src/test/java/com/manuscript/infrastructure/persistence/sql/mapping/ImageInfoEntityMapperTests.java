package com.manuscript.infrastructure.persistence.sql.mapping;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.infrastructure.persistence.sql.entities.ImageEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageInfoEntityMapperTests {
    //test classes
    private final ImageEntityMapperImpl imageEntityMapperImpl = new ImageEntityMapperImpl();
    private ImageEntity imageInfoEntity;
    private ImageInfoModel imageInfoModel;
    private UserEntity userEntity;

    //test data
    private final String uid = "2UYxH92SpBQfkRgEeN75EBdvM9r1";
    private final String title = "title";
    private final String author = "author";
    private final String description = "description";
    private final Date publicationDate = new Date();
    private final String tag = "tag";
    private final List<String> tags = new ArrayList<>();
    private JSONArray jsonTags;
    private final String addedUid = "2UYxH92SpBQfkRgEeN75EBdvM9r2";
    private final List<String> sharedUserIds = new ArrayList<>();
    private JSONArray jsonSharedUserIds;
    private final UUID imageId = UUID.randomUUID();
    private final Status status = Status.Enabled;
    private final Privacy privacy = Privacy.Public;
    private Date createdTime;
    private Date updatedTime;
    private final UUID userId = UUID.randomUUID();
    private final String email = "email@email.com";
    private final String name = "Bob";
    private final String phoneNumber = "0541234567";
    private final Role role = Role.User;


    @BeforeAll
    public void setup() {
        //data setup
        tags.add(tag);
        sharedUserIds.add(addedUid);
        jsonTags = new JSONArray(tags);
        jsonSharedUserIds = new JSONArray(sharedUserIds);

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
        //user setup
        userEntity = UserEntity.builder()
                .id(userId)
                .uid(uid)
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .status(status)
                .role(role)
                .build();

        //entity setup
        imageInfoEntity = ImageEntity.builder()
                .id(imageId)
                .user(userEntity)
                .title(title)
                .author(author)
                .description(description)
                .publicationDate(publicationDate)
                .status(status)
                .privacy(privacy)
                .tags(jsonTags)
                .sharedUserIds(jsonSharedUserIds)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();

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
    public void modelToEntity_Success() {
        //act
        ImageEntity testImageInfoEntity = imageEntityMapperImpl.modelToEntity(imageInfoModel);

        //assert
        assertNotNull(testImageInfoEntity);
        assertEquals(uid, testImageInfoEntity.getUser().getUid());
        assertEquals(title, testImageInfoEntity.getTitle());
        assertEquals(author, testImageInfoEntity.getAuthor());
        assertEquals(description, testImageInfoEntity.getDescription());
        assertEquals(publicationDate, testImageInfoEntity.getPublicationDate());
        assertEquals(status, testImageInfoEntity.getStatus());
        assertEquals(privacy, testImageInfoEntity.getPrivacy());
        assertEquals(jsonTags.toList(), testImageInfoEntity.getTags().toList());
        assertEquals(jsonSharedUserIds.toList(), testImageInfoEntity.getSharedUserIds().toList());
        assertEquals(status, testImageInfoEntity.getStatus());
        assertEquals(privacy, testImageInfoEntity.getPrivacy());
        assertEquals(createdTime, testImageInfoEntity.getCreatedTime());
        assertEquals(updatedTime, testImageInfoEntity.getUpdatedTime());
    }

    @Test
    public void entityToModel_Success() {
        //act
        ImageInfoModel testImageInfoModel = imageEntityMapperImpl.entityToModel(imageInfoEntity);

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
        assertEquals(createdTime, testImageInfoModel.getCreatedTime());
        assertEquals(updatedTime, testImageInfoModel.getUpdatedTime());
    }
}
