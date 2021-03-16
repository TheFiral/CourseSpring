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
public class DirectorController {

    @Autowired
    private TaskDAO taskDAO;
    @Autowired
    private CreateFile createFile;

    @GetMapping("/director")
    public String viewGetDirectorView(Model model) {
        Iterable<Task> iterable = taskDAO.getAllTask();
        List<Task> tasks = new LinkedList<>();
        iterable.forEach(task -> {
            if (task.getExecutor().equals(Role.DIRECTOR.getLogin())) {
                tasks.add(task);
            }
        });
        model.addAttribute("tasks", tasks);
        return "director";
    }

    @PostMapping("/director_new_task")
    public String postDirectorNewTask(@RequestParam String name, @RequestParam String owner,
                                      @RequestParam String executor,
                                      @RequestParam("taskFileName") MultipartFile file)
            throws IOException {

        Task task = new Task(name, owner, executor);
        task.setFileTaskName(createFile.createNewTaskFile(file));

        taskDAO.saveNewTask(task);
        return "redirect:/director";
    }

    @GetMapping("director/{decisionTaskName}")
    public ResponseEntity<ByteArrayResource> viewGetLoadDecisionFile(
            @PathVariable("decisionTaskName") String name) throws IOException {
        return createFile.downloadDecisionFile(name);
    }

    @PostMapping("director_down/{id}")
    public String postDirectorTaskDown(@PathVariable("id") int id){

        Task task = taskDAO.getIdTask(id);
        task.setOwner(Role.DIRECTOR.getLogin());
        task.setExecutor(Role.HEAD.getLogin());

        taskDAO.saveNewTask(task);
        return "redirect:/director";
    }

    @PostMapping("director_deleted/{id}")
    public String postDirectorTaskDeleted(@PathVariable("id") int id){
        taskDAO.deletedIdTask(id);
        return "redirect:/director";
    }
}
