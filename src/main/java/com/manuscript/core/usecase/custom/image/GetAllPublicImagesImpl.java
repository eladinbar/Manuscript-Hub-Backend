package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllPublicImagesImpl implements IGetAllPublicImages {

    private final IImageRepositoryService _serviceRepo;

    @Override
    public List<ImageModel> getAllPublicImages() {
        return _serviceRepo.getAllPublicImages();
    }
}
