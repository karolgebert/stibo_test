package pl.karolgebert.stibo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.karolgebert.stibo.dao.TaskRepository;
import pl.karolgebert.stibo.dto.TaskDto;
import pl.karolgebert.stibo.model.Task;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TaskController implements CrudController<Task, TaskDto> {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid TaskDto dto) {
        Task task = new Task(dto);
        taskRepository.save(task);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<TaskDto> read() {
        return taskRepository
                .getAll()
                .stream()
                .map(task -> new TaskDto(task.getName(), task.getStatus().getDone(), task.getStatus().getPlanned()))
                .collect(Collectors.toList());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody @Valid TaskDto dto) {
        taskRepository.update(id, new Task(dto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        taskRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
