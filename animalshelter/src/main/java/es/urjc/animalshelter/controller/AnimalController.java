package es.urjc.animalshelter.controller;

import es.urjc.animalshelter.entity.Animal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class AnimalController {

    // We use a Map as recommended for volatile, non-persistent storage
    private Map<Long, Animal> animals = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    // Constructor: Let's add some initial dummy data to test the web
    public AnimalController() {
        long id1 = nextId.getAndIncrement();
        animals.put(id1, new Animal(id1, "Rex", "Dog", 3, false));

        long id2 = nextId.getAndIncrement();
        animals.put(id2, new Animal(id2, "Luna", "Cat", 2, true));
    }

    // Read operation (GET all animals)
    @GetMapping("/animals")
    public String showAnimals(Model model) {
        Collection<Animal> animalList = animals.values();
        model.addAttribute("animals", animalList);
        return "animals_list"; // This is the HTML template we will create later
    }
}