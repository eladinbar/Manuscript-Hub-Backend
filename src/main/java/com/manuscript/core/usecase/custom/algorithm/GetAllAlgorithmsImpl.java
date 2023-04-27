package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.usecase.common.GetAllUseCaseImpl;

import java.util.List;
import java.util.UUID;

public class GetAllAlgorithmsImpl extends GetAllUseCaseImpl<AlgorithmModel> implements IGetAllAlgorithms {
    public GetAllAlgorithmsImpl(IBaseRepositoryService<AlgorithmModel> serviceRepo) {
        super(serviceRepo);
    }

    //TODO
    public List<AlgorithmModel> getAllByUserId(UUID userId) {
        throw new RuntimeException("Unimplemented");
    }
}
