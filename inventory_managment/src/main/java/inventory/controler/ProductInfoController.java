package inventory.controler;

import freemarker.ext.beans.HashAdapter;
import inventory.model.Category;
import inventory.model.Paging;
import inventory.model.ProductInfo;
import inventory.service.CategoryService;
import inventory.service.ProductInfoService;
import inventory.util.Constant;
import inventory.validate.ProductInfoValidator;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductInfoController {
    @Autowired
    private ProductInfoService productService;
    @Autowired
    private ProductInfoValidator productInfoValidator;
    @Autowired
    private CategoryService categoryService;
    static final Logger log = Logger.getLogger(ProductInfoController.class);
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if(binder.getTarget().getClass()== ProductInfo.class) {
            binder.setValidator(productInfoValidator);
        }
    }
    @RequestMapping(value= {"/product-info/list","/product-info/list/"})
    public String redirect() {
        log.info("====redirect to /product-info/list/{page}");
        return "redirect:/product-info/list/1";
    }

    @RequestMapping(value="/product-info/list/{page}")
    public String showProductInfoList(Model model, HttpSession session , @ModelAttribute("searchForm") ProductInfo productInfo, @PathVariable("page") int page) {
        log.info("===========SHOW PRODUCT INFO LIST");
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<ProductInfo> products = productService.getAllProductInfo(productInfo,paging);
        //set category for each product_info
        for(int i = 0; i <products.size(); i++){
            Category category = categoryService.findByIdCategory(products.get(i).getCategoryId());
            products.get(i).setCategory(category);
        }

        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", products);
        return "productInfo-list";

    }
    @GetMapping("/product-info/add")
    public String add(Model model) {
        log.info("=====GetMapping /product-info/add");
        model.addAttribute("titlePage", "Add ProductInfo");
        model.addAttribute("modelForm", new ProductInfo());
        List<Category> categories = categoryService.getAllCategory(null, null);
        Map<String, String> mapCategory = new HashMap<>();
        for(Category category : categories) {
            mapCategory.put(String.valueOf(category.getCategoryId()), category.getName());
        }
        model.addAttribute("mapCategory", mapCategory);
//        model.addAttribute("mapCategory", mapCategory);
        model.addAttribute("viewOnly", false);
        return "productInfo-action";
    }
    @GetMapping("/product-info/edit/{id}")
    public String edit(Model model , @PathVariable("id") short id) {
        log.info("Edit productInfo with id="+id);
        ProductInfo productInfo = productService.findProductInfoById(id);
        if(productInfo!=null) {
            List<Category> categories = categoryService.getAllCategory(null, null);
            Map<String, String> mapCategory = new HashMap<>();
            for(Category category : categories) {
                mapCategory.put(String.valueOf(category.getCategoryId()), category.getName());
            }
            productInfo.setCategoryId(categories.get(0).getCategoryId());
            model.addAttribute("mapCategory", mapCategory);
            model.addAttribute("titlePage", "Edit ProductInfo");
            model.addAttribute("modelForm", productInfo);
            model.addAttribute("viewOnly", false);
            return "productInfo-action";
        }
        return "redirect:/product-info/list";
    }
    @GetMapping("/product-info/view/{id}")
    public String view(Model model , @PathVariable("id") short id) {
        log.info("View productInfo with id="+id);
        ProductInfo productInfo = productService.findProductInfoById(id);
        if(productInfo!=null) {
            Category category = categoryService.findByIdCategory(productInfo.getCategoryId());
            productInfo.setCategory(category);
            model.addAttribute("titlePage", "View ProductInfo");
            model.addAttribute("modelForm", productInfo);
            model.addAttribute("viewOnly", true);
            return "productInfo-action";
        }
        return "redirect:/product-info/list";
    }
    @PostMapping("/product-info/save")
    public String save(Model model, @ModelAttribute("modelForm") ProductInfo productInfo, BindingResult result, HttpSession session) {
        if(result.hasErrors()) {
            log.info("====result hasErrors");
            if(productInfo.getProductInfoId()!=0) {
                model.addAttribute("titlePage", "Edit ProductInfo");
            }else {
                log.info("========result hasErrors at add productinfo");
                model.addAttribute("titlePage", "Add ProductInfo");
            }
            List<Category> categories = categoryService.getAllCategory(null, null);
            Map<String, String> mapCategory = new HashMap<>();
            for(Category category : categories) {
                mapCategory.put(String.valueOf(category.getCategoryId()), category.getName());
            }
            model.addAttribute("mapCategory", mapCategory);
            model.addAttribute("modelForm", productInfo);
            model.addAttribute("viewOnly", false);
            return "productInfo-action";

        }
        log.info("====save here");
        //Save from here
        Category category = new Category();
        category.setCategoryId(productInfo.getCategoryId());
        productInfo.setCategory(category);
        if(productInfo.getProductInfoId()!=0) {
            //Update ProductInfo
            try {
                log.info("====Update ProductInfo");
                productService.updateProductInfo(productInfo);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }

        }else {
            //Add ProductInfo
            try {
                log.info("====Insert success!!!");
                productService.saveProductInfo(productInfo);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/product-info/list";

    }
    @GetMapping("/product-info/delete/{id}")
    public String delete(Model model , @PathVariable("id") short id,HttpSession session) {
        log.info("Delete productInfo with id="+id);
        ProductInfo productInfo = productService.findProductInfoById(id);
        if(productInfo!=null) {
            try {
                productService.deleteProductInfo(productInfo);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/product-info/list";
    }

    public String getCateforyNameByCategoryId() {
        return "";
    }
}
