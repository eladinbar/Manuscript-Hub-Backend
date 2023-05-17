package com.manuscript.rest.mapping;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.rest.response.AlgorithmResponse;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmResponseMapperImpl implements IRestMapper<AlgorithmModel, AlgorithmResponse> {
    @Override
    public AlgorithmResponse modelToRest(AlgorithmModel algorithmModel) {
        return AlgorithmResponse.builder()
                .id(algorithmModel.getId())
                .uid(algorithmModel.getUid())
                .title(algorithmModel.getTitle())
                .modelType(algorithmModel.getModelType())
                .description(algorithmModel.getDescription())
                .url(algorithmModel.getUrl())
                .status(algorithmModel.getStatus())
                .createdTime(algorithmModel.getCreatedTime())
                .updatedTime(algorithmModel.getUpdatedTime())
                .build();
    }

    @Override
    public AlgorithmModel restToModel(AlgorithmResponse algorithmResponse) {
        return AlgorithmModel.builder()
                .id(algorithmResponse.getId())
                .uid(algorithmResponse.getUid())
                .title(algorithmResponse.getTitle())
                .modelType(algorithmResponse.getModelType())
                .description(algorithmResponse.getDescription())
                .url(algorithmResponse.getUrl())
                .status(algorithmResponse.getStatus())
                .createdTime(algorithmResponse.getCreatedTime())
                .updatedTime(algorithmResponse.getUpdatedTime())
                .build();
    }
}
