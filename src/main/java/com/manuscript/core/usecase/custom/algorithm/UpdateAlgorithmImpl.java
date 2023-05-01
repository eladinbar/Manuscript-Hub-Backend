package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.usecase.common.UpdateUseCaseImpl;

public class UpdateAlgorithmImpl extends UpdateUseCaseImpl<AlgorithmModel> implements IUpdateAlgorithm {
    public UpdateAlgorithmImpl(IBaseRepositoryService<AlgorithmModel> serviceRepo) {
        super(serviceRepo);
    }

    @Override
    public AlgorithmModel update(AlgorithmModel model) throws IllegalArgumentException {
        return super.update(model);
    }
}
