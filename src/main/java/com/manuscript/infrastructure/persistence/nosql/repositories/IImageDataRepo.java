package com.manuscript.infrastructure.persistence.nosql.repositories;

import com.manuscript.infrastructure.persistence.nosql.documents.ImageDataDocument;
import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IImageDataRepo extends MongoRepository<ImageDataDocument, UUID> {
    List<ImageDataDocument> findAllByImageId(UUID imageId);
}
