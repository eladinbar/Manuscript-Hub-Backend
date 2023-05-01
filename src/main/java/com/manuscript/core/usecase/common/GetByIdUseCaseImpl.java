package com.manuscript.core.usecase.common;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class GetByIdUseCaseImpl<M> implements IGetByIdUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;

    @Override
    public Optional<M> getById(UUID id) throws IllegalArgumentException {
        return _serviceRepo.getById(id);
    }
}
