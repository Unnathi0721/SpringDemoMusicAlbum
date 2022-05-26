package spring.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {

        return "login";

    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";

    }
    @GetMapping("/")
    public String showLogin() {

        return "home";
    }
}
