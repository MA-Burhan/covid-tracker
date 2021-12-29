package maburhan.covidtracker.model;

import lombok.Builder;
import lombok.ToString;

@ToString
public class LocationConfirmedCases extends Location {

    @Builder
    public LocationConfirmedCases(String state, String country, int total, int increase) {
        super(state, country, total, increase);
    }
}
