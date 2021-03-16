package com.dao;


import com.entity.Task;
import com.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskDAOImpl implements TaskDAO{

    @Autowired
    private TaskRepo taskRepo;

    @Override
    public Iterable<Task> getAllTask(){
        Iterable<Task> tasks = taskRepo.findAll();
        return tasks;
    }

    @Override
    public void saveNewTask(Task task) {
        taskRepo.save(task);
    }

    @Override
    public Task getIdTask(int id) {
        Optional<Task> optionalTask = taskRepo.findById(id);
        return optionalTask.get();
    }

    @Override
    public void deletedIdTask(int id) {
        taskRepo.deleteById(id);
    }

}
