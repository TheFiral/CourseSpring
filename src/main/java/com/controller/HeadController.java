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
import java.io.IOException;
import java.util.*;

@Controller
@PreAuthorize("hasAnyAuthority('HEAD')")
public class HeadController {

    @Value("${upload.path}")
    private String uploadPath;

    public static List<Order> headList = new LinkedList<>();

    @GetMapping("/head")
    public String head(Map<String, Object> model) {
        headList.clear();
        for (int i = 0; i < MainController.list.size(); i++) {
            if ((MainController.list.get(i).getOrderExecutor().equalsIgnoreCase("HEAD"))
            ) {
                headList.add(MainController.list.get(i));
            }
        }
        Iterator<Order> orders = headList.iterator();
        model.put("orders", orders);
        return "head";
    }

    @PostMapping("/head")
    public String result(@RequestParam String orderName, @RequestParam("orderDecision") MultipartFile file, Map<String, Object> model) throws IOException {
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
            for (int i = 0; i < MainController.list.size(); i++) {
                if (MainController.list.get(i).getOrderName().equalsIgnoreCase(orderName)) {
                    MainController.list.get(i).setStatus("На рассмотрении у директора");
                    MainController.list.get(i).setOrderExecutor("DIRECTOR");
                    MainController.list.get(i).setOrderOwner("HEAD");
                    MainController.list.get(i).setOrderDecision(fileName);
                }
            }
        }
        headList.clear();
        for (int i = 0; i < MainController.list.size(); i++) {
            if ((MainController.list.get(i).getOrderExecutor().equalsIgnoreCase("HEAD") && MainController.list.get(i).getStatus().equalsIgnoreCase("Активное"))
                    || (MainController.list.get(i).getStatus().equalsIgnoreCase("На рассмотрении у главы отдела"))
            ) {
                headList.add(MainController.list.get(i));
            }
        }
        Iterator<Order> orders = headList.iterator();
        model.put("orders", orders);
        return "head";
    }

    @PostMapping("/headcheak")
    public String cheakResult(@RequestParam String orderName, Map<String, Object> model) {
        for (int i = 0; i < MainController.list.size(); i++) {
            if (MainController.list.get(i).getOrderName().equalsIgnoreCase(orderName)) {
                MainController.list.get(i).setStatus("На доработку от главы отдела");
                MainController.list.get(i).setOrderExecutor("SPECIALIST");
                MainController.list.get(i).setOrderOwner("HEAD");
            }
        }
        headList.clear();
        Iterator<Order> orders = headList.iterator();
        model.put("orders", orders);
        return "head";
    }

    @PostMapping("/headfile")
    public String headfile(@RequestParam String orderName, @RequestParam String orderOwner, @RequestParam String orderExecutor, Map<String, Object> model, @RequestParam("orderFile") MultipartFile file) throws IOException {
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
            order.setStatus("Активное");
            MainController.list.add(order);
            headList.clear();
            for (int i = 0; i < MainController.list.size(); i++) {
                if ((MainController.list.get(i).getOrderExecutor().equalsIgnoreCase("HEAD"))) {
                    headList.add(MainController.list.get(i));
                }
            }
            Iterator<Order> orders = headList.iterator();
            model.put("orders", orders);
            return "director";
        }
        headList.clear();
        Iterator<Order> orders = headList.iterator();
        model.put("orders", orders);
        return "head";
    }
}
