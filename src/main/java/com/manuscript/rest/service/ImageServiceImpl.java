package com.manuscript.rest.service;


import com.manuscript.core.domain.document.models.ImageModel;
import com.manuscript.core.usecase.common.IGetByIdUseCase;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements IImageService {
    private final IRestMapper<ImageModel, ImageResponse> mapperResponseInfoModel;
    private final IGetByIdUseCase<ImageModel> getByIdDocumentUseCase;

    @Override
    public ImageResponse getById(UUID id) {
        return mapperResponseInfoModel.modelToRest(getByIdDocumentUseCase.getById(id).get());
        //todo: need to change if represent
    }

    @Override
    public void save(ImageRequest document) {

    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void update(ImageRequest document) {

    }


}
