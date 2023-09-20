package team.lc01.lb02.c.evss.web.controller;

import org.springframework.ui.Model;
import team.lc01.lb02.c.evss.util.URL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Value;

@Controller
public class MainController {
    @Value("${spring.application.name}")
    String appName;
    String groupName;

    public MainController() {
        this.groupName = "LC01-LB02-C";
    }

    /**
     * The home page
     *
     */
    @GetMapping(value = URL.ROOT)
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("groupName", groupName);
        return "index";
    }
}
