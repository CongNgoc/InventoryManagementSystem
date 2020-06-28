package inventory.validate;

import inventory.model.Invoice;
import inventory.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class InvoiceValidator implements Validator {
    @Autowired
    private InvoiceService invoiceService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Invoice.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Invoice invoice = (Invoice) target;
        ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "qty", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "price", "msg.required");
        if (invoice.getCode() != null) {
            List<Invoice> results = invoiceService.findInvoiceByProperty("code", invoice.getCode());
            if (results != null && !results.isEmpty()) {
                if (invoice.getInvoiceId() != 0) {
                    if (results.get(0).getInvoiceId() != invoice.getInvoiceId()) {
                        errors.rejectValue("code", "msg.code.exist");
                    }
                } else {
                    errors.rejectValue("code", "msg.code.exist");
                }
            }
        }

        if (invoice.getPrice() <= 0) {
            errors.rejectValue("price", "msg.wrong.format");
        }
        if (invoice.getFromDate() != null && invoice.getToDate() != null) {
            if (invoice.getFromDate().after(invoice.getToDate())) {
                errors.rejectValue("fromDate", "msg.wrong.date");
            }
        }
    }
}
