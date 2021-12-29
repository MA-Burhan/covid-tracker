package maburhan.covidtracker.repositories;

import maburhan.covidtracker.model.LocationStats;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CovidDataRepository {

    private Map<String, List<LocationStats>> allStats = new HashMap<>();
    private Map<String, LocalDate> latestUpdateDates = new HashMap<>();
    private Map<String, String> urls = new HashMap<>();
    private LocationStatsRepository repository;

    public CovidDataRepository(LocationStatsRepository repository) {
        this.repository = repository;

        urls.put("confirmed", "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/" +
                "csse_covid_19_time_series/time_series_covid19_confirmed_global.csv");
        urls.put("recovered", "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/" +
                "csse_covid_19_time_series/time_series_covid19_recovered_global.csv");
        urls.put("deaths", "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/" +
                "csse_covid_19_time_series/time_series_covid19_deaths_global.csv");
    }

    @PostConstruct
    @Scheduled(cron = "0 0 1 * * *")
    public void processCovidData(){

        processCovidData("confirmed");
        processCovidData("deaths");
    }

    public void processCovidData(String typeOfData){
        Object[] data = repository.processStats(urls.get(typeOfData));
        allStats.put(typeOfData, (List<LocationStats>) data[0]);
        latestUpdateDates.put(typeOfData, (LocalDate) data[1]);
    }

    public Map<String, List<LocationStats>> getAllStats() {
        return allStats;
    }

    public Map<String, LocalDate> getLatestUpdateDates() {
        return latestUpdateDates;
    }
}
