package com.manuscript.core.usecase.common;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeleteByIdUseCaseImpl<M> implements IDeleteByIdUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;

    @Override
    public void deleteById(UUID id) throws IllegalArgumentException {
        _serviceRepo.deleteById(id);

    }
}
