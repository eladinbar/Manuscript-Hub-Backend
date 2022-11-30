package com.manuscript.core.usecase.common;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;

import java.util.Optional;
import java.util.UUID;

public class GetByIdUseCaseImpl<M> implements IGetByIdUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;

    public GetByIdUseCaseImpl(IBaseRepositoryService<M> serviceRepo) {
        this._serviceRepo = serviceRepo;
    }

    @Override
    public Optional<M> getById(UUID id) throws IllegalArgumentException {
        return _serviceRepo.getById(id);
    }
}
