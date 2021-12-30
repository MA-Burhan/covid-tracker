package maburhan.covidtracker.repositories;

import maburhan.covidtracker.model.CovidData;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CovidDataRepository {

    private List<CovidData> covidDataList = new ArrayList<>();

    public void setCovidDataList(List<CovidData> covidDataList) {
        this.covidDataList = covidDataList;
    }

    public List<CovidData> getCovidDataList() {
        return covidDataList;
    }

}
