package inventory.dao;

import inventory.controler.ConnectDB;
import inventory.model.Users;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserDAOImpl extends BaseDAOImpl<Users> implements UserDAO<Users> {

    @Override
    public List<Users> findByPropertyUser(String property, String value){

        List<Users> users = new ArrayList<Users>();
        ConnectDB connDB = new ConnectDB();
        connDB.open();

        //Excute query
        String queryString = "SELECT * FROM USERS WHERE active_Flag=1 AND " + property + "=?";
        PreparedStatement statement = null;
        try {
            statement = connDB.getCon().prepareStatement(queryString);
            statement.setString(1, value);

            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Users user = new Users();
                user.setUserId(rs.getShort("user_Id"));
                user.setFirstName(rs.getString("first_Name"));
                user.setLastName(rs.getString("last_Name"));
                user.setBirthday(rs.getTime("birthday"));
                user.setUserName(rs.getString("user_Name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setActiveFlag(rs.getBoolean("active_Flag"));
                user.setCreateDate( rs.getTime("CREATE_DATE"));
                user.setUpdateDate(rs.getTime("update_Date"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Short getUserSEQ() {
        Query query = sessionFactory.getCurrentSession().createNativeQuery("select USERS_SEQ.nextval as num from dual").
                addScalar("num", StandardBasicTypes.SHORT);;

        return ((Short) query.uniqueResult()).shortValue();
    }
}
