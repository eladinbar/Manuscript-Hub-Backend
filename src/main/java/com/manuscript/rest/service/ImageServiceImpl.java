package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.image.*;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.forms.request.ImageRequest;
import com.manuscript.rest.forms.response.ImageResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements IImageService {
    private final IRestMapper<ImageModel, ImageRequest> imageRequestMapper;
    private final IRestMapper<ImageModel, ImageResponse> imageResponseMapper;
    private final IRestMapper<ImageDataModel, ImageDataRequest> imageDataRequestMapper;
    private final IRestMapper<ImageDataModel, ImageDataResponse> imageDataResponseMapper;
    private final ICreateImage createImageUseCase;
    private final ICreateImageData createImageDataUseCase;
    private final IUpdateImage updateImageUseCase;
    private final IGetByIdImage getByIdImageUseCase;
    private final IGetAllByImageIdImagesData getAllByImageIdImagesDataUseCase;
    private final IGetAllByUidImages getAllByUidImagesUseCase;
    private final IGetAllPublicImages getAllPublicImages;
    private final IGetAllSharedImages getAllSharedImages;
    private final IDeleteByIdImage deleteByIdImageUseCase;
    private final IDeleteByIdImageData deleteByIdImageDataUseCase;


    @Override
    public ImageResponse save(ImageRequest imageRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageModel imageModel = imageRequestMapper.restToModel(imageRequest);
        imageModel = createImageUseCase.create(imageModel);
        ImageResponse imageResponse = imageResponseMapper.modelToRest(imageModel);
        verifyOwnerPermissions(imageRequest.getUserId(), imageResponse.getUserId(), imageResponse.getStatus());
        return imageResponse;
    }

    @Override
    public ImageDataResponse saveData(ImageDataRequest imageDataRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageDataModel imageDataModel = imageDataRequestMapper.restToModel(imageDataRequest);
        imageDataModel = createImageDataUseCase.create(imageDataModel);
        ImageDataResponse imageDataResponse = imageDataResponseMapper.modelToRest(imageDataModel);
        return imageDataResponse;
    }

    @Override
    public ImageResponse update(ImageRequest imageRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageModel imageModel = imageRequestMapper.restToModel(imageRequest);
        imageModel = updateImageUseCase.update(imageModel);
        ImageResponse imageResponse= imageResponseMapper.modelToRest(imageModel);
        verifyOwnerPermissions(imageRequest.getUserId(), imageResponse.getUserId(), imageResponse.getStatus());
        verifySharedPermissions(imageRequest.getUserId(), imageResponse.getUserId(), imageResponse.getPrivacy(), imageResponse.getSharedUserIds());
        return imageResponse;
    }
    @Override
    public ImageResponse getById(UUID imageId, String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        Optional<ImageModel> optionalImageModel = getByIdImageUseCase.getById(imageId);
        if (!optionalImageModel.isPresent())
            throw new NoImageFoundException();
        ImageModel imageModel = optionalImageModel.get();
        ImageResponse imageResponse = imageResponseMapper.modelToRest(imageModel);
        verifyOwnerPermissions(userId, imageResponse.getUserId(), imageResponse.getStatus());
        verifySharedPermissions(userId, imageResponse.getUserId(), imageResponse.getPrivacy(), imageResponse.getSharedUserIds());
        return imageResponse;
    }

    public List<ImageDataResponse> getAllByIdData(UUID imageId, String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageResponse imageResponse = getById(imageId, userId);
        verifyOwnerPermissions(userId, imageResponse.getUserId(), imageResponse.getStatus());
        verifySharedPermissions(userId, imageResponse.getUserId(), imageResponse.getPrivacy(), imageResponse.getSharedUserIds());
        List<ImageDataModel> imageDataModelList = getAllByImageIdImagesDataUseCase.getAllByImageId(imageId);
        List<ImageDataResponse> imageDataResponseList = new ArrayList<>();
        for (ImageDataModel imageDataModel : imageDataModelList){
            ImageDataResponse imageDataResponse = imageDataResponseMapper.modelToRest(imageDataModel);
            imageDataResponseList.add(imageDataResponse);
        }
        return imageDataResponseList;
    }

    @Override
    public List<ImageResponse> getAllByUid(String userId) throws IllegalArgumentException, NoUserFoundException {
        List<ImageModel> imageModelList = getAllByUidImagesUseCase.getAllByUidImages(userId);
        List<ImageResponse> imageResponseList = ImageResponseListToModelList(imageModelList);
        return imageResponseList;
    }

    @Override
    public List<ImageResponse> getAllPublicImages() {
        List<ImageModel> imageModelList = getAllPublicImages.getAllPublicImages();
        List<ImageResponse> imageResponseList = ImageResponseListToModelList(imageModelList);
        return imageResponseList;
    }

    @Override
    public List<ImageResponse> getAllSharedImages(String userId) throws IllegalArgumentException, NoUserFoundException {
        List<ImageModel> imageModelList = getAllSharedImages.getAllSharedImages(userId);
        List<ImageResponse> imageResponseList = ImageResponseListToModelList(imageModelList);
        return imageResponseList;
    }

    @Override
    public void deleteById(UUID imageId, String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        //TODO: implement delete
    }

    private List<ImageResponse> ImageResponseListToModelList(List<ImageModel> imageModelList){
        List<ImageResponse> imageResponseList = new ArrayList<>();
        for (ImageModel imageModel : imageModelList){
            ImageResponse imageResponse = imageResponseMapper.modelToRest(imageModel);
            imageResponseList.add(imageResponse);
        }
        return imageResponseList;
    }

    private void verifyOwnerPermissions(String requestOwnerId, String responseOwnerId, Status responseStatus) throws NoImageFoundException, UnauthorizedException {
        if(responseStatus.equals(Status.inactive))
            throw new NoImageFoundException();
        if(!requestOwnerId.equals(responseOwnerId))
            throw new UnauthorizedException();
    }

    private void verifySharedPermissions(String requestSharedId, String responseOwnerId, Privacy responsePrivacy, List<String> responseSharedUserIds) throws UnauthorizedException {
        if (!requestSharedId.equals(responseOwnerId)) {
            if (!responsePrivacy.equals(Privacy.Shared))
                throw new UnauthorizedException();
            else if (!responseSharedUserIds.contains(requestSharedId)) {
                throw new UnauthorizedException();
            }
        }
    }

}
