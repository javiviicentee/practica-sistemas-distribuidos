package es.urjc.animalshelter.controller;

import es.urjc.animalshelter.service.AnimalService;
import es.urjc.animalshelter.service.RefugeService;
import es.urjc.animalshelter.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// controlador principal para la pagina de inicio del refugio
@Controller
public class HomeController {

    private final AnimalService animalService;
    private final VolunteerService volunteerService;
    private final RefugeService refugeService;

    public HomeController(AnimalService animalService, VolunteerService volunteerService, RefugeService refugeService) {
        this.animalService = animalService;
        this.volunteerService = volunteerService;
        this.refugeService = refugeService;
    }

    // peticion a la raiz, recoge los contadores de los servicios y los manda a la vista
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("animalsCount", animalService.findAll().size());
        model.addAttribute("volunteersCount", volunteerService.findAll().size());
        model.addAttribute("refugesCount", refugeService.findAll().size());
        return "index";
    }
}
