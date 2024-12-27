package ru.itis.orisproject.api;

import ru.itis.orisproject.dto.request.AccountRequest;
import ru.itis.orisproject.dto.response.AccountResponse;
import ru.itis.orisproject.models.AccountEntity;

import java.util.List;

public interface AccountApi {

    AccountResponse getByUsername(String username);

    int save(AccountRequest acc);

    int deleteByUsername(String username);

    int updateByUsername(AccountRequest acc, String username);

    List<AccountResponse> getByUsernameILike(String username);

    List<AccountResponse> getAll();

    AccountEntity getEntityByUsername(String username);
}
