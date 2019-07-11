package pl.karolgebert.stibo.controller;

import org.springframework.http.ResponseEntity;
import pl.karolgebert.stibo.model.Task;

import java.util.List;

public interface CrudController<T, D> {
    ResponseEntity create(D dto);
    List<Task> read();
    ResponseEntity update(long id, D dto);
    ResponseEntity delete(long id);
}
