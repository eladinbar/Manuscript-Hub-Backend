package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UnauthorizedException;
import com.manuscript.rest.forms.request.ImageDataRequest;
import com.manuscript.rest.forms.request.ImageInfoRequest;
import com.manuscript.rest.forms.response.ImageDataResponse;
import com.manuscript.rest.forms.response.ImageInfoResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IImageService {
    ImageInfoResponse saveInfo(ImageInfoRequest imageInfoRequest)                                       throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageDataResponse saveData(ImageDataRequest imageDataRequest)                                       throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageInfoResponse updateInfo(ImageInfoRequest imageInfoRequest)                                     throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageInfoResponse shareImage(ImageInfoRequest imageInfoRequest, String[] sharedUserEmails);
    List<String> getAllSharedEmailsByImageInfoId(UUID imageInfoId, String ownerUid);
    ImageInfoResponse getByIdInfo(UUID imageInfoId, String uid)                                      throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageDataResponse getByIdData(UUID imageDataId, String uid)                                      throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    List<ImageDataResponse> getAllByImageInfoIdImageDatas(UUID imageId, String uid)                  throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    List<ImageInfoResponse> getAllByUidImageInfos(String uid)                                        throws IllegalArgumentException, NoUserFoundException;
    List<ImageInfoResponse> getAllPublicImages();
    List<ImageInfoResponse> getAllSharedImages(String uid)                                           throws IllegalArgumentException, NoUserFoundException;
    void deleteByIdImageInfo(UUID imageInfoId, String uid)                                           throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    void deleteByIdImageData(UUID imageDataId, String uid, boolean deleteAnnotation)                 throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    Map<Privacy, List<ImageInfoResponse>> getImageInfoByTextSearch(String searchText, String uid)    throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
    ImageInfoResponse transferOwnership(ImageInfoRequest imageInfoRequest, String newOwnerUid)          throws IllegalArgumentException, NoImageFoundException, NoUserFoundException, UnauthorizedException;
}
