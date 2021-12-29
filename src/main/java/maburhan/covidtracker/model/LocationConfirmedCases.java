package maburhan.covidtracker.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LocationConfirmedCases extends Location {

    @Builder
    public LocationConfirmedCases(String state, String country, int total, int increase) {
        super(state, country, total, increase);
    }
}
