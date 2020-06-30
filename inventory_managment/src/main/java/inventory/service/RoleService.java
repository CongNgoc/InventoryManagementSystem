package inventory.service;

import inventory.dao.RoleDAO;
import inventory.model.Paging;
import inventory.model.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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

    public void saveRole(Role role)  throws Exception{
        log.info("Insert role "+role.toString());
        role.setActiveFlag(true);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        roleDAO.save(role);
    }
    public void updateRole(Role role) throws Exception {
        log.info("Update role "+role.toString());
        role.setUpdateDate(new Date());
        roleDAO.update(role);
    }
    public void deleteRole(Role role) throws Exception{
        role.setActiveFlag(false);
        role.setUpdateDate(new Date());
        log.info("Delete role "+role.toString());
        roleDAO.update(role);
    }

    public Role findByIdRole(int id) {
        log.info("find role by id ="+id);
        return roleDAO.findById(Role.class, id);
    }

}
