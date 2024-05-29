package com.example.racketready.Web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class controller {

    @GetMapping(path="/")
    public String InitialPage(Model model) {
        return "Main";
    }

}
