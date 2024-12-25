package ru.itis.orisproject.services;

import ru.itis.orisproject.api.AccountProjectApi;
import ru.itis.orisproject.dto.response.AccountResponse;
import ru.itis.orisproject.mappers.dto.AccountMapperImpl;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.repositories.AccountProjectRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountProjectService implements AccountProjectApi {
    private final AccountProjectRepository repo = new AccountProjectRepository();
    private final AccountMapperImpl mapper = new AccountMapperImpl();

    @Override
    public boolean hasAccess(UUID projectId, String username) {
        return repo.hasAccess(projectId, username);
    }

    @Override
    public int updateRole(UUID projectId, String username, String role) {
        return repo.updateRole(projectId, username, role);
    }

    @Override
    public int addNewParticipant(UUID projectId, String username, String role) {
        return repo.addNewParticipant(projectId, username, role);
    }

    @Override
    public Map<AccountResponse, String> getAllParticipants(UUID projectId) {
        Map<AccountEntity, String> oldParticipants = repo.getAllParticipants(projectId);
        Map<AccountResponse, String> newParticipants = new HashMap<>();
        for (Map.Entry<AccountEntity, String> entry : oldParticipants.entrySet()) {
            AccountResponse newKey = mapper.toResponse(entry.getKey());  // Пример преобразования
            newParticipants.put(newKey, entry.getValue());
        }
        return newParticipants;
    }

    @Override
    public AccountResponse getOwner(UUID projectId) {
        return mapper.toResponse(repo.getOwner(projectId));
    }
}