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

    // We use another map to temporarily store API data
    private Map<Long, Animal> animals = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public AnimalRestController() {
        // Add a test animal exclusively for the API
        long id1 = nextId.getAndIncrement();
        animals.put(id1, new Animal(id1, "Toby", "Perro", 5, false));
    }

    // 1. READ ALL (GET)
    @GetMapping("/")
    public Collection<Animal> getAllAnimals() {
        return animals.values();
    }

    // 2. READ ONE (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable Long id) {
        Animal animal = animals.get(id);
        if (animal != null) {
            return ResponseEntity.ok(animal);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if it doesn't exist
        }
    }

    // 3. CREATE (POST)
    @PostMapping("/")
    public Animal createAnimal(@RequestBody Animal animal) {
        animal.setId(nextId.getAndIncrement());
        animals.put(animal.getId(), animal);
        return animal;
    }

    // 4. UPDATE ALL (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal updatedAnimal) {
        if (animals.containsKey(id)) {
            updatedAnimal.setId(id);
            animals.put(id, updatedAnimal);
            return ResponseEntity.ok(updatedAnimal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable Long id) {
        Animal removed = animals.remove(id);
        if (removed != null) {
            return ResponseEntity.ok(removed);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. PARTIAL UPDATE (PATCH) - Rubric requirement
    @PatchMapping("/{id}")
    public ResponseEntity<Animal> patchAnimal(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Animal animal = animals.get(id);
        if (animal != null) {
            // If the JSON contains the "adopted" field, update it
            if (updates.containsKey("adopted")) {
                animal.setAdopted((Boolean) updates.get("adopted"));
            }
            return ResponseEntity.ok(animal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}