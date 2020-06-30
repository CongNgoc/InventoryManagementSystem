package inventory.service;

import inventory.dao.MenuDAO;
import inventory.model.Menu;
import inventory.model.Paging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {
    final static Logger log = Logger.getLogger(MenuService.class);
    @Autowired
    private MenuDAO<Menu> menuDAO;

    public List<Menu> findMenuByProperty(String property, Object value) {
        log.info("Find Menu by property start!");
        return menuDAO.findByProperty(property, value);
    }

    public List<Menu> getAllMenu(Menu menu, Paging paging){
        log.info("get all Menu!");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder queryStr = new StringBuilder();
        if(menu != null) {
            if(menu.getMenuName() != null && !StringUtils.isEmpty(menu.getMenuName())) {
                queryStr.append(" and model.menuName like :menuName");
                mapParams.put("menuName","%"+menu.getMenuName()+"%");
                queryStr.append(" and model.url like :url");
                mapParams.put("url","%"+menu.getMenuName()+"%");
            }
        }
        return menuDAO.findAll(queryStr.toString(), mapParams, paging);
    }

}
