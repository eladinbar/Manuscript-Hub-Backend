package com.manuscript.infrastructure.persistence.sql.impl;

import com.manuscript.core.domain.invitation_request.models.InvitationRequestModel;
import com.manuscript.core.domain.invitation_request.repository.IInvitationRequestRepositoryService;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.InvitationRequestEntity;
import com.manuscript.infrastructure.persistence.sql.repositories.IInvitationRequestRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InvitationRequestServiceSqlImpl implements IInvitationRequestRepositoryService {
    private final IInvitationRequestRepo repo;
    private final IRepositoryEntityMapper<InvitationRequestModel, InvitationRequestEntity> mapper;

    @Override
    public InvitationRequestModel save(InvitationRequestModel model) throws IllegalArgumentException {
        final InvitationRequestEntity toSave = mapper.modelToEntity(model);
        final InvitationRequestEntity result = repo.save(toSave);
        return mapper.entityToModel(result);
    }

    @Override
    public boolean existsById(UUID id) throws IllegalArgumentException {
        //todo : need to implement
        return false;
    }

    @Override
    public List<InvitationRequestModel> getAll() {
        List<InvitationRequestModel> result = new ArrayList<>();
        repo.findAll().forEach(invitationRequest -> result.add(mapper.entityToModel(invitationRequest)));
        return result;
    }

    @Override
    public void deleteById(UUID id) {
        //todo : need to implement
    }

    @Override
    public void deleteAll() {
        //todo : need to implement

    }

    @Override
    public Optional<InvitationRequestModel> getById(UUID id) throws IllegalArgumentException {
        return repo.findById(id).map(mapper::entityToModel);
    }

    @Override
    public Optional<InvitationRequestModel> getByUid(String uid) {
        Optional<InvitationRequestEntity> optionalInvitationRequest = repo.findByUid(uid);
        if (optionalInvitationRequest.isPresent()) {
            InvitationRequestModel requestModel = mapper.entityToModel(optionalInvitationRequest.get());
            return Optional.of(requestModel);
        }
        return Optional.empty();
    }

    @Override
    public Optional<InvitationRequestModel> getByEmail(String email) {
        Optional<InvitationRequestEntity> byEmail = repo.findByEmail(email);
        return byEmail.map(mapper::entityToModel);
    }

}

