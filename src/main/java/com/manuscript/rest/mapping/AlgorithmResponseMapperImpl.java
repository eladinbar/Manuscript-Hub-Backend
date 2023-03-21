package com.manuscript.rest.mapping;

import com.manuscript.core.domain.algorithm.models.AlgorithmModel;
import com.manuscript.rest.response.AlgorithmResponse;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmResponseMapperImpl implements IRestMapper<AlgorithmModel, AlgorithmResponse> {
    @Override
    public AlgorithmResponse modelToRest(AlgorithmModel algorithmModel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AlgorithmModel restToModel(AlgorithmResponse algorithmResponse) {
        throw new UnsupportedOperationException();
    }
}
