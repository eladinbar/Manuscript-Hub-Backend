package com.manuscript.infrastructure.persistence.nosql.repositories;

import com.manuscript.core.domain.image.models.ImageModel;
import com.manuscript.infrastructure.persistence.nosql.documents.ImageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IImageRepo extends MongoRepository<ImageDocument, UUID> {
    List<ImageDocument> getAllPublicImages();

}
