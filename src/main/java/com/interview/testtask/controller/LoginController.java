package com.interview.testtask.controller;

import com.interview.testtask.entity.Role;
import com.interview.testtask.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;


/**
 * Login controller redirects to a specific endpoint depending on the user authority.
 */

@AllArgsConstructor
@Controller
@Slf4j
public class LoginController {

    @RequestMapping(value = "/success", method = {RequestMethod.GET, RequestMethod.POST})
    public String user(@AuthenticationPrincipal User user) {
        Set<Role> roles = user.getRoles();
        if (roles.contains(Role.ROLE_ADMIN)){
            return "redirect:/admin";
        } else if (roles.contains(Role.ROLE_COURIER)){
            return "redirect:/order";
        } else if (roles.contains(Role.ROLE_OPERATOR)){
            return "redirect:/order-manage";
        }
        return "redirect:/greeting";
    }

}
