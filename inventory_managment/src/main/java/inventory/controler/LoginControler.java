package inventory.controler;

import inventory.model.Users;
import inventory.service.UserService;
import inventory.util.Constant;
import inventory.validate.LoginValidate;
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
    @Autowired
    private UserService userService;
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

//        user = userService.findByPropertyUser("user_Name", users.getUserName()).get(0);
        user = userService.findByProperty("userName", users.getUserName()).get(0);
        session.setAttribute(Constant.USER_INFO, user);
        System.out.println("====================="+session);
        System.out.println("====================="+result);
        return "redirect:/index";
    }
}
