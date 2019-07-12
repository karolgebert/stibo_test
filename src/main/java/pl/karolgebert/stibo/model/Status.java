package pl.karolgebert.stibo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Status {
    private short done;
    private short planned;

    public Status(int done, int planned) {
        this.done = (short)done;
        this.planned = (short) planned;
    }
}
