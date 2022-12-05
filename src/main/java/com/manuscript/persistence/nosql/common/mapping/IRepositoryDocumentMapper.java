package com.manuscript.persistence.nosql.common.mapping;

public interface IRepositoryDocumentMapper<M, TDocument> {

    default TDocument modelToDocument(final M model) {
        throw new UnsupportedOperationException();
    }

    default M documentToModel(final TDocument tDocument) {
        throw new UnsupportedOperationException();
    }

}
