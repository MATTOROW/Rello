package ru.itis.orisproject.services;

import ru.itis.orisproject.api.AccountApi;
import ru.itis.orisproject.dto.request.AccountRequest;
import ru.itis.orisproject.dto.response.AccountResponse;
import ru.itis.orisproject.mappers.dto.AccountMapper;
import ru.itis.orisproject.mappers.dto.AccountMapperImpl;
import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.repositories.AccountRepository;

import java.util.List;

public class AccountService implements AccountApi {
    private final AccountRepository repo = new AccountRepository();
    private final AccountMapper mapper = new AccountMapperImpl();

    @Override
    public AccountResponse getByUsername(String username) {
        return mapper.toResponse(repo.getByUsername(username));
    }

    @Override
    public int save(AccountRequest acc) {
        return repo.save(mapper.toEntity(acc));
    }

    @Override
    public int deleteByUsername(String username) {
        return repo.deleteByUsername(username);
    }

    @Override
    public int updateByUsername(AccountRequest acc, String username) {
        return repo.updateByUsername(mapper.toEntity(acc), username);
    }

    @Override
    public List<AccountResponse> getByUsernameILike(String username) {
        return repo.getByUsernameILike(username).stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<AccountResponse> getAll() {
        return repo.getAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public AccountEntity getEntityByUsername(String username) {
        return repo.getByUsername(username);
    }
}
