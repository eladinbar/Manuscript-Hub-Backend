package com.manuscript.core.usecase.custom.annotation;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.annotation.repository.IAnnotationRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class GetAllByImageIdAnnotationsImpl implements IGetAllByImageIdAnnotations {
    private final IAnnotationRepositoryService _serviceRepo;

    @Override
    public List<AnnotationModel> getAllByImageId(UUID imageId) {
        return _serviceRepo.getAllByImageId(imageId);
    }
}
