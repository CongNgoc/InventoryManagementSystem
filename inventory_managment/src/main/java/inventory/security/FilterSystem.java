package inventory.security;

import inventory.model.*;
import inventory.service.AuthService;
import inventory.service.MenuService;
import inventory.service.RoleService;
import inventory.service.UserRoleService;
import inventory.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FilterSystem implements HandlerInterceptor {
    Logger log = Logger.getLogger(FilterSystem.class);
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AuthService authService;
    @Autowired
    private MenuService menuService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Request URI= " + request.getRequestURI());
        Users users = (Users) request.getSession().getAttribute(Constant.USER_INFO);
        if(users == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        if(users != null) {
            log.info("=======user is not null");
            String url = request.getServletPath();
            if(!hasPermission(url, users)) {
                log.info("=======has permission");
                response.sendRedirect(request.getContextPath() + "/access-denied");
                return false;
            }

        }
        return true;
    }

    private boolean hasPermission(String url, Users users) {
        if(url.contains("/index") || url.contains("/logout") || url.contains("/access-denied") || url.contains("/weatherAPI")) {
            return true;
        }
        //Duplicate code => optimize
        UserRole userRole = userRoleService.findUserRoleByProperty("userId", users.getUserId()).get(0);
        List<Auth> authList = authService.findAuthByProperty("roleId", userRole.getRoleId());

        for(Auth auth:authList) {
            Menu menu = menuService.findMenuByProperty("menuId", auth.getMenuId()).get(0);
            if(url.contains(menu.getUrl())) {
                log.info("=======has permission: " + auth.isPermission());
                return auth.isPermission();

            }
        }
        return false;
    }

}
