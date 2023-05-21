package com.manuscript.core.domain.annotation.repository;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;

import java.util.List;
import java.util.UUID;

public interface IAnnotationRepositoryService extends IBaseRepositoryService<AnnotationModel> {
    List<AnnotationModel> getAllByImageDataId(UUID imageDataId);
    void deleteAllByImageDataId(UUID imageDataId);
}
