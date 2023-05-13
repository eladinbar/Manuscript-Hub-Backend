package com.manuscript.infrastructure.persistence.sql.repositories;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.infrastructure.persistence.sql.entities.ImageEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IImageRepo extends JpaRepository<ImageEntity, UUID> {
    List<ImageEntity> getImagesByUser(UserEntity user);
    List<ImageEntity> getImagesByPrivacy(Privacy privacy);
    List<ImageEntity> getImagesByPrivacyAndUser(Privacy privacy, UserEntity user);
}
