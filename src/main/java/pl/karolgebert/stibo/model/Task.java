package pl.karolgebert.stibo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.karolgebert.stibo.dto.TaskDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Task {
    private String name;
    private Status status;

    public Task(TaskDto dto) {
        this.name = dto.getName();
        this.status = new Status(
                dto.getDone(),
                dto.getPlanned()
        );
    }
}
