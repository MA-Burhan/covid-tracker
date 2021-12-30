package maburhan.covidtracker.services;

import maburhan.covidtracker.model.CovidData;
import maburhan.covidtracker.repositories.CovidDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CovidDataServiceImpl implements CovidDataService {

    CovidDataRepository covidDataRepository;

    public CovidDataServiceImpl(CovidDataRepository repository) {
        this.covidDataRepository = repository;
    }

    @Override
    public List<CovidData> getCovidData(){
        return covidDataRepository.getCovidDataList();
    }

    @Override
    public int getTotalConfirmedCasesGlobal() {
        return covidDataRepository.getCovidDataList()
                .stream()
                .mapToInt(covidData -> covidData.getTotalConfirmedCases())
                .sum();
    }

    @Override
    public int getTotalDeathsGlobal() {
        return covidDataRepository.getCovidDataList()
                .stream()
                .mapToInt(covidData -> covidData.getTotalDeaths())
                .sum();
    }


    @Override
    public int getNewConfirmedCasesGlobal() {
        return covidDataRepository.getCovidDataList()
                .stream()
                .mapToInt(covidData -> covidData.getNewConfirmedCases())
                .sum();
    }

    @Override
    public int getNewDeathsGlobal() {
        return covidDataRepository.getCovidDataList()
                .stream()
                .mapToInt(covidData -> covidData.getNewDeaths())
                .sum();
    }

    @Override
    public LocalDate getLastUpdateConfirmedCases(){
        return covidDataRepository.getCovidDataList().get(0).getConfirmedCasesLastUpdate();
    }

    @Override
    public LocalDate getLastUpdateDeaths(){
        return covidDataRepository.getCovidDataList().get(0).getDeathsLastUpdate();
    }

}
