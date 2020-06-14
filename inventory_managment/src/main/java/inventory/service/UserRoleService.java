package inventory.service;

import java.util.List;
import inventory.dao.UserRoleDAO;
import inventory.model.UserRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    final static Logger log = Logger.getLogger(UserRoleService.class);

    @Autowired
    private UserRoleDAO<UserRole> userRoleDAO;

    //Sai o day
    public List<UserRole> findAll(){
        log.info("Find all UserRole start!");
        return  userRoleDAO.findAll();
    }

    public List<UserRole> findUserRoleByProperty(String property , Object value) {
        log.info("Find UserRole by property start!");
        return  userRoleDAO.findByProperty(property, value);
    }

}
