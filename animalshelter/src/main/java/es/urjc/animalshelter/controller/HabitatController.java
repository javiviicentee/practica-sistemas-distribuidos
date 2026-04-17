package es.urjc.animalshelter.controller;

import es.urjc.animalshelter.entity.Animal;
import es.urjc.animalshelter.entity.Habitat;
import es.urjc.animalshelter.service.AnimalService;
import es.urjc.animalshelter.service.HabitatService;
import es.urjc.animalshelter.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HabitatController {

    private final HabitatService habitatService;
    private final AnimalService animalService;
    private final VolunteerService volunteerService;

    public HabitatController(HabitatService habitatService, AnimalService animalService, VolunteerService volunteerService) {
        this.habitatService = habitatService;
        this.animalService = animalService;
        this.volunteerService = volunteerService;
    }

    @GetMapping("/habitats")
    public String showHabitats(Model model) {
        model.addAttribute("habitats", habitatService.findAll());
        return "habitats_list";
    }

    @GetMapping("/habitats/new")
    public String newHabitatForm(Model model) {
        model.addAttribute("habitat", new Habitat());
        model.addAttribute("volunteers", volunteerService.findAll());
        model.addAttribute("animals", animalService.findAll());
        return "habitat_form";
    }

    @PostMapping("/habitats/save")
    public String saveHabitat(@ModelAttribute Habitat habitat,
                              @RequestParam(required = false) Long coordinatorId,
                              @RequestParam(required = false) List<Long> animalIds) {
        
        if (coordinatorId != null) {
            habitat.setCoordinator(volunteerService.findById(coordinatorId));
        }

        if (animalIds != null) {
            List<Animal> selectedAnimals = new ArrayList<>();
            for (Long id : animalIds) {
                Animal a = animalService.findById(id);
                if (a != null) selectedAnimals.add(a);
            }
            habitat.setAnimals(selectedAnimals);
        }

        habitatService.save(habitat);
        return "redirect:/habitats";
    }

    @GetMapping("/habitats/edit/{id}")
    public String editHabitatForm(@PathVariable Long id, Model model) {
        Habitat habitat = habitatService.findById(id);
        if (habitat != null) {
            model.addAttribute("habitat", habitat);
            model.addAttribute("volunteers", volunteerService.findAll());
            model.addAttribute("animals", animalService.findAll());
            return "habitat_form";
        }
        return "redirect:/habitats";
    }

    @PostMapping("/habitats/delete/{id}")
    public String deleteHabitat(@PathVariable Long id) {
        habitatService.delete(id);
        return "redirect:/habitats";
    }

    @PostMapping("/habitats/{id}/toggle-availability")
    public String toggleAvailability(@PathVariable Long id) {
        Habitat habitat = habitatService.findById(id);
        if (habitat != null) {
            habitat.setAvailable(!habitat.isAvailable());
        }
        return "redirect:/habitats";
    }
}
