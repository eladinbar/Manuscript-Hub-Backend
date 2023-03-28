package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.usecase.common.GetByIdUseCaseImpl;

import java.util.Optional;
import java.util.UUID;

public class GetByIdAlgorithmImpl extends GetByIdUseCaseImpl<AlgorithmModel> implements IGetByIdAlgorithm {
    public GetByIdAlgorithmImpl(IBaseRepositoryService<AlgorithmModel> serviceRepo) {
        super(serviceRepo);
    }

    @Override
    public Optional<AlgorithmModel> getById(UUID algorithmId) {
        return super.getById(algorithmId);
    }
}
