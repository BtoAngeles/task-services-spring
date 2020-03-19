package com.tasks.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tasks.model.Task;


public interface TaskRepository extends CrudRepository<Task, Integer> {
	List<Task> findByTitle(String title);
	List<Task> findByIsComplete(Boolean isComplete);
}
