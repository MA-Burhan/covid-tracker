package maburhan.covidtracker.model;

import lombok.Builder;

public class LocationDeaths extends Location{

    @Builder
    public LocationDeaths(String state, String country, int total, int increase) {
        super(state, country, total, increase);
    }
}
