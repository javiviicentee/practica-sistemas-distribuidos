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
        long id1 = nextId.getAndIncrement();
        animals.put(id1, new Animal(id1, "Rex", "Dog", 3, false));

        long id2 = nextId.getAndIncrement();
        animals.put(id2, new Animal(id2, "Luna", "Cat", 2, true));
    }

    // 1. Mostrar todos los animales (READ)
    @GetMapping("/animals")
    public String showAnimals(Model model) {
        Collection<Animal> animalList = animals.values();
        model.addAttribute("animals", animalList);
        return "animals_list";
    }

    // 2. Mostrar el formulario para crear un animal nuevo (CREATE - paso 1)
    @GetMapping("/animals/new")
    public String newAnimalForm(Model model) {
        model.addAttribute("animal", new Animal()); // Mandamos un animal vacío al formulario
        return "animal_form";
    }

    // 3. Guardar el animal del formulario (CREATE y UPDATE - paso 2)
    @PostMapping("/animals/save")
    public String saveAnimal(@ModelAttribute Animal animal) {
        if (animal.getId() == null) {
            // Es un animal nuevo, le damos un ID
            animal.setId(nextId.getAndIncrement());
        }
        // Guardamos o actualizamos en el mapa
        animals.put(animal.getId(), animal);
        
        // Redirigimos a la lista para ver los cambios
        return "redirect:/animals"; 
    }

    // 4. Mostrar el formulario con los datos de un animal existente (UPDATE - paso 1)
    @GetMapping("/animals/edit/{id}")
    public String editAnimalForm(@PathVariable Long id, Model model) {
        Animal animal = animals.get(id);
        if (animal != null) {
            model.addAttribute("animal", animal);
            return "animal_form";
        }
        return "redirect:/animals";
    }

    // 5. Borrar un animal (DELETE)
    @PostMapping("/animals/delete/{id}")
    public String deleteAnimal(@PathVariable Long id) {
        animals.remove(id);
        return "redirect:/animals";
    }
}