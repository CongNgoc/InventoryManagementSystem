package inventory.controler;

import inventory.model.Paging;
import inventory.model.ProductInStock;
import inventory.model.ProductInfo;
import inventory.service.CategoryService;
import inventory.service.ProductInStockService;
import inventory.service.ProductInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProductInStockController {
    @Autowired
    private ProductInStockService productInStockService;
    @Autowired
    ProductInfoService productInfoService;
    @Autowired
    CategoryService categoryService;
    static final Logger log = Logger.getLogger(ProductInStockController.class);

    @GetMapping({"/product-in-stock/list","/product-in-stock/list/"})
    public String redirect() {
        return "redirect:/product-in-stock/list/1";
    }

    @RequestMapping(value="/product-in-stock/list/{page}")
    public String getProductInStockList(Model model, @ModelAttribute("searchForm") ProductInStock productInStock, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<ProductInStock> productInStocks = productInStockService.getAllProductInStock(productInStock, paging);
        //set ProductInfo for each ProductInStock
        for (int i = 0; i < productInStocks.size(); i++) {
            ProductInfo productInfo =  productInfoService.findProductInfoById(productInStocks.get(i).getProductId());
            productInfo.setCategory(categoryService.findByIdCategory(productInfo.getCategoryId()));
            productInStocks.get(i).setProductInfo(productInfo);

        }

        model.addAttribute("products", productInStocks);
        model.addAttribute("pageInfo", paging);
        return "product-in-stock";
    }
}
