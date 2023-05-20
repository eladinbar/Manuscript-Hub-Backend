package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.domain.algorithm.repository.IAlgorithmRepositoryService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllByUidAlgorithmsImpl implements IGetAllByUidAlgorithms {
    private final IAlgorithmRepositoryService _serviceRepo;

    @Override
    public List<AlgorithmModel> getAllByUid(String uid) throws IllegalArgumentException {
        return _serviceRepo.getAllByUid(uid);
    }
}
