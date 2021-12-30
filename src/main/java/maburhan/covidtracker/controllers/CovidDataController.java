package maburhan.covidtracker.controllers;

import maburhan.covidtracker.model.CovidData;
import maburhan.covidtracker.services.CovidDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CovidDataController{

    CovidDataService covidDataService;

    public CovidDataController(CovidDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    @GetMapping({"/", "/confirmed"})
    public String getConfirmedCasesStats(Model model) {
        List<CovidData> covidDataList = covidDataService.getCovidData();
        int totalConfirmedCasesGlobal = covidDataService.getTotalConfirmedCasesGlobal();
        String totalConfirmedCasesGlobalString = NumberFormat.getInstance().format(totalConfirmedCasesGlobal);
        int newConfirmedCasesGlobal = covidDataService.getNewConfirmedCasesGlobal();
        String newConfirmedCasesGlobalString = NumberFormat.getInstance().format(newConfirmedCasesGlobal);
        LocalDate latestUpdateConfirmedCases = covidDataService.getLastUpdateConfirmedCases();

        addToModel(model, covidDataList, totalConfirmedCasesGlobalString, newConfirmedCasesGlobalString,
                latestUpdateConfirmedCases);

        return "confirmed";
    }

    @GetMapping("/deaths")
    public String getDeathStats(Model model) {
        List<CovidData> covidDataList = covidDataService.getCovidData();

        int totalDeathsGlobal = covidDataService.getTotalDeathsGlobal();
        String totalDeathsGlobalString = NumberFormat.getInstance().format(totalDeathsGlobal);
        int newDeathsGlobal = covidDataService.getNewDeathsGlobal();
        String newDeathsGlobalString = NumberFormat.getInstance().format(newDeathsGlobal);
        LocalDate latestUpdateDeaths = covidDataService.getLastUpdateDeaths();

        addToModel(model, covidDataList, totalDeathsGlobalString, newDeathsGlobalString, latestUpdateDeaths);

        return "deaths";
    }

    private void addToModel(Model model, List<CovidData> stats, String totalCases, String totalNewCases,
                            LocalDate lastUpdateDate){
        model.addAttribute("stats", stats);
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases );
        model.addAttribute("lastUpdateDate", lastUpdateDate);
    }
}
