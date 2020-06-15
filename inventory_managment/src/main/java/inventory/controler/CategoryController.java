package inventory.controler;

import inventory.model.Category;
import inventory.service.CategoryService;
import inventory.util.Constant;
import inventory.validate.CategoryValidator;
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
public class CategoryController {
    private static final Logger log = Logger.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryValidator categoryValidator;

    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {
        if(dataBinder.getTarget() == null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if(dataBinder.getTarget().getClass() == Category.class) {
            log.info("set Validator Category");
            dataBinder.setValidator(categoryValidator);
        }
    }

    @RequestMapping("/category/list")
    public String showCategoryList(Model model, HttpSession session, @ModelAttribute("searchForm") Category category) {
        List<Category> categories = categoryService.getAllCategory(category);
        log.info("========RUN AT HERE2");
        if(session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("categories", categories);
        return "category-list";
    }

    @GetMapping("/category/add")
    public String addCategory(Model model) {
        log.info("=======add category");

        model.addAttribute("titlePage", "Add Category");
        model.addAttribute("modelForm", new Category());
        model.addAttribute("viewOnly", false);
        return "category-action";
    }
    @GetMapping("/category/edit/{id}")
    public String editCatefory(Model model, @PathVariable("id") short id){
        log.info("Edit category with id: " +id);
        Category category = categoryService.findByIdCategory(id);
        if(category!=null) {
            model.addAttribute("titlePage", "Edit Category");
            model.addAttribute("modelForm", category);
            model.addAttribute("viewOnly", false);
            return "category-action";
        }
        return "redirect:/category/list";
    }
    @GetMapping("/category/view/{id}")
    public String viewCategory(Model model, @PathVariable("id") short id) {
        log.info("View category with id: " + id);
        Category category = categoryService.findByIdCategory(id);
        if(category!=null) {
            model.addAttribute("titlePage", "View Category");
            model.addAttribute("modelForm", category);
            model.addAttribute("viewOnly", true);
            return "category-action";
        }
        return "redirect:/category/list";
    }
    @PostMapping("/category/save")
    public String save(Model model, @ModelAttribute("modelForm") @Validated Category category, BindingResult result, HttpSession session) {
        log.info("===== save Category");
        if(result.hasErrors()) {
            if(category.getCategoryId()!=0) {
                model.addAttribute("titlePage", "Edit Category");
            }else {
                model.addAttribute("titlePage", "Add Category");
            }

            model.addAttribute("modelForm", category);
            model.addAttribute("viewOnly", false);
            return "category-action";
        }
        if(category.getCategoryId()!=0) {
            try {
                categoryService.updateCategory(category);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!");
            } catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error!");
            }
        }else {
            try {
                categoryService.saveCategory(category);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!");
            } catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!");
            }
        }
        return "redirect:/category/list";
    }
    @GetMapping("/category/delete/{id}")
    public String delete(Model model , @PathVariable("id") short id) {
        log.info("Delete category with id="+id);
        Category category = categoryService.findByIdCategory(id);
        if(category!=null) {
            categoryService.deleteCategory(category);
        }
        return "redirect:/category/list";
    }
}
