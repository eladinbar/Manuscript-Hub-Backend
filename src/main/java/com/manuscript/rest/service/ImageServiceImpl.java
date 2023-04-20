package com.manuscript.rest.service;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.usecase.custom.image.ICreateImage;
import com.manuscript.core.usecase.custom.image.IGetAllImages;
import com.manuscript.core.usecase.custom.image.IGetByIdImage;
import com.manuscript.core.usecase.custom.image.IUpdateImage;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.ImageRequest;
import com.manuscript.rest.response.ImageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements IImageService {
    private final IRestMapper<ImageModel, ImageRequest> imageRequestMapper;
    private final IRestMapper<ImageModel, ImageResponse> imageResponseMapper;
    private final ICreateImage createImageUseCase;
    private final IGetAllImages getAllImagesUseCase;
    private final IGetByIdImage getByIdImageUseCase;
    private final IUpdateImage updateImageUseCase;

    @Override
    public ImageResponse save(ImageRequest imageRequest) {
        ImageModel imageModel = imageRequestMapper.restToModel(imageRequest);
        imageModel = createImageUseCase.create(imageModel);
        return imageResponseMapper.modelToRest(imageModel);
    }

    @Override
    public ImageResponse getById(UUID id) {
        return imageResponseMapper.modelToRest(getByIdImageUseCase.getById(id).get());
        //todo: need to change if represent
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public ImageResponse update(ImageRequest imageRequest) {
        ImageModel imageModel = imageRequestMapper.restToModel(imageRequest);
        imageModel = updateImageUseCase.update(imageModel);
        return imageResponseMapper.modelToRest(imageModel);
    }

    @Override
    public List<ImageResponse> getAll() {
        return getAllImagesUseCase.getAll().stream().map(imageResponseMapper::modelToRest).collect(Collectors.toList());
    }

    @Override
    public List<ImageResponse> getAllByUid(String uid) {
        return getAllImagesUseCase.getAll().stream().map(imageResponseMapper::modelToRest).collect(Collectors.toList()).stream()
                .filter(image ->
                       image.getUid() != null && image.getUid().equals(uid)).collect(Collectors.toList());
    }
}
