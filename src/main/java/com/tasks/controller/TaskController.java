package com.tasks.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tasks.entity.Graph;
import com.tasks.model.Task;
import com.tasks.repository.TaskRepository;

@Controller
@RequestMapping(path = "/tasks")
public class TaskController {
	@Autowired
	
	private TaskRepository taskRepository;
	
	@CrossOrigin
	@PostMapping(path = "/add")
	public @ResponseBody String addNewTask (@RequestBody Task body) {
		Task newTask = new Task();
		newTask.setTitle(body.getTitle());
		newTask.setDescription(body.getDescription());
		newTask.setTime(body.getTime());
		newTask.setCreateAt(new Date());
		newTask.setIsComplete(body.getIsComplete());
		taskRepository.save(newTask);
		return "Save";
	}
	
	@CrossOrigin
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	
	@CrossOrigin
	@GetMapping(path = "/search")
	public @ResponseBody List<Task> getByTitle(@RequestParam String title) {
		return taskRepository.findByTitle(title);
	}
	
	@CrossOrigin
	@GetMapping(path = "/complete")
	public @ResponseBody List<Task> getByComplete(@RequestParam Boolean isComplete) {
		return taskRepository.findByIsComplete(isComplete);
	}
	
	@CrossOrigin
	@GetMapping(path = "/data-graph")
	public @ResponseBody Graph getDataGraphs() {
		Integer complete = taskRepository.findByIsComplete(true).size();
		Integer missing = taskRepository.findByIsComplete(false).size(); 
		Graph graps = new Graph();
		graps.setComplete(complete);
		graps.setMissing(missing);
		graps.setTotal(complete + missing); 
		return graps;
	}
	
	@CrossOrigin
	@DeleteMapping(path = "/delete")
	public @ResponseBody String deleteTask (@RequestParam Integer id ) {
		System.out.println("Id --> "+ id);
		taskRepository.deleteById(id);
		return "Delete";
	}
	
	@CrossOrigin
	@PatchMapping(path = "/update")
	public @ResponseBody String update (@RequestBody Task body, @RequestParam Integer id ) {
		Task updateTask = new Task();
		updateTask = taskRepository.findById(id).get();
		updateTask.setTitle(body.getTitle());
		updateTask.setDescription(body.getDescription());
		updateTask.setTime(body.getTime());
		updateTask.setIsComplete(body.getIsComplete());
		taskRepository.save(updateTask);
		return "Update";
	}
	
}
