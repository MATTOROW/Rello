package ru.itis.orisproject.services;

import ru.itis.orisproject.repositories.RmmtRepository;
import ru.itis.orisproject.models.AccountEntity;

public class RmmtService {
    private final RmmtRepository dao = new RmmtRepository();

    public AccountEntity getAccByToken(String token) {
        return dao.getAccByToken(token);
    }

    public int updateAccToken(String username, String token, String deviceId) {
        return dao.updateAccToken(username, token, deviceId);
    }

    public int save(String username, String token, String deviceId) {
        return dao.save(username, token, deviceId);
    }

    public boolean deviceRemembered(String deviceId) {
        return dao.deviceRemembered(deviceId);
    }
}
