package andry.hais.shopapi.service;

import andry.hais.shopapi.entity.User;

public interface UserService {
    User findOne(String email);

    User save(User user);

    User update(User user);
}
