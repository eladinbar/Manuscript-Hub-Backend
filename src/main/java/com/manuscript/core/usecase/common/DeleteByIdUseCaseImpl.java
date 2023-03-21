package com.manuscript.core.usecase.common;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteByIdUseCaseImpl<M> implements IDeleteByIdUseCase<M> {

    private final IBaseRepositoryService<M> _serviceRepo;

    @Override
    public void deleteById(M model) throws IllegalArgumentException {
        _serviceRepo.deleteById(model);

    }
}
