package com.controller;

import com.domain.Cript;
import com.domain.Order;
import com.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@PreAuthorize("hasAnyAuthority('SPECIALIST')")
public class SpecialistController {

    @Value("${upload.path}")
    private String uploadPath;

    public static List<Order> specList = new LinkedList<>();

    @GetMapping("/specialist")
    public String download(Map<String, Object> model) {
        specList.clear();
        for(int i=0;i<MainController.list.size();i++){
            if(MainController.list.get(i).getOrderExecutor().equalsIgnoreCase("SPECIALIST")){
               specList.add(MainController.list.get(i));
            }
        }
        Iterator<Order> orders = specList.iterator();
        model.put("orders", orders);
        return "specialist";
    }

    @PostMapping("/specialist")
    public String result(@RequestParam String orderName,@RequestParam("orderDecision") MultipartFile file,Map<String, Object> model) throws IOException {
        if (file != null) {
            File upFile = new File(uploadPath);
            if (!upFile.exists()) {
                upFile.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            String fileName = "решение" + uuid + "." + file.getOriginalFilename();
            File newFile = new File(uploadPath + "/" + fileName);
            file.transferTo(newFile);
            Cript.Cript(newFile);
            for(int i=0;i<MainController.list.size();i++){
                if(MainController.list.get(i).getOrderName().equalsIgnoreCase(orderName)){
                    MainController.list.get(i).setStatus("На рассмотрении у главы отдела");
                    MainController.list.get(i).setOrderExecutor("HEAD");
                    MainController.list.get(i).setOrderOwner("SPECIALIST");
                    MainController.list.get(i).setOrderDecision(fileName);
                }
            }
        }
        specList.clear();
        for(int i=0;i<MainController.list.size();i++){
            if(MainController.list.get(i).getOrderExecutor().equalsIgnoreCase(User.user.getRole()) && MainController.list.get(i).getStatus().equalsIgnoreCase("Активный")){
                specList.add(MainController.list.get(i));
            }
        }
        Iterator<Order> orders = specList.iterator();
        model.put("orders", orders);
        return "specialist";
    }
}
