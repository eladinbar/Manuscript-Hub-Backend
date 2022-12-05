package com.manuscript.persistence.sql.common.mapping;

public interface IRepositoryEntityMapper<M, TEntity> {

    default TEntity modelToEntity(final M model) {
        throw new UnsupportedOperationException();
    }

    default M entityToModel(final TEntity tEntity) {
        throw new UnsupportedOperationException();
    }

}
