package com.manuscript.infrastructure.persistence.sql.repositories;

import com.manuscript.infrastructure.persistence.sql.entities.AnnotationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAnnotationRepo extends CrudRepository<AnnotationEntity, UUID> {

}
