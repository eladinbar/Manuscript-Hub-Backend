package com.manuscript.core.usecase.common;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateUseCaseImpl<M> implements IUpdateUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;

    @Override
    public M update(M model) throws IllegalArgumentException {
        return _serviceRepo.update(model);
    }
}
