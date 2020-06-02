package inventory.controler;

import inventory.model.Users;
import inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HelloControler {
    @Autowired
    private UserService userService;


	@GetMapping("")
	public String normal(Model model) {
		model.addAttribute("loginForm", new Users());
		return "login/login";
	}

	@GetMapping("/index")
	public String index(Model model,  HttpSession session) {
//        List<Users> users = userService.findAll();
//        session.setAttribute(Constant.USER_INFO, users.get(0));
		return "index";
	}
}
