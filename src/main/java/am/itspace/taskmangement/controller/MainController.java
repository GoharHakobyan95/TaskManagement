package am.itspace.taskmangement.controller;

import am.itspace.taskmangement.entity.Role;
import am.itspace.taskmangement.entity.User;
import am.itspace.taskmangement.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }


    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            if (user.getRole() == Role.MANAGER) {
                return "redirect:/manager";
            } else if (user.getRole() == Role.USER) {
                return "redirect:/user";
            }
        }
        return "redirect:/";
    }

}
