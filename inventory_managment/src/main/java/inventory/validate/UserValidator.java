package inventory.validate;

import inventory.model.Users;
import inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Users.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Users user = (Users) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "password", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "firstName", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "birthday", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "email", "msg.required");
        if(!StringUtils.isEmpty(user.getEmail())) {
            List<Users> users = userService.findByProperty("email", user.getEmail());
            if(users!=null && !users.isEmpty()) {
                if(users.get(0).getEmail().equals(user.getEmail())) {
                    ValidationUtils.rejectIfEmpty(errors, "email", "msg.code.exist");
                }
            }
        }
        ValidationUtils.rejectIfEmpty(errors, "phone", "msg.required");
        if(!StringUtils.isEmpty(user.getPhone())) {
            List<Users> users = userService.findByProperty("phone", user.getPhone());
            if(users!=null && !users.isEmpty()) {
                if(users.get(0).getPhone().equals(user.getPhone())) {
                    ValidationUtils.rejectIfEmpty(errors, "phone", "msg.code.exist");
                }
            }
        }
    }
}
