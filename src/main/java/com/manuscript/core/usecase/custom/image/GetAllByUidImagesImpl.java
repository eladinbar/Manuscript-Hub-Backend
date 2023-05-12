package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.domain.image.repository.IImageDataRepositoryService;
import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllByUidImagesImpl implements IGetAllByUidImages{
    private final IImageRepositoryService _serviceRepo;
    @Override
    public List<ImageModel> getAllByUidImages(String userId) { return _serviceRepo.getAllByUidImages(userId); }
}
