package ca.vancouverdesis.antakshriweb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Buzzer {
    private String teamName;
    private String personName;

    public Buzzer(String teamName, String personName) {
        this.teamName = teamName;
        this.personName = personName;
    }

    public Buzzer() {
    }
}
