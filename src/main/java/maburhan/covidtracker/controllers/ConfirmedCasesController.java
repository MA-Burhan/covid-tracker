package maburhan.covidtracker.controllers;

import maburhan.covidtracker.services.CovidDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfirmedCasesController extends AbstractController{

    private String type = "confirmed";

    public ConfirmedCasesController(CovidDataService covidDataService) {
        super(covidDataService);
    }

    @GetMapping({"/", "/confirmed"})
    public String getConfirmedCases(Model model) {
        getStats(model, type);
        return "confirmed";
    }
}
