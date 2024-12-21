package ru.itis.orisproject.api;

import ru.itis.orisproject.dto.response.AccountResponse;

import java.util.Map;
import java.util.UUID;

public interface AccountProjectApi {
    boolean hasAccess(UUID projectId, String username);

    int updateRole(UUID projectId, String username, String role);

    int addNewParticipant(UUID projectId, String username, String role);

    Map<AccountResponse, String> getAllParticipants(UUID projectId);

    AccountResponse getOwner(UUID projectId);
}
