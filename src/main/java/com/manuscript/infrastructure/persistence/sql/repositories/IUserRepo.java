package com.manuscript.infrastructure.persistence.sql.repositories;

import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepo extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUid(String uid);
    Optional<UserEntity> findByEmail(String email);
}
