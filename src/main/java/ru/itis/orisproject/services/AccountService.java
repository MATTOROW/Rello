package ru.itis.orisproject.services;

import ru.itis.orisproject.db.dao.AccountDAO;
import ru.itis.orisproject.models.Account;

import java.util.List;

public class AccountService {
    private final AccountDAO dao = new AccountDAO();

    public Account getByUsername(String username) {
        return dao.getByUsername(username);
    }

    public int save(Account acc) {
        return dao.save(acc);
    }

    public int deleteByUsername(String username) {
        return dao.deleteByUsername(username);
    }

    public int updateByUsername(Account acc, String username) {
        return dao.updateByUsername(acc, username);
    }

    public List<Account> getByUsernameILike(String username) {
        return dao.getByUsernameILike(username);
    }
}
