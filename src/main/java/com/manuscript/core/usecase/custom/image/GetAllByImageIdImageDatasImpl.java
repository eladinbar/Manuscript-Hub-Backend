package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.domain.image.repository.IImageDataRepositoryService;
import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class GetAllByImageIdImageDatasImpl implements IGetAllByImageIdImageDatas{
    private final IImageDataRepositoryService _serviceRepo;
    @Override
    public List<ImageDataModel> getAllByImageId(UUID imageId) { return _serviceRepo.getAllByImageIdData(imageId); }
}
