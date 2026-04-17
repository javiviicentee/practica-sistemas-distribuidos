package es.urjc.animalshelter.controller;

import es.urjc.animalshelter.entity.Animal;
import es.urjc.animalshelter.service.AnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/animals")
    public String showAnimals(Model model) {
        model.addAttribute("animals", animalService.findAll());
        return "animals_list";
    }

    @GetMapping("/animals/new")
    public String newAnimalForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "animal_form";
    }

    @PostMapping("/animals/save")
    public String saveAnimal(@ModelAttribute Animal animal) {
        animalService.save(animal);
        return "redirect:/animals"; 
    }

    @GetMapping("/animals/edit/{id}")
    public String editAnimalForm(@PathVariable Long id, Model model) {
        Animal animal = animalService.findById(id);
        if (animal != null) {
            model.addAttribute("animal", animal);
            return "animal_form";
        }
        return "redirect:/animals";
    }

    @PostMapping("/animals/delete/{id}")
    public String deleteAnimal(@PathVariable Long id) {
        animalService.delete(id);
        return "redirect:/animals";
    }

    @PostMapping("/animals/{id}/toggle-adoption")
    public String toggleAdoptionStatus(@PathVariable Long id) {
        Animal animal = animalService.findById(id);
        if (animal != null) {
            animal.setAdopted(!animal.isAdopted());
        }
        return "redirect:/animals";
    }
}