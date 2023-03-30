package com.manuscript.core.domain.annotation.repository;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAnnotationRepositoryService extends IBaseRepositoryService<AnnotationModel> {
    List<Optional<AnnotationEntity>> getAllByDocumentId(UUID documentId);
    void deleteAllByDocumentId(UUID documentId);
}
