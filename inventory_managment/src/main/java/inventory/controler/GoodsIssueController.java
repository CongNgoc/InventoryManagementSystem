package inventory.controler;

import inventory.model.*;
import inventory.service.InvoiceService;
import inventory.service.ProductInfoService;
import inventory.util.Constant;
import inventory.validate.InvoiceValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsIssueController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceValidator invoiceValidator;
    @Autowired
    private ProductInfoService productInfoService;

    static final Logger log = Logger.getLogger(GoodReceiptController.class);

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if(binder.getTarget().getClass()== Invoice.class) {
            binder.setValidator(invoiceValidator);
        }
    }

    @GetMapping(value= {"/goods-issue/list","/goods-issue/list/"})
    public String redirect() {
        log.info("redirect:/goods-issue/list/list/1");
        return "redirect:/goods-issue/list/1";
    }

    @RequestMapping(value="/goods-issue/list/{page}")
    public String showInvoiceList(Model model, HttpSession session , @ModelAttribute("searchForm") Invoice invoice, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        if(invoice==null) {
            invoice = new Invoice();
        }

        invoice.setType(Constant.TYPE_GOODS_ISSUES);
        List<Invoice> invoices = invoiceService.getAllInvoice(invoice,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("invoices", invoices);
        return "goods-issue-list";
    }

    @RequestMapping("/goods-issue/add")
    public String addGoodReceipt(Model model, HttpSession session , @ModelAttribute("searchForm") InvoiceDetail invoiceDetail) {
        log.info(" addGoodReceipt /goods-receipt/add");
        if (invoiceDetail != null && invoiceDetail.getProductId() != 0) {
            ProductInfo productInfo = productInfoService.findProductInfoById(invoiceDetail.getProductId());
            invoiceDetail.setProductInfo(productInfo);

            Map<Short, InvoiceDetail> mapQuantityForProduct = invoiceDetail.getMapQuantityForProduct();
            if(mapQuantityForProduct.containsKey(invoiceDetail.getProductId())) {
                int sum_quantity = mapQuantityForProduct.get(invoiceDetail.getProductId()).getQuanity() + invoiceDetail.getQuanity();
                invoiceDetail.setQuanity((short) sum_quantity);
            }
            mapQuantityForProduct.put(invoiceDetail.getProductId(), invoiceDetail);
            invoiceDetail.setMapQuantityForProduct(mapQuantityForProduct);

            model.addAttribute("mapQuantity", invoiceDetail.getMapQuantityForProduct());
        }

        model.addAttribute("titlePage", "Add Goods Issue");
        model.addAttribute("searchForm", new InvoiceDetail());
        model.addAttribute("mapProduct",initMapProduct());
        return "goods-issue-add";
    }

    @RequestMapping("/goods-issue/save")
    public String saveInvoice(Model model, HttpSession session){
        log.info(" saveInvoice /goods-receipt/save");
        log.info(" mapQuanity size" + InvoiceDetail.getMapQuantityForProduct().size());

        Invoice invoice = new Invoice();
        Users users = (Users) session.getAttribute(Constant.USER_INFO);
        invoice.setUserId(users.getUserId());

        invoiceService.saveInvoice(invoice, Constant.TYPE_GOODS_ISSUES, InvoiceDetail.getMapQuantityForProduct());
        InvoiceDetail.setMapQuantityForProduct(new HashMap<Short, InvoiceDetail>());
        return "redirect:/goods-issue/list/1";
    }

    private Map<String, String> initMapProduct(){
        List<ProductInfo> productInfos =productInfoService.getAllProduct();
        Map<String, String> mapProduct = new HashMap<>();
        for(ProductInfo productIn: productInfos) {
            mapProduct.put(String.valueOf(productIn.getProductInfoId()), productIn.getName());
        }
        return mapProduct;
    }
}
