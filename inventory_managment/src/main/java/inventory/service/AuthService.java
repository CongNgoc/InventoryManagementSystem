package inventory.service;

import inventory.dao.AuthDAO;
import inventory.model.Auth;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthService {
    final Logger log = Logger.getLogger(AuthService.class);
    @Autowired
    private AuthDAO<Auth> authDAO;

    public List<Auth> findAuthByProperty(String property, Object value) {
        log.info("Find Auth by property start!");
        return authDAO.findByProperty(property, value);
    }
}
