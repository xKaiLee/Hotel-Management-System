package com.example.hotel.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PanelController {
    @GetMapping("/choose-panel")
    public String showChoosePanelPage() {
        return "choose_panel";
    }

    @PostMapping("/choose-panel")
    public String choosePanel(@RequestParam("panel") String panel) {
        if ("admin".equals(panel)) {
            return "redirect:/admin-panel";
        } else if ("user".equals(panel)) {
            return "redirect:/user-panel";
        }
        return "redirect/choose-panel";
    }

    @GetMapping("/user-panel")
    public String showUserPanel() {
        return "user_panel";
    }

    @GetMapping("/admin-panel")
    public String showAdminPanel() {return "admin-panel";}
}



