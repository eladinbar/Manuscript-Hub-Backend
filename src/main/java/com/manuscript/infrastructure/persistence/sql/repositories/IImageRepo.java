package com.manuscript.infrastructure.persistence.sql.repositories;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.infrastructure.persistence.sql.entities.ImageEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IImageRepo extends JpaRepository<ImageEntity, UUID> {
    List<ImageEntity> findAllByUser(UserEntity user);
    List<ImageEntity> findAllByPrivacy(Privacy privacy);
    List<ImageEntity> findAllByPrivacyAndUser(Privacy privacy, UserEntity user);
    }
