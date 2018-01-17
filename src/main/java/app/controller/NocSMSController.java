package app.controller;

import app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NocSMSController {
    @Autowired
    UserService userService;

    @RequestMapping("/noc_sms")
    public String nocSMS(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        String name = userService.getUserByLogin(login).getName();
        model.addAttribute("name", name);
        return "noc_sms/noc_sms_index";
    }
}
