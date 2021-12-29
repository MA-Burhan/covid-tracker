package maburhan.covidtracker.controllers;

import maburhan.covidtracker.model.LocationStats;
import maburhan.covidtracker.services.CovidDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.util.List;

@Controller
public class IndexController {

    CovidDataService covidDataService;

    public IndexController(CovidDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        List<LocationStats> allStats = covidDataService.getAllStats();
        int totalCases = covidDataService.getTotalNumberCases();
        String totalCasesString = NumberFormat.getInstance().format(totalCases);
        int totalNewCases = covidDataService.getTotalNumberNewCases();
        String totalNewCasesString = NumberFormat.getInstance().format(totalNewCases);

        model.addAttribute("stats",allStats);
        model.addAttribute("totalCases", totalCasesString);
        model.addAttribute("totalNewCases", totalNewCasesString);
        model.addAttribute("lastUpdateDate", covidDataService.getLastUpdateDate());

        return "index";
    }
}
