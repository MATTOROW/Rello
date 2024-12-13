package ru.itis.orisproject.services;

import ru.itis.orisproject.db.dao.RmmtDAO;
import ru.itis.orisproject.models.Account;

public class RmmtService {
    private final RmmtDAO dao = new RmmtDAO();

    public Account getAccByToken(String token) {
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
