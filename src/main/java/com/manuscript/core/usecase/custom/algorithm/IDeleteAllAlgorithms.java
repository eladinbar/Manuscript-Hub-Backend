package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.usecase.common.IDeleteAllUseCase;

import java.util.UUID;

public interface IDeleteAllAlgorithms extends IDeleteAllUseCase<AlgorithmModel> {
    void deleteAllByUserId(UUID userId);
}
