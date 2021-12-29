package maburhan.covidtracker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Location {

    private String state;
    private String country;
    private int total;
    private int increase    ;

    public Location(String state, String country, int total, int increase) {
        this.state = state;
        this.country = country;
        this.total = total;
        this.increase = increase;
    }
}
