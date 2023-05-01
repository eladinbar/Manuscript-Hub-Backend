package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.usecase.common.CreateUseCaseImpl;

public class CreateAlgorithmImpl extends CreateUseCaseImpl<AlgorithmModel> implements ICreateAlgorithm {
    public CreateAlgorithmImpl(IBaseRepositoryService<AlgorithmModel> _serviceRepo) {
        super(_serviceRepo);
    }

    @Override
    public AlgorithmModel create(AlgorithmModel algorithmModel) throws IllegalArgumentException {
        return super.create(algorithmModel);
    }
}
