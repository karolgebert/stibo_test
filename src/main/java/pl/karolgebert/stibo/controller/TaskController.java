package pl.karolgebert.stibo.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.karolgebert.stibo.dao.TaskRepository;
import pl.karolgebert.stibo.dto.TaskDto;
import pl.karolgebert.stibo.model.Task;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController implements CrudController<Task, TaskDto> {
    private final TaskRepository taskRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void init() {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @PostMapping
    public TaskDto create(@RequestBody @Valid TaskDto dto) {
        Task task = modelMapper.map(dto, Task.class);
        task = taskRepository.save(task);
        return modelMapper.map(task, TaskDto.class);
    }

    @GetMapping
    public List<TaskDto> read() {
        return taskRepository
                .getAll()
                .stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody @Valid TaskDto dto) {
        taskRepository.update(id, modelMapper.map(dto, Task.class));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        taskRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
