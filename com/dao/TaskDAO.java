package com.dao;

import com.entity.Task;

public interface TaskDAO {

    public Iterable<Task> getAllTask();

    public void saveNewTask(Task task);

    public Task getIdTask(int id);

    public void deletedIdTask(int id);
}
