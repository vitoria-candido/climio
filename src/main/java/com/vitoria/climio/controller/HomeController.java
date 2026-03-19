package com.vitoria.climio.controller;

import com.vitoria.climio.dto.SearchFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("searchForm", new SearchFormDTO());
        return "index";
    }
}