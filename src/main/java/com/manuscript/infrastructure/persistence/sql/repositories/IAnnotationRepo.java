package com.manuscript.infrastructure.persistence.sql.repositories;

import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAnnotationRepo extends JpaRepository<AnnotationEntity, UUID> {
    List<AnnotationEntity> findAllByImageDataId(UUID imageDataId);
    void deleteAllByImageDataId(UUID imageDataId);
}
