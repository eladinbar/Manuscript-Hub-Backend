package com.manuscript.core.domain.common.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBaseRepositoryService<M> {

    M save(M model) throws IllegalArgumentException;

    List<M> getAll();

    Optional<M> getById(UUID id) throws IllegalArgumentException;

    boolean isExists(UUID id) throws IllegalArgumentException;

    default List<M> saveAll(List<M> models) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    void deleteAll();

    void deleteById(M model);
}
