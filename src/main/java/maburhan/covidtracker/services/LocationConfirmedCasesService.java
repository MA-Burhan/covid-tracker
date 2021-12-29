package maburhan.covidtracker.services;

import maburhan.covidtracker.model.LocationConfirmedCases;
import maburhan.covidtracker.repositories.LocationConfirmedCasesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LocationConfirmedCasesService {

    LocationConfirmedCasesRepository repository;

    public LocationConfirmedCasesService(LocationConfirmedCasesRepository repository) {
        this.repository = repository;
    }

    public List<LocationConfirmedCases> getAllStats() {
        return repository.getLocationConfimedCasesList();
    }

    public int getTotalNumberCases() {
        return repository.getLocationConfimedCasesList().stream()
                .mapToInt(stat -> stat.getTotal())
                .sum();
    }

    public int getTotalNumberNewCases() {
        return repository.getLocationConfimedCasesList().stream()
                .mapToInt(stat -> stat.getIncrease())
                .sum();
    }

    public LocalDate getLastUpdateDate(){
        return repository.getLastUpdateDate();
    }
}
