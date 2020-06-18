package inventory.validate;

import inventory.model.ProductInfo;
import inventory.service.ProductInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.util.List;

@Component
public class ProductInfoValidator implements Validator {
    private static final Logger logger = Logger.getLogger(ProductInfoValidator.class);
    @Autowired
    private ProductInfoService productInfoService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == ProductInfo.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.info("validate ProductInfo!");
        ProductInfo productInfo = (ProductInfo) target;
        ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "name", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
        if(productInfo.getProductInfoId() != 0) {
            ValidationUtils.rejectIfEmpty(errors, "multipartFile", "msg.required");
        }
        if(productInfo.getCode() != null) {
            List<ProductInfo> results = productInfoService.findByProperty("code", productInfo.getCode());
            if (results != null && !results.isEmpty()) {
                if (productInfo.getProductInfoId() != 0) {
                    if (results.get(0).getProductInfoId() != productInfo.getProductInfoId()) {
                        errors.rejectValue("code", "msg.code.exist");
                    }
                } else {
                    errors.rejectValue("code", "msg.code.exist");
                }

            }
        }
//        if (!productInfo.getMultipartFile().getOriginalFilename().isEmpty()) {
//            String extension = FilenameUtils.getExtension(productInfo.getMultipartFile().getOriginalFilename());
//            if (!extension.equals("jpg") && !extension.equals("png")) {
//                errors.rejectValue("multipartFile", "msg.file.extension.error");
//            }
//        }

    }
}
