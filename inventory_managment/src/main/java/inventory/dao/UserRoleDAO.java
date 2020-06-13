package inventory.dao;

import inventory.model.UserRole;
import inventory.model.Users;

import java.util.List;

public interface UserRoleDAO<E> extends BaseDAO<E>{
    public List<UserRole> findByPropertyUser(String property, int value);
}
