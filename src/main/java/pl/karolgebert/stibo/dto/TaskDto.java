package pl.karolgebert.stibo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @NotEmpty
    private String name;
    @Min(0)
    @Max(4)
    private short done;
    @Min(0)
    @Max(4)
    private short planned;
}
