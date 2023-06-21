package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.algorithm.repository.IAlgorithmRepositoryService;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class GetByUrlAlgorithmImpl implements IGetByUrlAlgorithm {
    private final IAlgorithmRepositoryService _serviceRepo;

    @Override
    public Optional<AlgorithmModel> getByUrl(String url) throws IllegalArgumentException {
        return _serviceRepo.getByUrl(url);
    }
}
