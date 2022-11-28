package com.manuscript.core.usecase.common;

public interface IDeleteUseCase<M> {
    void delete(M model) throws IllegalArgumentException;
}
