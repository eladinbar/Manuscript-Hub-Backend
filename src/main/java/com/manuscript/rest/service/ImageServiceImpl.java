package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.image.*;
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
    private final IGetAllPublicImages getAllPublicImages;

    @Override
    public ImageResponse save(ImageRequest imageRequest) throws IllegalArgumentException, NoImageFoundException, UnauthorizedException{
        ImageModel imageModel = imageRequestMapper.restToModel(imageRequest);
        imageModel = createImageUseCase.create(imageModel);
        ImageResponse imageResponse= imageResponseMapper.modelToRest(imageModel);
        verifyPermission(imageRequest.getUid(), imageResponse.getUid(), imageResponse.getStatus());
        return imageResponse;
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
        ImageResponse imageResponse= imageResponseMapper.modelToRest(imageModel);
        verifyPermission(imageRequest.getUid(), imageResponse.getUid(), imageResponse.getStatus());
        return imageResponse;
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

    @Override
    public List<ImageResponse> getAllPublicImages(){
        return getAllPublicImages.getAllPublicImages().stream().map(imageResponseMapper::modelToRest).collect(Collectors.toList());
    }

    private void verifyPermission(String requestUid, String responseUid, Status responseStatus) throws NoImageFoundException, UnauthorizedException {
        if(responseStatus.equals(Status.inactive))
            throw new NoImageFoundException();
        if(!requestUid.equals(responseUid))
            throw new UnauthorizedException();
    }

}
