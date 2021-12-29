package maburhan.covidtracker.controllers;

import maburhan.covidtracker.model.LocationConfirmedCases;
import maburhan.covidtracker.services.LocationConfirmedCasesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.util.List;

@Controller
public class LocationConfirmedCasesController {

    LocationConfirmedCasesService locationConfirmedCasesService;

    public LocationConfirmedCasesController(LocationConfirmedCasesService locationConfirmedCasesService) {
        this.locationConfirmedCasesService = locationConfirmedCasesService;
    }

    @GetMapping("/")
    public String getConfirmedCases(Model model) {
        List<LocationConfirmedCases> allStats = locationConfirmedCasesService.getAllStats();
        int totalCases = locationConfirmedCasesService.getTotalNumberCases();
        String totalCasesString = NumberFormat.getInstance().format(totalCases);
        int totalNewCases = locationConfirmedCasesService.getTotalNumberNewCases();
        String totalNewCasesString = NumberFormat.getInstance().format(totalNewCases);

        model.addAttribute("stats",allStats);
        model.addAttribute("totalCases", totalCasesString);
        model.addAttribute("totalNewCases", totalNewCasesString);
        model.addAttribute("lastUpdateDate", locationConfirmedCasesService.getLastUpdateDate());

        return "index";
    }
}
