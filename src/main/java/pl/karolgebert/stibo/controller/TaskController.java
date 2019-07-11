package pl.karolgebert.stibo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.karolgebert.stibo.dao.TaskRepository;
import pl.karolgebert.stibo.dto.TaskDto;
import pl.karolgebert.stibo.model.Task;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController implements CrudController<Task, TaskDto> {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public ResponseEntity create(@ModelAttribute TaskDto dto) {
        Task task = new Task(dto);
        taskRepository.save(task);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Task> read() {
        return taskRepository.getAll();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable long id, @ModelAttribute TaskDto dto) {
        taskRepository.update(id, new Task(dto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        taskRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}