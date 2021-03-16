package com.controller;

import com.domain.ElectronicPrinting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

    @GetMapping("/main")
    public String viewGetMainView(Model model){
        model.addAttribute("check", "не обнаружено");
        return "main";
    }

    @GetMapping("/")
    public String viewGetMainViewTwo(Model model){
        model.addAttribute("check", "не обнаружено");
        return "main";
    }

    @PostMapping("main")
    public String postCheckSignFile(@RequestParam("file") MultipartFile file, Model model){
        model.addAttribute("check",ElectronicPrinting.checkSignFile(file));
        return "main";
    }
}
