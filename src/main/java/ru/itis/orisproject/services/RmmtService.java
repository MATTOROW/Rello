package ru.itis.orisproject.services;

import ru.itis.orisproject.repositories.RmmtRepository;
import ru.itis.orisproject.models.AccountEntity;

public class RmmtService {
    private final RmmtRepository repo = new RmmtRepository();

    public AccountEntity getAccByToken(String token) {
        return repo.getAccByToken(token);
    }

    public int updateAccToken(String username, String token, String deviceId) {
        return repo.updateAccToken(username, token, deviceId);
    }

    public int save(String username, String token, String deviceId) {
        return repo.save(username, token, deviceId);
    }

    public boolean deviceRemembered(String username, String deviceId) {
        return repo.deviceRemembered(username, deviceId);
    }
}
