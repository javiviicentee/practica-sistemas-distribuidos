package es.urjc.animalshelter.restcontroller;

import es.urjc.animalshelter.entity.Animal;
import es.urjc.animalshelter.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/animals")
public class AnimalRestController {

    private final AnimalService animalService;

    public AnimalRestController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/")
    public Collection<Animal> getAllAnimals() {
        return animalService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable Long id) {
        Animal animal = animalService.findById(id);
        return (animal != null) ? ResponseEntity.ok(animal) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public Animal createAnimal(@RequestBody Animal animal) {
        animalService.save(animal);
        return animal;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal updatedAnimal) {
        if (animalService.findById(id) != null) {
            updatedAnimal.setId(id);
            animalService.save(updatedAnimal);
            return ResponseEntity.ok(updatedAnimal);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable Long id) {
        Animal removed = animalService.delete(id);
        return (removed != null) ? ResponseEntity.ok(removed) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Animal> patchAnimal(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Animal animal = animalService.findById(id);
        if (animal != null) {
            if (updates.containsKey("adopted")) {
                animal.setAdopted((Boolean) updates.get("adopted"));
            }
            if (updates.containsKey("name")) {
                animal.setName((String) updates.get("name"));
            }
            if (updates.containsKey("species")) {
                animal.setSpecies((String) updates.get("species"));
            }
            if (updates.containsKey("age")) {
                animal.setAge(((Number) updates.get("age")).intValue());
            }
            return ResponseEntity.ok(animal);
        }
        return ResponseEntity.notFound().build();
    }
}