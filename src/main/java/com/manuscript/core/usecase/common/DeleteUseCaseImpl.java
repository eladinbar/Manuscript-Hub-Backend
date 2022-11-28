package com.manuscript.core.usecase.common;

import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteUseCaseImpl<M> implements IDeleteUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;

    @Override
    public void delete(M model) throws IllegalArgumentException {
        _serviceRepo.deleteById(model);

    }
}
