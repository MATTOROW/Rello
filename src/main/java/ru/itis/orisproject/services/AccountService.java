package ru.itis.orisproject.services;

import ru.itis.orisproject.models.AccountEntity;
import ru.itis.orisproject.repositories.AccountRepository;

import java.util.List;

public class AccountService {
    private final AccountRepository dao = new AccountRepository();

    public AccountEntity getByUsername(String username) {
        return dao.getByUsername(username);
    }

    public int save(AccountEntity acc) {
        return dao.save(acc);
    }

    public int deleteByUsername(String username) {
        return dao.deleteByUsername(username);
    }

    public int updateByUsername(AccountEntity acc, String username) {
        return dao.updateByUsername(acc, username);
    }

    public List<AccountEntity> getByUsernameILike(String username) {
        return dao.getByUsernameILike(username);
    }
}
