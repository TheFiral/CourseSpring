package com.controller;

import com.domain.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;



//класс для удобного тестирования
@Controller
public class MainController {


    @Value("${upload.path}")
    private String uploadPath;

    public static List<Order> list = new LinkedList<Order>();

    @GetMapping("/")
    public String mainPage(Map<String, Object> model) {
        Iterator<Order> orders = list.iterator();
        model.put("orders", orders);
        return "main";
    }


    @GetMapping("/main")
    public String maining(Map<String, Object> model) {
        Iterator<Order> orders = list.iterator();
        model.put("orders", orders);
        return "main";
    }


    @PostMapping("/main")
    public String add(@RequestParam String orderName, @RequestParam String orderOwner, @RequestParam String orderExecutor, Map<String, Object> model, @RequestParam("orderFile") MultipartFile file) throws IOException {
        Order order = new Order(orderName, orderOwner, orderExecutor);
        if (file != null) {
            File upFile = new File(uploadPath);
            if (!upFile.exists()) {
                upFile.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + fileName));
            order.setOrderFile(fileName);
        }
        order.setStatus("Активное");
        list.add(order);
        Iterator<Order> orders = list.iterator();
        model.put("orders", orders);
        return "main";
    }

    public static boolean checkName(String name) {
        for (int i = 0; i < MainController.list.size(); i++) {
            if (MainController.list.get(i).getOrderName().equalsIgnoreCase(name) && (name.length() > 4)) {
                return false;
            }
        }
        return true;
    }


    @GetMapping(value = "/get-file",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] getFile() throws IOException {
        File file = new File("testFile.txt");
        file.createNewFile();

        byte[] Arr = Files.readAllBytes(Paths.get(uploadPath + "/" + "file"));
        return Arr;
    }

//    @GetMapping("{fileName}")
//    public ResponseEntity<ByteArrayResource> load(@PathVariable("fileName") String name) throws IOException {
//        return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + name + "\"")
//                .body(new ByteArrayResource(Files.readAllBytes(Paths.get(uploadPath + "/" + name))));
//    }

    @GetMapping("{orderDecision}")
    public ResponseEntity<ByteArrayResource> loadDescription(@PathVariable("orderDecision") String name) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + name + "\"")
                .body(new ByteArrayResource(Files.readAllBytes(Paths.get(uploadPath + "/" + name))));
    }

}
