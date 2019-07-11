package pl.karolgebert.stibo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
class Status {
    private short done;
    private short planed;

    Status(int done, int planed) {
        this.done = (short)done;
        this.planed = (short)planed;
    }
}
