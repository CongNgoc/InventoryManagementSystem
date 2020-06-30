package inventory.controler;

import inventory.model.Menu;
import inventory.model.Paging;
import inventory.model.Role;
import inventory.service.MenuService;
import inventory.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    static final Logger log = Logger.getLogger(ProductInfoController.class);
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @GetMapping(value= {"/menu/list","/menu/list/"})
    public String redirectMenuList() {
        log.info("redirectMenuList");
        return "redirect:/menu/list/1";
    }

    @RequestMapping("/menu/list/{page}")
    public String showMenuLIst(Model model, HttpSession session, @ModelAttribute("searchForm") Menu menu, @PathVariable("page") Short page) {
        log.info("show menu list!");
        Paging paging = new Paging(5);

        List<Menu> menuList = menuService.getAllMenu(menu, paging);

        model.addAttribute("titlePage", "All Menu");
        model.addAttribute("pageInfo", paging);
        model.addAttribute("menuList", menuList);
        return "menu-list";
    }

}
