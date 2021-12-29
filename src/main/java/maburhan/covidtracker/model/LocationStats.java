package maburhan.covidtracker.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocationStats {

    private String state;
    private String country;
    private int total;
    private int increase;

    @Builder
    public LocationStats(String state, String country, int total, int increase) {
        this.state = state;
        this.country = country;
        this.total = total;
        this.increase = increase;
    }
}
