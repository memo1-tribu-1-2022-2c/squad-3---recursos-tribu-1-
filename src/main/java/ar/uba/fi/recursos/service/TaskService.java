package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.model.Task;
import ar.uba.fi.recursos.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        // hourDetail.setId(-1L);
        return taskRepository.save(task);
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }
}