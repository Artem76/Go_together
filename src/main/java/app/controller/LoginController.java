package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping
    public String loginPage() {
        return "login";
    }

    @RequestMapping("?error")
    public String loginError(Model model) {
        model.addAttribute("pass" , "error");
        return "redirect:/login";
    }
}
