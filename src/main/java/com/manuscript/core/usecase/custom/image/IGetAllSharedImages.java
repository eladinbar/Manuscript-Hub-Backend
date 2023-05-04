package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.models.ImageModel;

import java.util.List;

public interface IGetAllSharedImages {
    List<ImageModel> getAllSharedImages(String userId);
}
