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

    public List<LocationStats> getAllStats() {
        return repository.getLocationStatsList();
    }

    public int getTotalNumberCases() {
        return repository.getLocationStatsList().stream()
                .mapToInt(stat -> stat.getTotalNumOfCasesLatest())
                .sum();
    }

    public int getTotalNumberNewCases() {
        return repository.getLocationStatsList().stream()
                .mapToInt(stat -> stat.getNumOfNewCases())
                .sum();
    }

    public LocalDate getLastUpdateDate(){
        return repository.getLastUpdateDate();
    }
}
