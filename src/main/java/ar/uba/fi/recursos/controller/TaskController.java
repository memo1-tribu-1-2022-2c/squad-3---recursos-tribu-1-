package ar.uba.fi.recursos.controller;

import ar.uba.fi.recursos.model.Task;
import ar.uba.fi.recursos.repository.TaskRepository;
import ar.uba.fi.recursos.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/task")
@EnableSwagger2
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    @PostMapping(path = "")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        return ResponseEntity.of(taskRepository.findById(id));
    }
}