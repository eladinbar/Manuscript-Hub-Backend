package com.manuscript.core.usecase.common;

public interface IDeleteByIdUseCase<M> {
    void deleteById(M model) throws IllegalArgumentException;
}
