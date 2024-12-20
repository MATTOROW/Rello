package ru.itis.orisproject.mappers.dto;

import ru.itis.orisproject.dto.request.AccountRequest;
import ru.itis.orisproject.dto.response.AccountResponse;
import ru.itis.orisproject.models.AccountEntity;

public interface AccountMapper {
    AccountEntity toEntity(AccountRequest accountRequest);

    AccountResponse toResponse(AccountEntity accountEntity);
}
