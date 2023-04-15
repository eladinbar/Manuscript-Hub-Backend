package com.manuscript.core.usecase.custom.annotation;

import com.manuscript.core.domain.annotation.models.AnnotationModel;
import com.manuscript.core.domain.annotation.repository.IAnnotationRepositoryService;

import java.util.List;
import java.util.UUID;

public class GetAllByImageIdAnnotationsImpl implements IGetAllByImageIdAnnotations {
    private final IAnnotationRepositoryService _serviceRepo;

    public GetAllByImageIdAnnotationsImpl(IAnnotationRepositoryService serviceRepo) {
        this._serviceRepo = serviceRepo;
    }

    @Override
    public List<AnnotationModel> getAllByImageId(UUID imageId) {
        return _serviceRepo.getAllByImageId(imageId);
    }
}
