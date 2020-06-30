package inventory.controler;

import inventory.model.Paging;
import inventory.model.Users;
import inventory.service.UserService;
import inventory.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    static final Logger log = Logger.getLogger(ProductInfoController.class);
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//        if(binder.getTarget().getClass()== Users.class) {
//            binder.setValidator(productInfoValidator);
//        }
    }

    @GetMapping(value= {"/user/list","/user/list/"})
    public String redirectUserList() {
        log.info("redirectUserList");
        return "redirect:/user/list/1";
    }

    @RequestMapping("/user/list/{page}")
    public String showUserLIst(Model model, HttpSession session, @ModelAttribute("searchForm") Users users, @PathVariable("page") Short page) {
        log.info("show users list!");
        Paging paging = new Paging(5);

        List<Users> usersList = userService.getALlUser(users, paging);
        model.addAttribute("titlePage", "All Users");
        model.addAttribute("pageInfo", paging);
        model.addAttribute("usersList", usersList);
        return "user-list";
    }

    @RequestMapping("/user/add")
    public String addUser(Model model) {
        log.info("add User!");
        model.addAttribute("titlePage", "Add Users");
        model.addAttribute("viewOnly", false);
        model.addAttribute("searchForm", new Users());
        return "user-action";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(Model model, @PathVariable("id") short id){
        log.info("Edit user with id: " +id);
        Users user = userService.findUserById(id);
        if(user!=null) {
            model.addAttribute("titlePage", "Edit User");
            model.addAttribute("modelForm", user);
            model.addAttribute("viewOnly", false);
            return "user-action";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/user/view/{id}")
    public String viewUser(Model model, @PathVariable("id") short id) {
        log.info("View user with id: " + id);
        Users user = userService.findUserById(id);
        if(user!=null) {
            model.addAttribute("titlePage", "View User");
            model.addAttribute("modelForm", user);
            model.addAttribute("viewOnly", true);
            return "user-action";
        }
        return "redirect:/user/list";
    }

    @PostMapping("/user/save")
    public String save(Model model, @ModelAttribute("modelForm") Users users, BindingResult result, HttpSession session) {
        log.info("===== save User");
        if(result.hasErrors()) {
            if(users.getUserId()!=0) {
                model.addAttribute("titlePage", "Edit User");
            }else {
                model.addAttribute("titlePage", "Add User");
            }

            model.addAttribute("modelForm", users);
            model.addAttribute("viewOnly", false);
            return "user-action";
        }
        if(users.getUserId()!=0 && users!= null) {
            try {
                log.info("===UPDATE USERS");
                userService.updateUser(users);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!");
            } catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error!");
            }
        }else {
            try {
                log.info("===CREATE USERS");
                userService.saveUser(users);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!");
            } catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!");
            }
        }
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(Model model , @PathVariable("id") short id) {
        log.info("Delete user with id="+id);
        Users user = userService.findUserById(id);
        if(user!=null) {
            userService.deleteUser(user);
        }
        return "redirect:/user/list";
    }
}
