package maburhan.covidtracker.repositories;

import maburhan.covidtracker.model.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;

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
public class LocationStatsRepository {

    public Object[] processStats(String url){

        HttpResponse<String> response = getData(url);

        return parseCsv(response.body());
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

    private Object[] parseCsv(String csv){
        StringReader stringReader = new StringReader(csv);
        Object[] statsArray = new Object[2];

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();
        CSVParser records = null;
        try {
            records = csvFormat.parse(stringReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<LocationStats> statsList = new ArrayList<>();
        for (CSVRecord record : records) {
            LocationStats locationStats = LocationStats.builder()
                    .state(record.get("Province/State"))
                    .country(record.get("Country/Region"))
                    .total(Integer.parseInt(record.get(record.size() - 1)))
                    .increase(Integer.parseInt(record.get(record.size() - 1))
                            - Integer.parseInt(record.get(record.size() - 2)))
                    .build();

            System.out.println(locationStats);
            statsList.add(locationStats);
        }

        //Set last update date
        List<String> headerNames =records.getHeaderNames();
        String lastColumnHeader = headerNames.get(headerNames.size() - 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        LocalDate lastUpdateDate = LocalDate.parse(lastColumnHeader, formatter);

        statsArray[0] = statsList;
        statsArray[1] = lastUpdateDate;
        return statsArray;
    }

}
