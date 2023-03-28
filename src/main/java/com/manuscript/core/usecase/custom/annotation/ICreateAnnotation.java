package com.manuscript.core.usecase.custom.annotation;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.usecase.common.ICreateUseCase;

public interface ICreateAnnotation extends ICreateUseCase<AnnotationModel> {
    @Override
    AnnotationModel create(AnnotationModel model) throws IllegalArgumentException;
}
