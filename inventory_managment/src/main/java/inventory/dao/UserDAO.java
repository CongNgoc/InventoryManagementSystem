package inventory.dao;

import inventory.model.Users;

import java.util.List;

public interface UserDAO<E> extends BaseDAO<E> {
    public List<Users> findByPropertyUser(String property, String value);
}
