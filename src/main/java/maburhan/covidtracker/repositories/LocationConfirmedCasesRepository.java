package maburhan.covidtracker.repositories;

import maburhan.covidtracker.model.LocationConfirmedCases;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

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
import java.util.List;

@Repository
public class LocationConfirmedCasesRepository {

    private final String url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/" +
            "csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationConfirmedCases> locationConfirmedCasesList = new ArrayList<>();
    private LocalDate lastUpdateDate;

    @PostConstruct
    @Scheduled(cron = "0 0 1 * * *")
    public void processCovidData(){

        HttpResponse<String> response = getData();

        List<LocationConfirmedCases> locationConfirmedCases = parseCsv(response.body());

        //TODO make this thread safe
        this.locationConfirmedCasesList = locationConfirmedCases;
    }

    private HttpResponse<String> getData(){

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

    private List<LocationConfirmedCases> parseCsv(String csv){
        StringReader stringReader = new StringReader(csv);
        List<LocationConfirmedCases> newStats = new ArrayList<>();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();
        CSVParser records = null;
        try {
            records = csvFormat.parse(stringReader);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (CSVRecord record : records) {
            LocationConfirmedCases locationConfirmedCases = LocationConfirmedCases.builder()
                    .state(record.get("Province/State"))
                    .country(record.get("Country/Region"))
                    .total(Integer.parseInt(record.get(record.size() - 1)))
                    .increase(Integer.parseInt(record.get(record.size() - 1))
                            - Integer.parseInt(record.get(record.size() - 2)))
                    .build();

            newStats.add(locationConfirmedCases);
        }

        //Set last update date
        List<String> headerNames =records.getHeaderNames();
        String lastColumnHeader = headerNames.get(headerNames.size() - 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        lastUpdateDate = LocalDate.parse(lastColumnHeader, formatter);

        return newStats;
    }

    public List<LocationConfirmedCases> getLocationConfimedCasesList() {
        return locationConfirmedCasesList;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }
}
