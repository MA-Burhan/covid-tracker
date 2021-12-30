package maburhan.covidtracker.services;

import maburhan.covidtracker.model.CovidData;
import maburhan.covidtracker.repositories.CovidDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CovidDataService {

    CovidDataRepository covidDataRepository;

    public CovidDataService(CovidDataRepository repository) {
        this.covidDataRepository = repository;
    }

    public List<CovidData> getCovidData(){
        return covidDataRepository.getCovidDataList();
    }

    public int getTotalConfirmedCasesGlobal() {
        return covidDataRepository.getCovidDataList()
                .stream()
                .mapToInt(covidData -> covidData.getTotalConfirmedCases())
                .sum();
    }

    public int getTotalDeathsGlobal() {
        return covidDataRepository.getCovidDataList()
                .stream()
                .mapToInt(covidData -> covidData.getTotalDeaths())
                .sum();
    }


    public int getNewConfirmedCasesGlobal() {
        return covidDataRepository.getCovidDataList()
                .stream()
                .mapToInt(covidData -> covidData.getNewConfirmedCases())
                .sum();
    }

    public int getNewDeathsGlobal() {
        return covidDataRepository.getCovidDataList()
                .stream()
                .mapToInt(covidData -> covidData.getNewDeaths())
                .sum();
    }

    public LocalDate getLastUpdateConfirmedCases(){
        return covidDataRepository.getCovidDataList().get(0).getConfirmedCasesLastUpdate();
    }

    public LocalDate getLastUpdateDeaths(){
        return covidDataRepository.getCovidDataList().get(0).getDeathsLastUpdate();
    }
    
}
