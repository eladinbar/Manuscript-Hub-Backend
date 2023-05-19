package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.usecase.common.DeleteByIdUseCaseImpl;

public class DeleteByIdAlgorithm extends DeleteByIdUseCaseImpl<AlgorithmModel> implements IDeleteByIdAlgorithm {
    public DeleteByIdAlgorithm(IBaseRepositoryService<AlgorithmModel> _serviceRepo) {
        super(_serviceRepo);
    }
}
