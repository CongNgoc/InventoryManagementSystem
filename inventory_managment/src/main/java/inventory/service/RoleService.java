package inventory.service;

import inventory.dao.RoleDAO;
import inventory.model.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    final static Logger log = Logger.getLogger(RoleService.class);

    @Autowired
    private RoleDAO<Role> roleDAO;
    public List<Role> findRoleByProperty(String property, Object value) {
        log.info("Find Role by property start!");
        return roleDAO.findByProperty(property, value);
    }
}
