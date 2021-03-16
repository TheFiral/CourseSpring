package com.repos;

import com.entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepo extends CrudRepository<Task,Integer> {
}
