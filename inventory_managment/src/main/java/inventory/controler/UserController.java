package inventory.controler;

import inventory.model.Paging;
import inventory.model.ProductInfo;
import inventory.model.Users;
import inventory.service.UserService;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Parameter;
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
}
