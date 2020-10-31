package inventory.controler;

import inventory.model.Invoice;
import inventory.model.Paging;
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
import java.util.List;

@Controller
public class StatictisController {
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

    @GetMapping(value= {"/report/invoice","/report/invoice/"})
    public String redirect() {
        log.info("redirect:/report/invoice");
        return "redirect:/report/invoice";
    }

    @RequestMapping(value="/report/invoice")
    public String showInvoiceList(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoice(null,null);
        model.addAttribute("invoices", invoices);
        return "goods-receipt-list";
    }
}
