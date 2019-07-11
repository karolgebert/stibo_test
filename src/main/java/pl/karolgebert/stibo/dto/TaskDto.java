package pl.karolgebert.stibo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    private String name;
    private short done;
    private short planned;
}
