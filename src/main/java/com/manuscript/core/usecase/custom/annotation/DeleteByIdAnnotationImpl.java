package com.manuscript.core.usecase.custom.annotation;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.usecase.common.DeleteByIdUseCaseImpl;

public class DeleteByIdAnnotationImpl extends DeleteByIdUseCaseImpl<AnnotationModel> implements IDeleteByIdAnnotation {
    public DeleteByIdAnnotationImpl(IBaseRepositoryService<AnnotationModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
