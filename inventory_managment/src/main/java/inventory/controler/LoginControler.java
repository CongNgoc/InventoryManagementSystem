package inventory.controler;

import inventory.model.UserRole;
import inventory.model.Users;
import inventory.service.UserService;
import inventory.service.UserRoleService;
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

@Controller
public class LoginControler {
    private Logger log = Logger.getLogger(LoginControler.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private LoginValidate loginValidate;

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

//    @PostMapping("/processLogin1")
//    public

    @PostMapping("/processLogin")
    public String processLogin(Model model, @ModelAttribute("loginForm")@Validated Users users, BindingResult result, HttpSession session) {
        if(result.hasErrors()) {
            return "login/login";
        }
        Users user = null;
        UserRole userRole = null;

        user = userService.findByProperty("userName", users.getUserName()).get(0);
//        UserRole userRole = userRoleService.findByProperty("userId", user.getUserId()).get(0);
        log.info("=================================RUN USERROLE GET(0)");
        userRole = userRoleService.findAll().get(0);
//        UserRole userRole = userRoleService.findByUserProperty("user_Id", user.getUserId()).get(0);

        log.info("====================="+userRole.getUserRoleId());

        session.setAttribute(Constant.USER_INFO, user);
        System.out.println("====================="+session);
        System.out.println("====================="+result);
        return "redirect:/index";
    }
}
