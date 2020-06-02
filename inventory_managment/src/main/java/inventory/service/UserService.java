package inventory.service;

import inventory.dao.UserDAO;
import inventory.model.Users;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    final static Logger log = Logger.getLogger(UserService.class);

    @Autowired
    private UserDAO<Users> userDAO;

    public List<Users> findAll() {
        log.info("Find all users start");
        return userDAO.findAll();
    }
    public List<Users> findByProperty(String property, Object value) {
        log.info("Find user by property start ");
        return userDAO.findByProperty(property, value);
    }
    public List<Users> findByPropertyUser(String property, String value) {
        log.info("Find user by property start ");
        return userDAO.findByPropertyUser(property, value);
    }
}
