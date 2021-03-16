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
public class HeadController {

    @Autowired
    private TaskDAO taskDAO;
    @Autowired
    private CreateFile createFile;

    @GetMapping("/head")
    public String viewGetHeadView(Model model) {
        Iterable<Task> iterable = taskDAO.getAllTask();
        List<Task> tasks = new LinkedList<>();
        iterable.forEach(task -> {
            if (task.getExecutor().equals(Role.HEAD.getLogin())) {
                tasks.add(task);
            }
        });
        model.addAttribute("tasks", tasks);
        return "head";
    }

    @PostMapping("/head_new_task")
    public String postHeadNewTask(@RequestParam String name, @RequestParam String owner,
                                  @RequestParam String executor,
                                  @RequestParam("taskFileName") MultipartFile file)
            throws IOException {

        Task task = new Task(name, owner, executor);
        task.setFileTaskName(createFile.createNewTaskFile(file));

        taskDAO.saveNewTask(task);
        return "redirect:/head";
    }

    @GetMapping("head/{decisionTaskName}")
    public ResponseEntity<ByteArrayResource> viewGetLoadDecisionFile(
            @PathVariable("decisionTaskName") String name) throws IOException {
        return createFile.downloadDecisionFile(name);
    }

    @PostMapping("head_down/{id}")
    public String postHeadTaskDown(@PathVariable("id") int id){

        Task task = taskDAO.getIdTask(id);
        task.setOwner(Role.HEAD.getLogin());
        task.setExecutor(Role.SPECIALIST.getLogin());

        taskDAO.saveNewTask(task);
        return "redirect:/head";
    }

    @PostMapping("head_up/{id}")
    public String postHeadTaskUp(@PathVariable("id") int id){

        Task task = taskDAO.getIdTask(id);
        task.setOwner(Role.HEAD.getLogin());
        task.setExecutor(Role.DIRECTOR.getLogin());

        taskDAO.saveNewTask(task);
        return "redirect:/head";
    }

    @PostMapping("head_upload/{id}")
    public String postHeadDecision(@PathVariable("id") int id,
                                   @RequestParam("file") MultipartFile file) throws IOException {

        Task task = taskDAO.getIdTask(id);
        task.setDecisionTaskName(createFile.createNewDecisionFile(file));
        task.setOwner(Role.HEAD.getLogin());
        task.setExecutor(Role.SPECIALIST.getLogin());

        taskDAO.saveNewTask(task);
        return "redirect:/head";
    }
}
