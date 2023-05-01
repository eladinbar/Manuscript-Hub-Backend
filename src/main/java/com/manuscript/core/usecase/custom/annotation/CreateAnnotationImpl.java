package com.manuscript.core.usecase.custom.annotation;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;

public class CreateAnnotationImpl extends CreateUseCaseImpl<AnnotationModel> implements ICreateAnnotation {
    public CreateAnnotationImpl(IBaseRepositoryService<AnnotationModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
