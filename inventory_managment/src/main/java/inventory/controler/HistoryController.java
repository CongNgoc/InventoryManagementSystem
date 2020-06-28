package inventory.controler;

import inventory.model.History;
import inventory.model.Paging;
import inventory.model.ProductInfo;
import inventory.service.CategoryService;
import inventory.service.HistoryService;
import inventory.service.ProductInfoService;
import inventory.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping({"/history/list","/history/list/", "/history", "/history/"})
    public String redirect() {
        return "redirect:/history/list/1";
    }
    @RequestMapping(value="/history/list/{page}")
    public String list(Model model, @ModelAttribute("searchForm") History history, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<History> histories = historyService.getAllHistoty(history, paging);
        //set ProductInfo for each ProductInStock
        for (int i = 0; i < histories.size(); i++) {
            ProductInfo productInfo =  productInfoService.findProductInfoById(histories.get(i).getProductId());
            productInfo.setCategory(categoryService.findByIdCategory(productInfo.getCategoryId()));
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
