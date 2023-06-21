package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageInfoModel;

public interface ITransferOwnership {
    ImageInfoModel transferOwnership(ImageInfoModel imageInfoModel, String newOwnerUid);
}
