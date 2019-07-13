package pl.karolgebert.stibo.controller;

import org.springframework.http.ResponseEntity;
import pl.karolgebert.stibo.dto.TaskDto;

import java.util.List;

public interface CrudController<T, D> {
    D create(D dto);
    List<TaskDto> read();
    ResponseEntity update(long id, D dto);
    ResponseEntity delete(long id);
}
