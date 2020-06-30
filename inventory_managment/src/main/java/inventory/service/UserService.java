package inventory.service;

import inventory.dao.UserDAO;
import inventory.model.Paging;
import inventory.model.Users;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    final static Logger log = Logger.getLogger(UserService.class);

    @Autowired
    private UserDAO<Users> userDAO;

    public List<Users> findByProperty(String property, Object value) {
        log.info("Find user by property start ");
        return userDAO.findByProperty(property, value);
    }
    public List<Users> findByPropertyUser(String property, String value) {
        log.info("Find user by property start ");
        return userDAO.findByPropertyUser(property, value);
    }

    public List<Users> getALlUser(Users users, Paging paging) {
        log.info("Get all user!");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder queryStr = new StringBuilder();
        if(users != null) {
            if(users.getFirstName() != null && !StringUtils.isEmpty(users.getFirstName())) {
                queryStr.append(" and model.firstName like :firstName");
                mapParams.put("firstName","%"+users.getFirstName()+"%");
                queryStr.append(" and model.lastName like :lastName");
                mapParams.put("lastName","%"+users.getFirstName()+"%");
                queryStr.append(" and model.email like :email");
                mapParams.put("email","%"+users.getFirstName()+"%");
            }
        }
        return userDAO.findAll(queryStr.toString(), mapParams, paging);
    }
}
