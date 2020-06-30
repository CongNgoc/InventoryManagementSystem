package inventory.controler;

import inventory.model.Paging;
import inventory.model.Role;
import inventory.model.Users;
import inventory.service.RoleService;
import inventory.service.UserService;
import inventory.util.Constant;
import inventory.validate.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    static final Logger log = Logger.getLogger(RoleController.class);
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @GetMapping(value= {"/role/list","/role/list/"})
    public String redirectRoleList() {
        log.info("redirectRoleList");
        return "redirect:/role/list/1";
    }

    @RequestMapping("/role/list/{page}")
    public String showRoleList(Model model, HttpSession session, @ModelAttribute("searchForm") Role role, @PathVariable("page") Short page) {
        log.info("show role list!");
        Paging paging = new Paging(10);
        paging.setIndexPage(page);

        List<Role> roleList = roleService.getAllRole(role, paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("titlePage", "All Role");
        model.addAttribute("pageInfo", paging);
        model.addAttribute("roleList", roleList);
        return "role-list";
    }
}
