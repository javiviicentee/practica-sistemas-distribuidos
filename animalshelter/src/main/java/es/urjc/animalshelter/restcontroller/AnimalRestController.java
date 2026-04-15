package es.urjc.animalshelter.restcontroller;

import es.urjc.animalshelter.entity.Animal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/animals")
public class AnimalRestController {

    private Map<Long, Animal> animals = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public AnimalRestController() {
        //Initial mock data for API testing
        long id1 = nextId.getAndIncrement();
        animals.put(id1, new Animal(id1, "Toby", "Perro", 5, false));
    }

    @GetMapping("/")
    public Collection<Animal> getAllAnimals() {
        return animals.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable Long id) {
        Animal animal = animals.get(id);
        return (animal != null) ? ResponseEntity.ok(animal) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public Animal createAnimal(@RequestBody Animal animal) {
        animal.setId(nextId.getAndIncrement());
        animals.put(animal.getId(), animal);
        return animal;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal updatedAnimal) {
        if (animals.containsKey(id)) {
            updatedAnimal.setId(id);
            animals.put(id, updatedAnimal);
            return ResponseEntity.ok(updatedAnimal);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable Long id) {
        Animal removed = animals.remove(id);
        return (removed != null) ? ResponseEntity.ok(removed) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Animal> patchAnimal(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Animal animal = animals.get(id);
        if (animal != null) {
            if (updates.containsKey("adopted")) {
                animal.setAdopted((Boolean) updates.get("adopted"));
            }
            return ResponseEntity.ok(animal);
        }
        return ResponseEntity.notFound().build();
    }
}