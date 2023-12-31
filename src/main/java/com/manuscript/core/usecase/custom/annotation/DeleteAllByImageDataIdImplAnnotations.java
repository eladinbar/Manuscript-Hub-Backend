package com.manuscript.core.usecase.custom.annotation;

import com.manuscript.core.domain.annotation.repository.IAnnotationRepositoryService;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeleteAllByImageDataIdImplAnnotations implements IDeleteAllByImageDataIdAnnotations {
    private final IAnnotationRepositoryService _serviceRepo;
    @Override
    public void deleteAllByImageDataId(UUID imageDataId) { _serviceRepo.deleteAllByImageDataId(imageDataId); }
}
