package es.urjc.animalshelter.controller;

import es.urjc.animalshelter.entity.Animal;
import es.urjc.animalshelter.entity.Refuge;
import es.urjc.animalshelter.service.AnimalService;
import es.urjc.animalshelter.service.RefugeService;
import es.urjc.animalshelter.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// controlador mvc para la gestion visual de los refugios
@Controller
public class RefugeController {

    private final RefugeService refugeService;
    private final AnimalService animalService;
    private final VolunteerService volunteerService;

    public RefugeController(RefugeService refugeService, AnimalService animalService, VolunteerService volunteerService) {
        this.refugeService = refugeService;
        this.animalService = animalService;
        this.volunteerService = volunteerService;
    }

    // carga y muestra el listado completo de refugios
    @GetMapping("/refuges")
    public String showRefuges(Model model) {
        model.addAttribute("refuges", refugeService.findAll());
        return "refuges_list";
    }

    // muestra el formulario vacio para añadir un nuevo elemento
    @GetMapping("/refuges/new")
    public String newRefugeForm(Model model) {
        model.addAttribute("refuge", new Refuge());
        model.addAttribute("volunteers", volunteerService.findAll());
        model.addAttribute("animals", animalService.findAll());
        return "refuge_form";
    }

    // procesa los datos del formulario y los guarda en memoria
    @PostMapping("/refuges/save")
    public String saveRefuge(@ModelAttribute Refuge refuge,
                              @RequestParam(required = false) Long coordinatorId,
                              @RequestParam(required = false) List<Long> animalIds) {
        
        if (coordinatorId != null) {
            refuge.setCoordinator(volunteerService.findById(coordinatorId));
        }

        if (animalIds != null) {
            List<Animal> selectedAnimals = new ArrayList<>();
            for (Long id : animalIds) {
                Animal a = animalService.findById(id);
                if (a != null) selectedAnimals.add(a);
            }
            refuge.setAnimals(selectedAnimals);
        }

        refugeService.save(refuge);
        return "redirect:/refuges";
    }

    @GetMapping("/refuges/edit/{id}")
    public String editRefugeForm(@PathVariable Long id, Model model) {
        Refuge refuge = refugeService.findById(id);
        if (refuge != null) {
            model.addAttribute("refuge", refuge);
            model.addAttribute("volunteers", volunteerService.findAll());
            model.addAttribute("animals", animalService.findAll());
            return "refuge_form";
        }
        return "redirect:/refuges";
    }

    @PostMapping("/refuges/delete/{id}")
    public String deleteRefuge(@PathVariable Long id) {
        refugeService.delete(id);
        return "redirect:/refuges";
    }

    @PostMapping("/refuges/{id}/toggle-availability")
    public String toggleAvailability(@PathVariable Long id) {
        Refuge refuge = refugeService.findById(id);
        if (refuge != null) {
            refuge.setAvailable(!refuge.isAvailable());
        }
        return "redirect:/refuges";
    }
}
