package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageDataModel;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.core.usecase.custom.image.*;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements IImageService {
    private final IRestMapper<ImageInfoModel, ImageInfoRequest> imageInfoRequestMapper;
    private final IRestMapper<ImageInfoModel, ImageInfoResponse> imageInfoResponseMapper;
    private final IRestMapper<ImageDataModel, ImageDataRequest> imageDataRequestMapper;
    private final IRestMapper<ImageDataModel, ImageDataResponse> imageDataResponseMapper;
    private final ICreateImage createImageUseCase;
    private final ICreateImageData createImageDataUseCase;
    private final IUpdateImage updateImageUseCase;
    private final IGetByIdImageInfo getByIdImageInfoUseCase;
    private final IGetByIdImageData getByIdImageDataUseCase;
    private final IGetAllByImageIdImageDatas getAllByImageIdImagesDataUseCase;
    private final IGetAllByUidImages getAllByUidImagesUseCase;
    private final IGetAllPublicImages getAllPublicImagesUseCase;
    private final IGetAllSharedImages getAllSharedImagesUseCase;
    private final IDeleteByIdImage deleteByIdImageUseCase;
    private final IDeleteByIdImageData deleteByIdImageDataUseCase;


    @Override
    public ImageInfoResponse saveInfo(ImageInfoRequest imageInfoRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageInfoModel imageInfoModel = imageInfoRequestMapper.restToModel(imageInfoRequest);
        imageInfoModel = createImageUseCase.create(imageInfoModel);
        ImageInfoResponse imageInfoResponse = imageInfoResponseMapper.modelToRest(imageInfoModel);
        verifyOwnerPermissions(imageInfoRequest.getUid(), imageInfoResponse.getUid(), imageInfoResponse.getStatus());
        return imageInfoResponse;
    }

    @Override
    public ImageDataResponse saveData(ImageDataRequest imageDataRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageDataModel imageDataModel = imageDataRequestMapper.restToModel(imageDataRequest);
        imageDataModel = createImageDataUseCase.create(imageDataModel);
        ImageDataResponse imageDataResponse = imageDataResponseMapper.modelToRest(imageDataModel);
        return imageDataResponse;
    }

    @Override
    public ImageInfoResponse updateInfo(ImageInfoRequest imageInfoRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageInfoModel imageInfoModel = imageInfoRequestMapper.restToModel(imageInfoRequest);
        imageInfoModel = updateImageUseCase.update(imageInfoModel);
        ImageInfoResponse imageInfoResponse = imageInfoResponseMapper.modelToRest(imageInfoModel);
        verifyOwnerPermissions(imageInfoRequest.getUid(), imageInfoResponse.getUid(), imageInfoResponse.getStatus());
        verifySharedPermissions(imageInfoRequest.getUid(), imageInfoResponse.getUid(), imageInfoResponse.getPrivacy(), imageInfoResponse.getSharedUserIds());
        return imageInfoResponse;
    }
    @Override
    public ImageInfoResponse getByIdInfo(UUID imageInfoId, String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        Optional<ImageInfoModel> optionalImageInfoModel = getByIdImageInfoUseCase.getById(imageInfoId);
        if (!optionalImageInfoModel.isPresent())
            throw new NoImageFoundException();
        ImageInfoModel imageInfoModel = optionalImageInfoModel.get();
        ImageInfoResponse imageInfoResponse = imageInfoResponseMapper.modelToRest(imageInfoModel);
        verifyOwnerPermissions(userId, imageInfoResponse.getUid(), imageInfoResponse.getStatus());
        verifySharedPermissions(userId, imageInfoResponse.getUid(), imageInfoResponse.getPrivacy(), imageInfoResponse.getSharedUserIds());
        return imageInfoResponse;
    }

    @Override
    public ImageDataResponse getByIdData(UUID imageDataId, String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        Optional<ImageDataModel> optionalImageDataModel = getByIdImageDataUseCase.getById(imageDataId);
        if (!optionalImageDataModel.isPresent())
            throw new NoImageFoundException();
        ImageDataModel imageDataModel = optionalImageDataModel.get();
        ImageDataResponse imageDataResponse = imageDataResponseMapper.modelToRest(imageDataModel);
        ImageInfoResponse imageInfoResponse = getByIdInfo(imageDataResponse.getInfoId(), userId);
        verifyOwnerPermissions(userId, imageInfoResponse.getUid(), imageInfoResponse.getStatus());
        verifySharedPermissions(userId, imageInfoResponse.getUid(), imageInfoResponse.getPrivacy(), imageInfoResponse.getSharedUserIds());
        return imageDataResponse;
    }

    public List<ImageDataResponse> getAllByImageInfoIdImageData(UUID imageId, String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageInfoResponse imageInfoResponse = getByIdInfo(imageId, userId);
        verifyOwnerPermissions(userId, imageInfoResponse.getUid(), imageInfoResponse.getStatus());
        verifySharedPermissions(userId, imageInfoResponse.getUid(), imageInfoResponse.getPrivacy(), imageInfoResponse.getSharedUserIds());
        List<ImageDataModel> imageDataModelList = getAllByImageIdImagesDataUseCase.getAllByImageId(imageId);
        List<ImageDataResponse> imageDataResponseList = new ArrayList<>();
        for (ImageDataModel imageDataModel : imageDataModelList){
            ImageDataResponse imageDataResponse = imageDataResponseMapper.modelToRest(imageDataModel);
            imageDataResponseList.add(imageDataResponse);
        }
        return imageDataResponseList;
    }

    @Override
    public List<ImageInfoResponse> getAllByUidImageInfos(String userId) throws IllegalArgumentException, NoUserFoundException {
        List<ImageInfoModel> imageInfoModelList = getAllByUidImagesUseCase.getAllByUidImages(userId);
        List<ImageInfoResponse> imageInfoResponseList = ImageModelListToResponseList(imageInfoModelList);
        return imageInfoResponseList;
    }

    @Override
    public List<ImageInfoResponse> getAllPublicImages() {
        List<ImageInfoModel> imageInfoModelList = getAllPublicImagesUseCase.getAllPublicImages();
        List<ImageInfoResponse> imageInfoResponseList = ImageModelListToResponseList(imageInfoModelList);
        return imageInfoResponseList;
    }

    @Override
    public List<ImageInfoResponse> getAllSharedImages(String userId) throws IllegalArgumentException, NoUserFoundException {
        List<ImageInfoModel> imageInfoModelList = getAllSharedImagesUseCase.getAllSharedImages(userId);
        List<ImageInfoResponse> imageInfoResponseList = ImageModelListToResponseList(imageInfoModelList);
        return imageInfoResponseList;
    }

    @Override
    public void deleteByIdImageInfo(UUID imageInfoId, String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        //TODO: implement delete
    }

    @Override
    public void deleteByIdImageData(UUID imageDataId, String userId) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        //TODO: implement delete
    }

    private List<ImageInfoResponse> ImageModelListToResponseList(List<ImageInfoModel> imageInfoModelList){
        List<ImageInfoResponse> imageInfoResponseList = new ArrayList<>();
        for (ImageInfoModel imageInfoModel : imageInfoModelList){
            ImageInfoResponse imageInfoResponse = imageInfoResponseMapper.modelToRest(imageInfoModel);
            imageInfoResponseList.add(imageInfoResponse);
        }
        return imageInfoResponseList;
    }

    private void verifyOwnerPermissions(String requestOwnerId, String responseOwnerId, Status responseStatus) throws NoImageFoundException, UnauthorizedException {
        if(responseStatus.equals(Status.Disabled))
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
