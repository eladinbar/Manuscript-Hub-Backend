package com.manuscript.infrastructure.persistence.nosql.repositories;

import com.manuscript.infrastructure.persistence.nosql.documents.ImageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IImageRepo extends MongoRepository<ImageDocument, UUID> {

}
