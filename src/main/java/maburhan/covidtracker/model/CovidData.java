package maburhan.covidtracker.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CovidData {

    private String state;
    private String country;

    private int totalConfirmedCases;
    private int newConfirmedCases;
    private LocalDate confirmedCasesLastUpdate;

    private int totalDeaths;
    private int newDeaths;
    private LocalDate deathsLastUpdate;

    private int total;
    private int increase;

    @Builder
    public CovidData(String state, String country, int totalConfirmedCases, int newConfirmedCases, LocalDate confirmedCasesLastUpdate, int totalDeaths, int newDeaths, LocalDate deathsLastUpdate, int total, int increase) {
        this.state = state;
        this.country = country;
        this.totalConfirmedCases = totalConfirmedCases;
        this.newConfirmedCases = newConfirmedCases;
        this.confirmedCasesLastUpdate = confirmedCasesLastUpdate;
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.deathsLastUpdate = deathsLastUpdate;
        this.total = total;
        this.increase = increase;
    }
}
