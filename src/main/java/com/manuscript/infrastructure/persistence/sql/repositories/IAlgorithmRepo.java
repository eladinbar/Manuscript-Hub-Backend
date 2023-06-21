package com.manuscript.infrastructure.persistence.sql.repositories;

import com.manuscript.core.domain.common.enums.AlgorithmStatus;
import com.manuscript.infrastructure.persistence.sql.entities.AlgorithmEntity;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAlgorithmRepo extends CrudRepository<AlgorithmEntity, UUID> {
    Optional<AlgorithmEntity> findByUrl(String url);
    List<AlgorithmEntity> findAllByUser(UserEntity user);
    List<AlgorithmEntity> findAllByStatus(AlgorithmStatus status);
    void deleteByUrl(String url);
}
