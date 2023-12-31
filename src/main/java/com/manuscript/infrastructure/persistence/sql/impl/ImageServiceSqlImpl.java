package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.common.enums.Privacy;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.image.models.ImageInfoModel;
import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import com.manuscript.core.exceptions.NoImageFoundException;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.ImageEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IImageRepo;
import com.manuscript.infrastructure.persistence.sql.repositories.IUserRepo;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageServiceSqlImpl implements IImageRepositoryService {
    private final IUserRepo userRepo;
    private final IImageRepo imageRepo;
    private final IRepositoryEntityMapper<ImageInfoModel, ImageEntity> mapper;

    @Override
    @Transactional
    public ImageInfoModel save(ImageInfoModel imageInfoModel) throws IllegalArgumentException {
        ImageEntity imageEntity = mapper.modelToEntity(imageInfoModel);
        Optional<UserEntity> optionalUser = userRepo.findByUid(imageEntity.getUser().getUid());
        if(!optionalUser.isPresent())
            throw new IllegalArgumentException("No user found.\n" +
                    "This should not happen, please contact an administrator.");
        UserEntity user = optionalUser.get();
        imageEntity.setUser(user);
        imageEntity = imageRepo.save(imageEntity);
        return mapper.entityToModel(imageEntity);
    }

    @Override
    public ImageInfoModel update(ImageInfoModel imageInfoModel) throws IllegalArgumentException, NoImageFoundException {
        Optional<ImageEntity> optionalImageEntity = imageRepo.findById(imageInfoModel.getId());
        if (!optionalImageEntity.isPresent()){
            throw new NoImageFoundException();
        }
        ImageEntity newImageEntity = mapper.modelToEntity(imageInfoModel);
        ImageEntity currentImageEntity = optionalImageEntity.get();
        currentImageEntity.setTitle(newImageEntity.getTitle());
        currentImageEntity.setAuthor(newImageEntity.getAuthor());
        currentImageEntity.setPublicationDate(newImageEntity.getPublicationDate());
        currentImageEntity.setDescription(newImageEntity.getDescription());
        currentImageEntity.setTags(newImageEntity.getTags());
        currentImageEntity.setSharedUserIds(newImageEntity.getSharedUserIds());
        currentImageEntity.setPrivacy(newImageEntity.getPrivacy());
        currentImageEntity.setUpdatedTime(new Date());
        newImageEntity = imageRepo.save(currentImageEntity);
        return mapper.entityToModel(newImageEntity);
    }

    @Override
    public Optional<ImageInfoModel> getById(UUID id) throws IllegalArgumentException {
        Optional<ImageEntity> optionalImageEntity = imageRepo.findById(id);
        if (!optionalImageEntity.isPresent()){
            return Optional.empty();
        }
        ImageInfoModel imageInfoModel = mapper.entityToModel(optionalImageEntity.get());
        return Optional.of(imageInfoModel);
    }

    @Override
    public List<ImageInfoModel> getAllByUidImageInfos(String userId) throws IllegalArgumentException {
        Optional<UserEntity> optionalUser = userRepo.findByUid(userId);
        if(!optionalUser.isPresent())
            throw new IllegalArgumentException("No user found.\n" +
                    "This should not happen, please contact an administrator.");
        UserEntity user = optionalUser.get();
        List<ImageEntity> imageEntityList = imageRepo.findAllByUser(user);
        List<ImageInfoModel> result = ImageEntityListToModelList(imageEntityList);
        return result;
    }

    @Override
    public List<ImageInfoModel> getAllPublicImages() {
        List<ImageEntity> imageEntityList = imageRepo.findAllByPrivacy(Privacy.Public);
        List<ImageInfoModel> result = ImageEntityListToModelList(imageEntityList);
        return result;
    }

    @Override
    public List<ImageInfoModel> getAllSharedImages(String userId) {
        List<ImageEntity> imageEntityList = imageRepo.findAll();
        List<ImageEntity> filteredList = imageEntityList.stream()
                .filter(imageEntity -> {
                    String sharedUserIdsString = imageEntity.getSharedUserIds();
                    if (sharedUserIdsString != null) {
                        JSONArray sharedUserIds = new JSONArray(sharedUserIdsString);
                        for (int i = 0; i < sharedUserIds.length(); i++) {
                            if (sharedUserIds.getString(i).equals(userId)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
        List<ImageInfoModel> result = ImageEntityListToModelList(filteredList);
        return result;
    }

    @Override
    public List<String> getAllEmailsByImageInfoId(UUID imageInfoId, String ownerUid) {
        Optional<ImageEntity> optionalImage = imageRepo.findById(imageInfoId);
        if(!optionalImage.isPresent())
            throw new IllegalArgumentException("No document with the given ID exists.");
        ImageEntity imageEntity = optionalImage.get();
        List<String> emails = new ArrayList<>();
        List<String> sharedUserIdsList = new ArrayList<>();
        if(imageEntity.getSharedUserIds() != null && imageEntity.getSharedUserIds().length() > 2) {
            sharedUserIdsList = Arrays.stream(imageEntity.getSharedUserIds().substring(1, imageEntity.getSharedUserIds().length() - 1).split(","))
                    .map(element -> element.trim().replaceAll("^\"|\"$", ""))
                    .collect(Collectors.toList());
        }
        for(String uid : sharedUserIdsList) {
            Optional<UserEntity> optionalUser = userRepo.findByUid(uid);
            if(!optionalUser.isPresent())
                continue; //Skip potentially deleted users
            UserEntity user = optionalUser.get();
            emails.add(user.getEmail());
        }

        return emails;
    }

    @Override
    public void deleteById(UUID id) {
        imageRepo.deleteById(id);
    }

    public boolean existsById(UUID id) throws IllegalArgumentException {
        return imageRepo.existsById(id);
    }

    @Override
    public List<ImageInfoModel> getAll() {
        List<ImageInfoModel> result = new ArrayList<>();
        imageRepo.findAll().forEach(imageDocument -> result.add(mapper.entityToModel(imageDocument)));
        return result;
    }

    @Override
    public void deleteAll() {
        imageRepo.deleteAll();
    }

    @Override
    public Map<Privacy, List<ImageInfoModel>> getImageInfoByTextSearch(String searchText, String userId) {
        Optional<UserEntity> optionalUser = userRepo.findByUid(userId);
        if(!optionalUser.isPresent())
            throw new IllegalArgumentException("No user found.\n" +
                    "This should not happen, please contact an administrator.");
        UserEntity user = optionalUser.get();
        Predicate<ImageEntity> filterPredicate = (ImageEntity imageEntity) ->
                            imageEntity.getStatus() != Status.Disabled &&
                            imageEntity.getTitle().toLowerCase().contains(searchText.toLowerCase()) ||
                            imageEntity.getAuthor().toLowerCase().contains(searchText.toLowerCase()) ||
                            imageEntity.getDescription().toLowerCase().contains(searchText.toLowerCase()) ||
                            imageEntity.getTags().toLowerCase().contains(searchText.toLowerCase());
        Map<Privacy, List<ImageInfoModel>> imageEntityDict = new HashMap<>();
        imageEntityDict.put(Privacy.Private, imageRepo.findAllByUser(user)
                                            .stream().filter(filterPredicate).collect(Collectors.toList())
                                            .stream().map(mapper::entityToModel).collect(Collectors.toList()));
        imageEntityDict.put(Privacy.Shared, imageRepo.findAllByPrivacyAndUser(Privacy.Shared, user)
                                            .stream().filter(filterPredicate).collect(Collectors.toList())
                                            .stream().map(mapper::entityToModel).collect(Collectors.toList()));
        imageEntityDict.put(Privacy.Public, imageRepo.findAllByPrivacy(Privacy.Shared)
                                            .stream().filter(filterPredicate).collect(Collectors.toList())
                                            .stream().map(mapper::entityToModel).collect(Collectors.toList()));
        return imageEntityDict;
    }

    @Override
    public ImageInfoModel transferOwnership(ImageInfoModel imageInfoModel, String newOwnerUid) {
        Optional<ImageEntity> optionalImageEntity = imageRepo.findById(imageInfoModel.getId());
        if (!optionalImageEntity.isPresent()){
            throw new NoImageFoundException();
        }
        ImageEntity imageEntity = optionalImageEntity.get();
        Optional<UserEntity> optionalUserEntity = userRepo.findByUid(newOwnerUid);
        if(!optionalUserEntity.isPresent())
            throw new IllegalArgumentException("No user found.\n" +
                    "This should not happen, please contact an administrator.");
        UserEntity userEntity = optionalUserEntity.get();
        imageEntity.setUser(userEntity);
        imageEntity = imageRepo.save(imageEntity);
        return mapper.entityToModel(imageEntity);
    }

    private List<ImageInfoModel> ImageEntityListToModelList(List<ImageEntity> imageEntityList){
        List<ImageInfoModel> imageInfoModelList = new ArrayList<>();
        for (ImageEntity imageEntity : imageEntityList){
            ImageInfoModel imageInfoModel = mapper.entityToModel(imageEntity);
            imageInfoModelList.add(imageInfoModel);
        }
        return imageInfoModelList;
    }
}
