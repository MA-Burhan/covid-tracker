package maburhan.covidtracker.controllers;

import maburhan.covidtracker.services.CovidDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeathsController extends AbstractController {

    private String type = "deaths";

    public DeathsController(CovidDataService covidDataService) {
        super(covidDataService);
    }

    @GetMapping("/deaths")
    public String getRecoveredCases(Model model) {
        getStats(model, type);
        return "deaths";
    }
}
