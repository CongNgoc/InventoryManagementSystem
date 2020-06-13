package inventory.service;

import inventory.dao.MenuDAO;
import inventory.model.Menu;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuService {
    final static Logger log = Logger.getLogger(MenuService.class);
    @Autowired
    private MenuDAO<Menu> menuDAO;

    public List<Menu> findMenuByProperty(String property, Object value) {
        log.info("Find Menu by property start!");
        return menuDAO.findByProperty(property, value);
    }
}
