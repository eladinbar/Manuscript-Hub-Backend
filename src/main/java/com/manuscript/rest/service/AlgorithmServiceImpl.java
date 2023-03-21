package com.manuscript.rest.service;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.core.usecase.custom.algorithm.ICreateAlgorithm;
import com.manuscript.core.usecase.custom.algorithm.IGetByIdAlgorithm;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.AlgorithmRequest;
import com.manuscript.rest.response.AlgorithmResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AlgorithmServiceImpl implements IAlgorithmService {
    private final IRestMapper<AlgorithmModel, AlgorithmRequest> imageRequestMapper;
    private final IRestMapper<AlgorithmModel, AlgorithmResponse> imageResponseMapper;
    private final ICreateAlgorithm createAlgorithmUseCase;
    private final IGetByIdAlgorithm getByIdAlgorithmUseCase;
}
