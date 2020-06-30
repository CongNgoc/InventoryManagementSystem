package inventory.controler;

import inventory.model.*;
import inventory.service.CategoryService;
import inventory.service.HistoryService;
import inventory.service.ProductInfoService;
import inventory.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HistoryController {
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private CategoryService categoryService;
    static final Logger log = Logger.getLogger(HistoryController.class);
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//        if(binder.getTarget().getClass()== Users.class) {
//            binder.setValidator(userValidator);
//        }
    }

    @GetMapping({"/history/list","/history/list/", "/history", "/history/"})
    public String redirect() {
        return "redirect:/history/list/1";
    }

    @RequestMapping(value="/history/list/{page}")
    public String list(Model model, @ModelAttribute("searchForm") History history, @PathVariable("page") int page) {
        log.info("get list history");
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        if(history == null) {
            log.info("history is null");
            history = new History();
        }
        List<History> histories = historyService.getAllHistoty(history, paging);

        //set ProductInfo for each ProductInStock
        for (int i = 0; i < histories.size(); i++) {
            ProductInfo productInfo =  productInfoService.findProductInfoById(histories.get(i).getProductId());
            log.info("===PRODUCT_ID " + productInfo.getProductInfoId());
            Category category = categoryService.findByIdCategory(productInfo.getCategoryId());
            productInfo.setCategory(category);
            log.info("===CATEGORY_NAME " + category.getName());
            log.info("===CATEGORY_ID " + category.getCategoryId());
            histories.get(i).setProductInfo(productInfo);
        }

        Map<String,String> mapType = new HashMap<>();
        mapType.put(String.valueOf(Constant.TYPE_ALL), "All");
        mapType.put(String.valueOf(Constant.TYPE_GOODS_RECEIPT), "Goods Receipt");
        mapType.put(String.valueOf(Constant.TYPE_GOODS_ISSUES), "Goods Issues");
        model.addAttribute("histories", histories);
        model.addAttribute("pageInfo", paging);
        model.addAttribute("mapType", mapType);
        return "history";
    }
}
