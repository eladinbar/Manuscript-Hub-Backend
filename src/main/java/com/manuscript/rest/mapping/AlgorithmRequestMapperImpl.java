package com.manuscript.rest.mapping;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.rest.request.AlgorithmRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AlgorithmRequestMapperImpl implements IRestMapper<AlgorithmModel, AlgorithmRequest> {
    @Override
    public AlgorithmRequest modelToRest(AlgorithmModel algorithmModel) {
        return AlgorithmRequest.builder()
                .id(algorithmModel.getId())
                .uid(algorithmModel.getUid())
                .title(algorithmModel.getTitle())
                .modelType(algorithmModel.getModelType())
                .description(algorithmModel.getDescription())
                .url(algorithmModel.getUrl())
                .status(algorithmModel.getStatus())
                .build();
    }

    @Override
    public AlgorithmModel restToModel(AlgorithmRequest algorithmRequest) {
        return AlgorithmModel.builder()
                .id(algorithmRequest.getId())
                .uid(algorithmRequest.getUid())
                .title(algorithmRequest.getTitle())
                .modelType(algorithmRequest.getModelType())
                .description(algorithmRequest.getDescription())
                .url(algorithmRequest.getUrl())
                .status(algorithmRequest.getStatus())
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
    }
}
