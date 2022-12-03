package com.manuscript.nosql.repositories;

import com.manuscript.nosql.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepo extends CrudRepository<UserEntity, UUID> {

}
