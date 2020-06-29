package inventory.service;

import inventory.dao.InvoiceDAO;
import inventory.dao.InvoiceDetailDAO;
import inventory.model.Invoice;
import inventory.model.InvoiceDetail;
import inventory.model.Paging;
import inventory.model.Users;
import inventory.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {
    private Logger logger = Logger.getLogger(InvoiceService.class);
    @Autowired
    private InvoiceDAO<Invoice> invoiceDAO;
    @Autowired
    private InvoiceDetailDAO<InvoiceDetail> invoiceDetailDAO;
    @Autowired
    private HistoryService historyService;
    private Date sys_date = new Date();

    public List<Invoice> findInvoiceByProperty(String property, Object value) {
        return invoiceDAO.findByProperty(property, value);
    }

    public void saveInvoice(Invoice invoice, Map<Short, InvoiceDetail> mapQuantityForProduct){
        Short INVOICE_SEQ = invoiceDAO.getInvoiceSEQ();
        invoice.setInvoiceId(INVOICE_SEQ);
        String code_random = getCodeRandom(10);
        while (!invoiceDAO.findByProperty("code", code_random).isEmpty()){
            code_random = getCodeRandom(10);
        };

        invoice.setCode(code_random);
        invoice.setType(true);
        invoice.setPrice(getTotalPayment(InvoiceDetail.getMapQuantityForProduct()));
        invoice.setCreateDate(sys_date);
        invoice.setUpdateDate(sys_date);
        invoice.setActiveFlag(true);
        invoiceDAO.save(invoice);

        for(Map.Entry<Short, InvoiceDetail> entry : mapQuantityForProduct.entrySet()) {
            Short invoiceDetailSEQ = invoiceDetailDAO.getInvoiceDetailSEQ();
            InvoiceDetail invoiceDetail = entry.getValue();
            invoiceDetail.setInDeId(invoiceDetailSEQ);
            invoiceDetail.setInvoiceId(INVOICE_SEQ);
            invoiceDetail.setCreateDate(sys_date);
            invoiceDetail.setUpdateDate(sys_date);
            invoiceDetail.setActiveFlag(true);

            invoiceDetailDAO.save(invoiceDetail);
            historyService.save(invoiceDetail, Constant.ACTION_ADD, true);
        }
    }

    public List<Invoice> getAllInvoice(Invoice invoice, Paging paging) {
        StringBuilder queryStr = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        if(invoice != null) {
            if(invoice.getCode() != null && !invoice.getCode().isEmpty()) {
                logger.info("====find getAllInvoice with code");
                queryStr.append(" and model.code =:code ");
                mapParams.put("code", invoice.getCode());
                //add find here
            }
            if(invoice.getFromDate()!=null) {
                queryStr.append(" and model.updateDate >= :fromDate");
                mapParams.put("fromDate", invoice.getFromDate());
            }
            if(invoice.getToDate()!=null) {
                queryStr.append(" and model.updateDate <= :toDate");
                mapParams.put("toDate", invoice.getToDate());
            }
        }
        return invoiceDAO.findAll(queryStr.toString(), mapParams, paging);
    }

    public Short getInvoiceSEQ() {
        return invoiceDAO.getInvoiceSEQ();
    }

    public InvoiceDetail findInvoiceDetailById(short id) {
        return invoiceDetailDAO.findById(InvoiceDetail.class, id);
    }

    public List<InvoiceDetail> findInvoiceDetailByProperty(String property, Object value) {
        return invoiceDetailDAO.findByProperty(property, value);
    }

    public void saveInvoiceDetail(InvoiceDetail invoiceDetail) {
        invoiceDetailDAO.save(invoiceDetail);
    }

    private static String getCodeRandom(int n)
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    private Long getTotalPayment(Map<Short, InvoiceDetail> mapQuantityForProduct) {
        Long total_payment = (long) 0;
        for(Map.Entry<Short, InvoiceDetail> entry : mapQuantityForProduct.entrySet()) {
            total_payment = total_payment + entry.getValue().getAmount();
        }
        return  total_payment;
    }
}
