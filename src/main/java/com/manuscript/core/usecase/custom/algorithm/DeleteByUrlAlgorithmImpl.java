package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.repository.IAlgorithmRepositoryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteByUrlAlgorithmImpl implements IDeleteByUrlAlgorithm {
    private final IAlgorithmRepositoryService _serviceRepo;

    @Override
    public void deleteByUrl(String url) throws IllegalArgumentException {
        _serviceRepo.deleteByUrl(url);
    }
}
