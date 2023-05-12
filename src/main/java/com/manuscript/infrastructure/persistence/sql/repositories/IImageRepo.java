package com.manuscript.infrastructure.persistence.sql.repositories;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.infrastructure.persistence.nosql.documents.ImageDataDocument;
import com.manuscript.infrastructure.persistence.sql.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IImageRepo extends JpaRepository<ImageEntity, UUID> {
    List<ImageEntity> getImagesByUid(String userId);
    List<ImageEntity> getImagesByPrivacy(Privacy privacy);
    List<ImageEntity> getImagesByPrivacyAndUid(Privacy privacy, String userId);
}
