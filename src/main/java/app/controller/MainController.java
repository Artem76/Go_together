package app.controller;

import app.entity.CustomUser;
import app.entity.enums.UserRole;
import app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/enter")
    public String entr(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        String role = null;
        try{
            role = userService.getUserByLogin(login).getRole().toString();
        }catch (Exception e){
            return "redirect:/logout";
        }
        switch (role) {
            case "ROLE_Administrator":
                return "redirect:/admin_full_index";
            case "ROLE_Administrator_VoIP":
                return "redirect:/admin_voip";
            case "ROLE_Administrator_SMS":
                return "redirect:/admin_sms";
            case "ROLE_Manager_VoIP":
                return "redirect:/manager_voip";
            case "ROLE_Manager_SMS":
                return "redirect:/manager_sms";
            case "ROLE_NOC_VoIP":
                return "redirect:/noc_voip";
            case "ROLE_NOC_SMS":
                return "redirect:/noc_sms";
            case "ROLE_Report_User":
                return "redirect:/report_user";
        }
        model.addAttribute("role", "error");
        return "blocked";
    }

    @RequestMapping("/profile")
    public String profile(@RequestParam String error,
                          @RequestParam String err_log,
                          Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("user", userService.getUserByLogin(login));
        model.addAttribute("error", error);
        model.addAttribute("err_log", err_log);
        return "profile";
    }

    @RequestMapping(value = "/profile_save", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<String> profileSave(@RequestParam String name_user,
                             @RequestParam String password_user,
                             @RequestParam String confirm_password_user,
                             @RequestParam String phone_user,
                             @RequestParam String email_user) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        List<String> error_list = new ArrayList<>();
        try {
            if (name_user == null || name_user.equals("")) error_list.add("name");
            if (password_user == null) error_list.add("password");
            if (confirm_password_user == null) error_list.add("confirm_password");
            if (!password_user.equals(confirm_password_user)){
                if (!error_list.contains("password")) error_list.add("password");
                if (!error_list.contains("confirm_password")) error_list.add("confirm_password");
            }
            if(error_list.size() == 0){
                if (!userService.updateUser(customUser.getId(), name_user, customUser.getLogin(), password_user, confirm_password_user, phone_user, email_user, customUser.getRole()))
                    error_list.add("data error");
            }
        } catch (Exception e) {
            error_list.add("data error");
        }
        return error_list;
    }


    @RequestMapping("/404")
    public String er404() {
        return "pages-404";
    }

    @RequestMapping("/403")
    public String er403() {
        return "pages-403";
    }

    @RequestMapping("/500")
    public String er500() {
        return "pages-500";
    }
}
