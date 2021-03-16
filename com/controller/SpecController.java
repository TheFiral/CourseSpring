package com.controller;

import com.dao.TaskDAO;
import com.domain.CreateFile;
import com.domain.Role;
import com.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Controller
public class SpecController {

    @Autowired
    private TaskDAO taskDAO;
    @Autowired
    private CreateFile createFile;

    @GetMapping("/spec")
    public String viewGetSpecView(Model model) {
        Iterable<Task> iterable = taskDAO.getAllTask();
        List<Task> tasks = new LinkedList<>();
        iterable.forEach(task -> {
            if (task.getExecutor().equals(Role.SPECIALIST.getLogin())) {
                tasks.add(task);
            }
        });
        model.addAttribute("tasks", tasks);
        return "spec";
    }

    @GetMapping("spec/{fileTaskName}")
    public ResponseEntity<ByteArrayResource> viewGetLoadTaskFile(
            @PathVariable("fileTaskName") String name) throws IOException {
        return createFile.downloadTaskFile(name);
    }

    @PostMapping("spec/{id}")
    public String postSpecDecision(@PathVariable("id") int id,
                                   @RequestParam("file") MultipartFile file) throws IOException {

        Task task = taskDAO.getIdTask(id);
        task.setDecisionTaskName(createFile.createNewDecisionFile(file));
        task.setOwner(Role.SPECIALIST.getLogin());
        task.setExecutor(Role.HEAD.getLogin());

        taskDAO.saveNewTask(task);
        return "redirect:/spec";
    }
}
