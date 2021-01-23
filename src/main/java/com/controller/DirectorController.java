package com.controller;

import com.domain.Cript;
import com.domain.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Controller
@PreAuthorize("hasAnyAuthority('DIRECTOR')")
public class DirectorController {
    @Value("${upload.path}")
    private String uploadPath;

    public static List<Order> dirOrder = new LinkedList<>();

    @GetMapping("/director")
    public String download(Map<String, Object> model) {
        dirOrder.clear();
        for (int i = 0; i < MainController.list.size(); i++) {
            if ((MainController.list.get(i).getOrderExecutor().equalsIgnoreCase("DIRECTOR"))) {
                dirOrder.add(MainController.list.get(i));
            }
        }
        Iterator<Order> orders = dirOrder.iterator();
        model.put("orders", orders);
        return "/director";
    }

    @PostMapping("/director")
    public String add(@RequestParam String orderName, @RequestParam String orderOwner, @RequestParam String orderExecutor, Map<String, Object> model, @RequestParam("orderFile") MultipartFile file) throws IOException {
        if (MainController.checkName(orderName)) {
            Order order = new Order(orderName, orderOwner, orderExecutor);
            if (file != null) {
                File upFile = new File(uploadPath);
                if (!upFile.exists()) {
                    upFile.mkdir();
                }
                String uuid = UUID.randomUUID().toString();
                String fileName = uuid + "." + file.getOriginalFilename();
                File newFile = new File(uploadPath + "/" + fileName);
                file.transferTo(newFile);
                Cript.Cript(newFile);
                order.setOrderFile(fileName);
            }
            order.setStatus("Активный");
            MainController.list.add(order);
            dirOrder.clear();
            for (int i = 0; i < MainController.list.size(); i++) {
                if ((MainController.list.get(i).getOrderExecutor().equalsIgnoreCase("DIRECTOR"))) {
                    dirOrder.add(MainController.list.get(i));
                }
            }
            Iterator<Order> orders = dirOrder.iterator();
            model.put("orders", orders);
            return "director";
        }
        Iterator<Order> orders = dirOrder.iterator();
        model.put("orders", orders);
        return "director";
    }

    @PostMapping("/dircheak")
    public String cheakResult(@RequestParam String orderName, Map<String, Object> model) {
        for (int i = 0; i < MainController.list.size(); i++) {
            if (MainController.list.get(i).getOrderName().equalsIgnoreCase(orderName)) {
                MainController.list.get(i).setStatus("На доработку от директора");
                MainController.list.get(i).setOrderExecutor("HEAD");
                MainController.list.get(i).setOrderOwner("DIRECTOR");
            }
        }
        dirOrder.clear();
        Iterator<Order> orders = dirOrder.iterator();
        model.put("orders", orders);
        return "director";
    }

    @PostMapping("/direnter")
    public String enterResult(@RequestParam String orderName, Map<String, Object> model) throws FileNotFoundException {
        for (int i = 0; i < MainController.list.size(); i++) {
            if (MainController.list.get(i).getOrderName().equalsIgnoreCase(orderName)) {
                MainController.list.remove(i);
            }
        }
        dirOrder.clear();
        Iterator<Order> orders = dirOrder.iterator();
        model.put("orders", orders);
        return "director";
    }


}
