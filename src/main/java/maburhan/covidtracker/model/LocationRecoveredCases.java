package maburhan.covidtracker.model;

import lombok.Builder;

public class LocationRecoveredCases extends Location{

    @Builder
    public LocationRecoveredCases(String state, String country, int total, int increase) {
        super(state, country, total, increase);
    }
}
