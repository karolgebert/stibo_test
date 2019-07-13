package pl.karolgebert.stibo.dao;

import org.springframework.stereotype.Repository;
import pl.karolgebert.stibo.model.Task;

import java.util.*;

@Repository
public class TaskRepository extends RepositoryIdGenerator implements Dao<Task> {

    // <id, task>
    private Map<Long, Task> tasks = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Optional<Task> get(long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> getAll() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task save(Task task) {
        Long id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return task;
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
