package pl.karolgebert.stibo.dao;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import pl.karolgebert.stibo.model.Task;

import java.util.*;

@Repository
public class TaskRepository extends RepositoryIdGenerator implements Dao<Task> {

    // <id, task>
    private Map<Long, Task> tasks = new HashMap<>();

    @Override
    public Optional<Task> get(long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> getAll() {
        return (List<Task>) tasks.values();
    }

    @Override
    public void save(Task task) {
        tasks.put(generateId(), task);
    }

    @Override
    public void update(long id, Task task) {
        tasks.put(id, task);
    }

    @Override
    public void delete(long id) {
        tasks.remove(id);
    }
}
