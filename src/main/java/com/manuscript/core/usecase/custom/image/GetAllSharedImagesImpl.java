package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllSharedImagesImpl implements IGetAllSharedImages {
    private final IImageRepositoryService _serviceRepo;

    @Override
    public List<ImageInfoModel> getAllSharedImages(String userId) {
        return _serviceRepo.getAllSharedImages(userId);
    }
}
