package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TransferOwnershipImpl implements ITransferOwnership{
    private final IImageRepositoryService _serviceRepo;
    @Override
    public ImageInfoModel transferOwnership(ImageInfoModel imageInfoModel, String newOwnerUid) { return _serviceRepo.transferOwnership(imageInfoModel, newOwnerUid); }
}
