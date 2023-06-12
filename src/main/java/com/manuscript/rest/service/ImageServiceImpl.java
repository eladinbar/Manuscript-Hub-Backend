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
import com.manuscript.rest.forms.response.UserResponse;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.forms.response.ImageInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements IImageService {
    private final IRestMapper<ImageInfoModel, ImageInfoRequest> imageInfoRequestMapper;
    private final IRestMapper<ImageInfoModel, ImageInfoResponse> imageInfoResponseMapper;
    private final IRestMapper<ImageDataModel, ImageDataRequest> imageDataRequestMapper;
    private final IRestMapper<ImageDataModel, ImageDataResponse> imageDataResponseMapper;
    private final IUserService userService;
    private final IAnnotationService annotationService;
    private final ICreateImage createImageUseCase;
    private final ICreateImageData createImageDataUseCase;
    private final IUpdateImage updateImageUseCase;
    private final IGetByIdImageInfo getByIdImageInfoUseCase;
    private final IGetByIdImageData getByIdImageDataUseCase;
    private final IGetAllByImageIdImageDatas getAllByImageIdImagesDataUseCase;
    private final IGetAllByUidImages getAllByUidImagesUseCase;
    private final IGetAllPublicImages getAllPublicImagesUseCase;
    private final IGetAllSharedImages getAllSharedImagesUseCase;
    private final IDeleteByIdImageInfo deleteByIdImageInfoUseCase;
    private final IDeleteByIdImageData deleteByIdImageDataUseCase;
    private final IGetImageInfoByTextSearch getImageInfoByTextSearchUseCase;
    private final ITransferOwnership transferOwnershipUseCase;
    private final IGetAllEmailsByImageInfoId getAllEmailsByImageInfoIdUseCase;

    @Override
    public ImageInfoResponse saveInfo(ImageInfoRequest imageInfoRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageInfoModel imageInfoModel = imageInfoRequestMapper.restToModel(imageInfoRequest);
        imageInfoModel = createImageUseCase.create(imageInfoModel);
        ImageInfoResponse imageInfoResponse = imageInfoResponseMapper.modelToRest(imageInfoModel);
        verifyPermissions(imageInfoRequest.getUid(), imageInfoResponse.getUid(), imageInfoResponse.getSharedUserIds(), imageInfoResponse.getStatus(), imageInfoResponse.getPrivacy());
        return imageInfoResponse;
    }

    @Override
    public ImageDataResponse saveData(ImageDataRequest imageDataRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageDataModel imageDataModel = imageDataRequestMapper.restToModel(imageDataRequest);
        imageDataModel = createImageDataUseCase.create(imageDataModel);
        return imageDataResponseMapper.modelToRest(imageDataModel);
    }

    @Override
    public ImageInfoResponse updateInfo(ImageInfoRequest imageInfoRequest) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        getByIdInfo(imageInfoRequest.getId(), imageInfoRequest.getUid()); //get requested image from db to check if it exists
        verifyModifyPermissions(imageInfoRequest.getId(), imageInfoRequest.getUid());
        ImageInfoModel imageInfoModel = imageInfoRequestMapper.restToModel(imageInfoRequest);
        imageInfoModel = updateImageUseCase.update(imageInfoModel);
        return imageInfoResponseMapper.modelToRest(imageInfoModel);
    }

    @Override
    public ImageInfoResponse shareImage(ImageInfoRequest imageInfoRequest, String[] sharedUserEmails) {
        getByIdInfo(imageInfoRequest.getId(), imageInfoRequest.getUid()); //get requested image from db to check if it exists
        verifyOwnerPermissions(imageInfoRequest.getId(), imageInfoRequest.getUid());
        ImageInfoModel imageInfoModel = imageInfoRequestMapper.restToModel(imageInfoRequest);

        //List to keep track of users that weren't found in the database
        //Can potentially be used to return an appropriate partial error message
        List<String> nonExistingUsers = new ArrayList<>();
        imageInfoModel.setSharedUserIds(new ArrayList<>());
        for (String email : sharedUserEmails) {
            try {
                UserResponse userResponse = userService.getByEmail(email);
                imageInfoModel.getSharedUserIds().add(userResponse.getUid());
            } catch (NoUserFoundException exception) {
                nonExistingUsers.add(email);
            }
        }
        if (nonExistingUsers.size() == sharedUserEmails.length)
            throw new NoUserFoundException("No valid users were found given the provided email addresses.");

        imageInfoModel = updateImageUseCase.update(imageInfoModel);
        return imageInfoResponseMapper.modelToRest(imageInfoModel);
    }

    @Override
    public List<String> getAllEmailsByImageInfoId(UUID imageInfoId, String ownerUid) {
        verifyOwnerPermissions(imageInfoId, ownerUid);
        return getAllEmailsByImageInfoIdUseCase.getAllEmailsByImageInfoIdImpl(imageInfoId, ownerUid);
    }

    @Override
    public ImageInfoResponse getByIdInfo(UUID imageInfoId, String uid) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        Optional<ImageInfoModel> optionalImageInfoModel = getByIdImageInfoUseCase.getById(imageInfoId);
        if (!optionalImageInfoModel.isPresent())
            throw new NoImageFoundException("No document was found.");
        ImageInfoModel imageInfoModel = optionalImageInfoModel.get();
        ImageInfoResponse imageInfoResponse = imageInfoResponseMapper.modelToRest(imageInfoModel);
        verifyPermissions(uid, imageInfoResponse.getUid(), imageInfoResponse.getSharedUserIds(), imageInfoResponse.getStatus(), imageInfoResponse.getPrivacy());
        return imageInfoResponse;
    }

    @Override
    public ImageDataResponse getByIdData(UUID imageDataId, String uid) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        Optional<ImageDataModel> optionalImageDataModel = getByIdImageDataUseCase.getById(imageDataId);
        if (!optionalImageDataModel.isPresent())
            throw new NoImageFoundException();
        ImageDataModel imageDataModel = optionalImageDataModel.get();
        ImageDataResponse imageDataResponse = imageDataResponseMapper.modelToRest(imageDataModel);
        getByIdInfo(imageDataResponse.getInfoId(), uid); //get requested image from db to check if permissions match.
        return imageDataResponse;
    }

    @Override
    public List<ImageDataResponse> getAllByImageInfoIdImageDatas(UUID imageInfoId, String uid) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        getByIdInfo(imageInfoId, uid); //get requested image from db to check if permissions match.
        List<ImageDataModel> imageDataModelList = getAllByImageIdImagesDataUseCase.getAllByImageId(imageInfoId);
        List<ImageDataResponse> imageDataResponseList = new ArrayList<>();
        for (ImageDataModel imageDataModel : imageDataModelList) {
            ImageDataResponse imageDataResponse = imageDataResponseMapper.modelToRest(imageDataModel);
            imageDataResponseList.add(imageDataResponse);
        }
        return imageDataResponseList;
    }

    @Override
    public List<ImageInfoResponse> getAllByUidImageInfos(String uid) throws IllegalArgumentException, NoUserFoundException {
        List<ImageInfoModel> imageInfoModelList = getAllByUidImagesUseCase.getAllByUidImageInfos(uid);
        List<ImageInfoResponse> imageInfoResponseList = ImageModelListToResponseList(imageInfoModelList);
        for (ImageInfoResponse imageInfoResponse : imageInfoResponseList) {
            verifyPermissions(uid, imageInfoResponse.getUid(), imageInfoResponse.getSharedUserIds(), imageInfoResponse.getStatus(), imageInfoResponse.getPrivacy());
        }
        return imageInfoResponseList;
    }

    @Override
    public List<ImageInfoResponse> getAllPublicImages() {
        List<ImageInfoModel> imageInfoModelList = getAllPublicImagesUseCase.getAllPublicImages();
        List<ImageInfoResponse> imageInfoResponseList = ImageModelListToResponseList(imageInfoModelList);
        return imageInfoResponseList;
    }

    @Override
    public List<ImageInfoResponse> getAllSharedImages(String uid) throws IllegalArgumentException, NoUserFoundException {
        List<ImageInfoModel> imageInfoModelList = getAllSharedImagesUseCase.getAllSharedImages(uid);
        List<ImageInfoResponse> imageInfoResponseList = ImageModelListToResponseList(imageInfoModelList);
        for (ImageInfoResponse imageInfoResponse : imageInfoResponseList) {
            verifyPermissions(uid, imageInfoResponse.getUid(), imageInfoResponse.getSharedUserIds(), imageInfoResponse.getStatus(), imageInfoResponse.getPrivacy());
        }
        return imageInfoResponseList;
    }

    @Override
    public void deleteByIdImageInfo(UUID imageInfoId, String uid) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        verifyDeletePermissions(imageInfoId, uid);
        List<ImageDataResponse> imageDataResponseList = getAllByImageInfoIdImageDatas(imageInfoId, uid);
        for (ImageDataResponse imageDataResponse : imageDataResponseList) {
            deleteByIdImageData(imageDataResponse.getId(), uid, true);
        }
        deleteByIdImageInfoUseCase.deleteById(imageInfoId);
    }

    @Override
    public void deleteByIdImageData(UUID imageDataId, String uid, boolean deleteAnnotation) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        ImageDataResponse imageDataResponse = getByIdData(imageDataId, uid); //get requested image from db to check if permissions match.
        verifyDeletePermissions(imageDataResponse.getInfoId(), uid);
        if (deleteAnnotation) {
            annotationService.deleteAllByImageDataId(imageDataId);
        }
        deleteByIdImageDataUseCase.deleteById(imageDataId);
    }

    @Override
    public Map<Privacy, List<ImageInfoResponse>> getImageInfoByTextSearch(String searchText, String uid) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        Map<Privacy, List<ImageInfoResponse>> imageInfoResponseMap = new HashMap<>();
        Map<Privacy, List<ImageInfoModel>> imageInfoModelMap = getImageInfoByTextSearchUseCase.getImageInfoByTextSearch(searchText, uid);
        for (Map.Entry<Privacy, List<ImageInfoModel>> entry : imageInfoModelMap.entrySet()) {
            List<ImageInfoResponse> imageInfoResponseList = ImageModelListToResponseList(entry.getValue());
            for (ImageInfoResponse imageInfoResponse : imageInfoResponseList) {
                verifyPermissions(uid, imageInfoResponse.getUid(), imageInfoResponse.getSharedUserIds(), imageInfoResponse.getStatus(), imageInfoResponse.getPrivacy());
            }
            imageInfoResponseMap.put(entry.getKey(), imageInfoResponseList);
        }
        return imageInfoResponseMap;
    }

    @Override
    public ImageInfoResponse transferOwnership(ImageInfoRequest imageInfoRequest, String newOwnerUid) throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException {
        getByIdInfo(imageInfoRequest.getId(), imageInfoRequest.getUid()); //get requested image from db to check if permissions match.
        ImageInfoModel imageInfoModel = imageInfoRequestMapper.restToModel(imageInfoRequest);
        imageInfoModel = transferOwnershipUseCase.transferOwnership(imageInfoModel, newOwnerUid);
        ImageInfoResponse imageInfoResponse = imageInfoResponseMapper.modelToRest(imageInfoModel);
        return imageInfoResponse;
    }

    private List<ImageInfoResponse> ImageModelListToResponseList(List<ImageInfoModel> imageInfoModelList) {
        List<ImageInfoResponse> imageInfoResponseList = new ArrayList<>();
        for (ImageInfoModel imageInfoModel : imageInfoModelList) {
            ImageInfoResponse imageInfoResponse = imageInfoResponseMapper.modelToRest(imageInfoModel);
            imageInfoResponseList.add(imageInfoResponse);
        }
        return imageInfoResponseList;
    }

    private void verifyPermissions(String requestUid, String responseOwnerUid, List<String> responseSharedUserIds, Status responseStatus, Privacy responsePrivacy) throws UnauthorizedException {
        if (responseStatus.equals(Status.Disabled))
            throw new NoImageFoundException("Disabled images are unavailable.");
        if (!requestUid.equals(responseOwnerUid)) {
            if (!responsePrivacy.equals(Privacy.Public)) {
                if (!responsePrivacy.equals(Privacy.Shared))
                    throw new UnauthorizedException("You have no permissions to view this image.");
                else if (!responseSharedUserIds.contains(requestUid)) {
                    throw new UnauthorizedException("This document isn't shared with you.");
                }
            }
        }
    }

    private void verifyModifyPermissions(UUID imageInfoId, String uid) throws UnauthorizedException {
        ImageInfoResponse imageInfoResponse = getByIdInfo(imageInfoId, uid); //also checks permissions
        if (imageInfoResponse.getPrivacy() == Privacy.Public && !imageInfoResponse.getUid().equals(uid)) {
            throw new UnauthorizedException("Cannot modify public property if you are not the owner.");
        } else if (imageInfoResponse.getPrivacy() == Privacy.Shared) {
            if (!imageInfoResponse.getUid().equals(uid)) {
                throw new IllegalArgumentException("Only the owner can modify this item");
            }
        }
    }

    private void verifyDeletePermissions(UUID imageInfoId, String uid) throws UnauthorizedException {
        ImageInfoResponse imageInfoResponse = getByIdInfo(imageInfoId, uid); //also checks permissions
        if (imageInfoResponse.getPrivacy() == Privacy.Public && !imageInfoResponse.getUid().equals(uid)) {
            throw new UnauthorizedException("Cannot delete public property if you are not the owner.");
        } else if (imageInfoResponse.getPrivacy() == Privacy.Shared) {
            if (!imageInfoResponse.getUid().equals(uid)) {
                throw new IllegalArgumentException("Only the owner can delete this item.");
            } else {
                throw new IllegalArgumentException("This item is shared with other users\n" +
                        "please transfer item ownership or set its privacy to 'Private' first before attempting to delete it.");
            }
        }
    }

    private void verifyOwnerPermissions(UUID imageInfoId, String uid) throws UnauthorizedException {
        ImageInfoResponse imageInfoResponse = getByIdInfo(imageInfoId, uid); //also checks permissions
        if (!imageInfoResponse.getUid().equals(uid)) {
            throw new IllegalArgumentException("Only the owner can modify this item.");
        }
    }
}
