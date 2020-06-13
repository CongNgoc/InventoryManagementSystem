package inventory.controler;

import inventory.model.*;
import inventory.service.*;
import inventory.util.Constant;
import inventory.validate.LoginValidate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class LoginControler {
    private Logger log = Logger.getLogger(LoginControler.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private LoginValidate loginValidate;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    @Autowired
    private MenuService menuService;

    @InitBinder
    private void initBinder(WebDataBinder binder){
        if (binder.getTarget() == null) return;
        if(binder.getTarget().getClass() == Users.class) {
            binder.setValidator(loginValidate);
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new Users());
        return "login/login";
    }

    @PostMapping("/processLogin")
    public String processLogin(Model model, @ModelAttribute("loginForm")@Validated Users users, BindingResult result, HttpSession session) {
        if(result.hasErrors()) {
            return "login/login";
        }
        Users user = null;
        UserRole userRole = null;
        Role role = null;

        user = userService.findByProperty("userName", users.getUserName()).get(0);
        userRole = userRoleService.findByProperty("userId", user.getUserId()).get(0);
        List<Menu> menuList = new ArrayList<>();
        role = roleService.findRoleByProperty("roleId", userRole.getRoleId()).get(0);
        List<Menu> menuChildList = new ArrayList<>();
        List<Auth> authList = authService.findAuthByProperty("roleId", role.getRoleId());
        for(Auth auth : authList) {
            Menu menu = menuService.findMenuByProperty("menuId", auth.getMenuId()).get(0);
            // MAIN MENU
            if(menu.getParentId()==0 && menu.getOrderIndex()!=-1 && menu.isActiveFlag()
                && auth.isPermission() && auth.isActiveFlag()) {
                menu.setMenuIdForHTML(menu.getUrl().replace("/", "")+"Id");
                menuList.add(menu);
            }
            else if(menu.getParentId()!=0 && menu.getOrderIndex()!=-1 && menu.isActiveFlag()
                    && auth.isActiveFlag() && auth.isPermission()) {
                menu.setMenuIdForHTML(menu.getUrl().replace("/", "")+"Id");
                menuChildList.add(menu);
            }
        }

        for (Menu menu : menuList) {
            List<Menu> childList = new ArrayList<>();
            for(Menu menuChild : menuChildList) {
                if(menuChild.getParentId() == menu.getMenuId()) {
                    childList.add(menuChild);
                }
            }
            menu.setChildList(childList);
        }
        sortMenu(menuList);
        for (Menu menu : menuList) {
            sortMenu(menu.getChildList());
        }
        log.info("=================SESSION SET ATTRIBUTE");

        session.setAttribute(Constant.MENU_SESSION, menuList);
        session.setAttribute(Constant.USER_INFO, user);

        return "redirect:/index";
    }

    //order_index < =>
    private void sortMenu(List<Menu> menus) {
        Collections.sort(menus, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
    }
}
