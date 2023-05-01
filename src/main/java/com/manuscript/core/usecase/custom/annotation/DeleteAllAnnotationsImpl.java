package com.manuscript.core.usecase.custom.annotation;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.usecase.common.DeleteAllUseCaseImpl;

public class DeleteAllAnnotationsImpl extends DeleteAllUseCaseImpl<AnnotationModel> implements IDeleteAllAnnotations {
    public DeleteAllAnnotationsImpl(IBaseRepositoryService<AnnotationModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
