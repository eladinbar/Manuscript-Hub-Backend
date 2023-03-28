package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.usecase.common.DeleteAllUseCaseImpl;

import java.util.UUID;

public class DeleteAllAlgorithmsImpl extends DeleteAllUseCaseImpl<AlgorithmModel> implements IDeleteAllAlgorithms {
    public DeleteAllAlgorithmsImpl(IBaseRepositoryService<AlgorithmModel> _serviceRepo) {
        super(_serviceRepo);
    }

    public void deleteAllByUserId(UUID userId) {
        throw new RuntimeException("Unimplemented");
    }
}
