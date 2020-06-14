package inventory.dao;

import inventory.controler.ConnectDB;
import inventory.model.UserRole;
import inventory.model.Users;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserRoleDAOImpl  extends BaseDAOImpl<UserRole> implements UserRoleDAO<UserRole>{
}
