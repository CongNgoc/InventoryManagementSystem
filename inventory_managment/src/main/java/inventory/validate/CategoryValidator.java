package inventory.validate;

import inventory.model.Category;
import inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.util.List;

@Component
public class CategoryValidator implements Validator {
    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Category.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Category category = (Category) target;
        ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "name", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
        if(category.getCode() != null) {
            List<Category> results = categoryService.findCategoryByProperty("code", category.getCode());
            if(results!=null && !results.isEmpty()) {
                errors.rejectValue("code", "msg.code.exist");
            }
        }

    }
}
