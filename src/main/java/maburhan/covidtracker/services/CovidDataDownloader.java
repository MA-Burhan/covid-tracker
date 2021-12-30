package maburhan.covidtracker.services;

import maburhan.covidtracker.model.CovidData;
import maburhan.covidtracker.repositories.CovidDataRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CovidDataDownloader {

    private final String urlConfirmed = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/" +
            "csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private final String urlDeaths = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/" +
            "csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    CovidDataRepository covidDataRepository;

    public CovidDataDownloader(CovidDataRepository covidDataRepository) {
        this.covidDataRepository = covidDataRepository;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 1 * * *")
    public void downloadCovidData(){
        HttpResponse<String> confirmedResponse = getData(urlConfirmed);
        HttpResponse<String> deathsResponse = getData(urlDeaths);

        parseCsv(confirmedResponse.body(), deathsResponse.body());
    }


    private HttpResponse<String> getData(String url){

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void parseCsv(String confirmedCsv, String deathsCsv){
        StringReader confirmedStringReader = new StringReader(confirmedCsv);
        StringReader deathsStringReader = new StringReader(deathsCsv);

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();

        CSVParser confirmedRecords = null;
        CSVParser deathsRecords = null;
        try {
            confirmedRecords = csvFormat.parse(confirmedStringReader);
            deathsRecords = csvFormat.parse(deathsStringReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, CovidData> covidDataMap = new LinkedHashMap<>();

        LocalDate confirmedCasesLastUpdate = getLastUpdateDate(confirmedRecords);

        for (CSVRecord confirmedRecord : confirmedRecords) {
            CovidData covidData = CovidData.builder()
                    .state(confirmedRecord.get("Province/State"))
                    .country(confirmedRecord.get("Country/Region"))
                    .totalConfirmedCases(Integer.parseInt(confirmedRecord.get(confirmedRecord.size() - 1)))
                    .newConfirmedCases(Integer.parseInt(confirmedRecord.get(confirmedRecord.size() - 1))
                            - Integer.parseInt(confirmedRecord.get(confirmedRecord.size() - 2)))
                    .confirmedCasesLastUpdate(confirmedCasesLastUpdate)
                    .build();
            covidDataMap.put(covidData.getCountry(), covidData);
        }


        LocalDate deathsLastUpdate = getLastUpdateDate(deathsRecords);

        for (CSVRecord deathsRecord : deathsRecords) {
            CovidData covidData = covidDataMap.get(deathsRecord.get("Country/Region"));
            covidData.setTotalDeaths(Integer.parseInt(deathsRecord.get(deathsRecord.size() - 1)));
            covidData.setNewDeaths(Integer.parseInt(deathsRecord.get(deathsRecord.size() - 1))
                    - Integer.parseInt(deathsRecord.get(deathsRecord.size() - 2)));
            covidData.setDeathsLastUpdate(deathsLastUpdate);
        }


        covidDataRepository.setCovidDataList(new ArrayList<>(covidDataMap.values()));
    }

    private LocalDate getLastUpdateDate(CSVParser records){
        List<String> headerNames = records.getHeaderNames();
        String lastColumnHeader = headerNames.get(headerNames.size() - 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return LocalDate.parse(lastColumnHeader, formatter);
    }
}
