package com.manuscript.core.usecase.custom.algorithm;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;

import java.util.Optional;

public interface IGetByUrlAlgorithm {
    Optional<AlgorithmModel> getByUrl(String url) throws IllegalArgumentException;
}
