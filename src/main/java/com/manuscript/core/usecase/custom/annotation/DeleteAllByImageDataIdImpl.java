package com.manuscript.core.usecase.custom.annotation;

import com.manuscript.core.domain.annotation.repository.IAnnotationRepositoryService;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeleteAllByImageDataIdImpl implements IDeleteAllByImageDataId{
    private final IAnnotationRepositoryService _serviceRepo;
    @Override
    public void deleteAllByImageDataId(UUID imageDataId) { _serviceRepo.deleteAllByImageDataId(imageDataId); }
}
