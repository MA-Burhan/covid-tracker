package maburhan.covidtracker.controllers;

import maburhan.covidtracker.model.LocationStats;
import maburhan.covidtracker.services.CovidDataService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;

abstract public class AbstractController {

    private CovidDataService covidDataService;

    public AbstractController(CovidDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    public void getStats(Model model, String type) {
        List<LocationStats> allStats = covidDataService.getStats(type);
        int totalCases = covidDataService.getTotalCases(type);
        String totalCasesString = NumberFormat.getInstance().format(totalCases);
        int totalNewCases = covidDataService.getTotalNewCases(type);
        String totalNewCasesString = NumberFormat.getInstance().format(totalNewCases);
        LocalDate latestUpdate = covidDataService.getLastUpdateDate(type);

        model.addAttribute("stats",allStats);
        model.addAttribute("totalCases", totalCasesString);
        model.addAttribute("totalNewCases", totalNewCasesString);
        model.addAttribute("lastUpdateDate", latestUpdate);
    }
}
