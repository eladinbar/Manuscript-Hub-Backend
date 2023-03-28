package com.manuscript.core.usecase.custom.annotation;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.usecase.common.GetAllUseCaseImpl;

public class GetAllAnnotationsImpl extends GetAllUseCaseImpl<AnnotationModel> implements IGetAllAnnotations {
    public GetAllAnnotationsImpl(IBaseRepositoryService<AnnotationModel> serviceRepo) {
        super(serviceRepo);
    }
}
