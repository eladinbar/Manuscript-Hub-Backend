package com.manuscript.infrastructure.persistence.sql.repositories;

import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAlgorithmRepo extends CrudRepository<AlgorithmEntity, UUID> {

}
