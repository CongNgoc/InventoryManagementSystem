package inventory.service;

import inventory.dao.RoleDAO;
import inventory.model.Paging;
import inventory.model.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    final static Logger log = Logger.getLogger(RoleService.class);

    @Autowired
    private RoleDAO<Role> roleDAO;
    public List<Role> findRoleByProperty(String property, Object value) {
        log.info("Find Role by property start!");
        return roleDAO.findByProperty(property, value);
    }

    public List<Role> getAllRole(Role role, Paging paging) {
        log.info("Get all user!");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder queryStr = new StringBuilder();
        if(role != null) {
            if(role.getRoleName() != null && !StringUtils.isEmpty(role.getRoleName())) {
                queryStr.append(" and model.roleName like :roleName");
                mapParams.put("roleName","%"+role.getRoleName()+"%");
            }
        }
        return roleDAO.findAll(queryStr.toString(), mapParams, paging);
    }

}
