package andry.hais.hp_store.service;

import andry.hais.hp_store.entity.User;

public interface UserService {
    User findOne(String email);

    User save(User user);

    User update(User user);
}
