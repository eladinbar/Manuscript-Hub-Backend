package com.manuscript.core.usecase.custom.annotation;

import com.manuscript.core.domain.annotation.models.AnnotationModel;

import java.util.List;
import java.util.UUID;

public interface IGetAllByImageIdAnnotations {
    List<AnnotationModel> getAllByImageId(UUID imageId);
}
