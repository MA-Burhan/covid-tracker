package maburhan.covidtracker.services;

import maburhan.covidtracker.model.LocationStats;
import maburhan.covidtracker.repositories.CovidDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CovidDataService {

    CovidDataRepository repository;

    public CovidDataService(CovidDataRepository repository) {
        this.repository = repository;
    }

    public List<LocationStats> getStats(String typeOfStats) {
        return repository.getAllStats().get(typeOfStats);
    }

    public int getTotalCases(String typeOfStats) {
        return repository.getAllStats().get(typeOfStats)
                .stream()
                .mapToInt(stat -> stat.getTotal())
                .sum();
    }

    public int getTotalNewCases(String typeOfStats) {
        return repository.getAllStats().get(typeOfStats)
                .stream()
                .mapToInt(stat -> stat.getIncrease())
                .sum();
    }

    public LocalDate getLastUpdateDate(String typeOfStats){
        return repository.getLatestUpdateDates().get(typeOfStats);
    }
}
