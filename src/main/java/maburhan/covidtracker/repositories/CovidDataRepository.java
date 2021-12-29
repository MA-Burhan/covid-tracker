package maburhan.covidtracker.repositories;

import maburhan.covidtracker.model.LocationStats;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CovidDataRepository {

    private final String url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/" +
            "csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> locationStatsList = new ArrayList<>();
    private LocalDate lastUpdateDate;

    @PostConstruct
    @Scheduled(cron = "0 0 1 * * *")
    public void processCovidData(){

        HttpResponse<String> response = getData();

        List<LocationStats> locationStats = parseCsv(response.body());

        this.locationStatsList = locationStats;
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

    private List<LocationStats> parseCsv(String csv){
        StringReader stringReader = new StringReader(csv);
        List<LocationStats> newStats = new ArrayList<>();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();
        CSVParser records = null;
        try {
            records = csvFormat.parse(stringReader);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (CSVRecord record : records) {
            LocationStats locationStats = LocationStats.builder()
                    .state(record.get("Province/State"))
                    .country(record.get("Country/Region"))
                    .totalNumOfCasesLatest(Integer.parseInt(record.get(record.size() - 1)))
                    .totalNumOfCasesPrevDay(Integer.parseInt(record.get(record.size() - 2)))
                    .build();

            newStats.add(locationStats);
        }

        //Set last update date
        List<String> headerNames =records.getHeaderNames();
        String lastColumnHeader = headerNames.get(headerNames.size() - 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        lastUpdateDate = LocalDate.parse(lastColumnHeader, formatter);

        return newStats;
    }

    public List<LocationStats> getLocationStatsList() {
        return locationStatsList;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }
}
