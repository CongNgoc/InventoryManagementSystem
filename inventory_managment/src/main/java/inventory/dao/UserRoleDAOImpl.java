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

    @Override
    public List<UserRole> findByPropertyUser(String property, int value){

        List<UserRole> userRoles = new ArrayList<UserRole>();
        ConnectDB connDB = new ConnectDB();
        connDB.open();

        //Excute query
        String queryString = "SELECT * FROM USER_ROLE WHERE active_Flag=1 AND " + property + "=?";
        PreparedStatement statement = null;
        try {
            statement = connDB.getCon().prepareStatement(queryString);
            statement.setInt(1, value);

            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                UserRole userrole = new UserRole();
                userrole.setUserRoleId(rs.getShort("user_role_Id"));
                userrole.setUserId(rs.getShort("user_Id"));
                userrole.setRoleId(rs.getShort("role_Id"));
                userrole.setActiveFlag(rs.getBoolean("active_Flag"));
                userrole.setCreateDate( rs.getTime("CREATE_DATE"));
                userrole.setUpdateDate(rs.getTime("update_Date"));
                userRoles.add(userrole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRoles;
    }
}
