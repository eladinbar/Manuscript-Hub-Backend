package com.manuscript.infrastructure.persistence.sql.repositories;

import com.manuscript.infrastructure.persistence.sql.entities.InvitationRequestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IInvitationRequestRepo extends CrudRepository<InvitationRequestEntity, UUID> {
    Optional<InvitationRequestEntity> findByUid(String uid);

    Optional<InvitationRequestEntity> findByEmail(String email);
}
