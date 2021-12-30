package maburhan.covidtracker.services;

import maburhan.covidtracker.model.CovidData;

import java.time.LocalDate;
import java.util.List;

public interface CovidDataService {
    List<CovidData> getCovidData();

    int getTotalConfirmedCasesGlobal();

    int getTotalDeathsGlobal();

    int getNewConfirmedCasesGlobal();

    int getNewDeathsGlobal();

    LocalDate getLastUpdateConfirmedCases();

    LocalDate getLastUpdateDeaths();
}
