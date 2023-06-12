package com.manuscript.core.usecase.custom.image;

import com.manuscript.core.domain.image.repository.IImageRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class GetAllEmailsByImageInfoIdImpl implements IGetAllEmailsByImageInfoId {
    private final IImageRepositoryService _serviceRepo;

    @Override
    public List<String> getAllEmailsByImageInfoIdImpl(UUID imageInfoId, String ownerUid) {
        return _serviceRepo.getAllEmailsByImageInfoId(imageInfoId, ownerUid);
    }
}
