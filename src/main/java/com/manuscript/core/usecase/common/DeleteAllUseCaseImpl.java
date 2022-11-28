package com.manuscript.core.usecase.common;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteAllUseCaseImpl<M> implements IDeleteAllUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;


    @Override
    public void deleteAll() throws IllegalArgumentException {
        _serviceRepo.deleteAll();

    }
}
