package ru.itis.orisproject.mappers.dto;

import ru.itis.orisproject.dto.request.AccountRequest;
import ru.itis.orisproject.dto.response.AccountResponse;
import ru.itis.orisproject.models.AccountEntity;

public class AccountMapperImpl implements AccountMapper {
    @Override
    public AccountEntity toEntity(AccountRequest accountRequest) {
        if (accountRequest == null) {
            return null;
        }

        return new AccountEntity(
                accountRequest.username(),
                accountRequest.password(),
                accountRequest.email(),
                accountRequest.iconPath(),
                accountRequest.description()
        );
    }

    @Override
    public AccountResponse toResponse(AccountEntity accountEntity) {
        if (accountEntity == null) {
            return null;
        }

        return new AccountResponse(
                accountEntity.getUsername(),
                accountEntity.getEmail(),
                accountEntity.getIconPath(),
                accountEntity.getDescription()
        );
    }
}
