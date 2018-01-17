package app.controller;

import app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NocVoIPController {
    @Autowired
    UserService userService;

    @RequestMapping("/noc_voip")
    public String nocVoIP(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        String name = userService.getUserByLogin(login).getName();
        model.addAttribute("name", name);
        return "noc_voip/noc_voip_index";
    }
}
