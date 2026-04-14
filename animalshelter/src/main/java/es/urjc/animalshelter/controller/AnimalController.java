package es.urjc.animalshelter.controller;

import es.urjc.animalshelter.entity.Animal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class AnimalController {

    private Map<Long, Animal> animals = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public AnimalController() {
        // Initial mock data in Spanish for the web UI
        long id1 = nextId.getAndIncrement();
        animals.put(id1, new Animal(id1, "Rex", "Perro", 3, false));

        long id2 = nextId.getAndIncrement();
        animals.put(id2, new Animal(id2, "Luna", "Gato", 2, true));
    }

    @GetMapping("/animals")
    public String showAnimals(Model model) {
        Collection<Animal> animalList = animals.values();
        model.addAttribute("animals", animalList);
        return "animals_list";
    }

    @GetMapping("/animals/new")
    public String newAnimalForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "animal_form";
    }

    @PostMapping("/animals/save")
    public String saveAnimal(@ModelAttribute Animal animal) {
        if (animal.getId() == null) {
            animal.setId(nextId.getAndIncrement());
        }
        animals.put(animal.getId(), animal);
        return "redirect:/animals"; 
    }

    @GetMapping("/animals/edit/{id}")
    public String editAnimalForm(@PathVariable Long id, Model model) {
        Animal animal = animals.get(id);
        if (animal != null) {
            model.addAttribute("animal", animal);
            return "animal_form";
        }
        return "redirect:/animals";
    }

    @PostMapping("/animals/delete/{id}")
    public String deleteAnimal(@PathVariable Long id) {
        animals.remove(id);
        return "redirect:/animals";
    }

    // Handles the equivalent of a PATCH request through standard HTML forms
    @PostMapping("/animals/{id}/toggle-adoption")
    public String toggleAdoptionStatus(@PathVariable Long id) {
        Animal animal = animals.get(id);
        if (animal != null) {
            animal.setAdopted(!animal.isAdopted());
        }
        return "redirect:/animals";
    }
}