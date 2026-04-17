package es.urjc.animalshelter.restcontroller;

import es.urjc.animalshelter.entity.Habitat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/habitats")
public class HabitatRestController {

    private Map<Long, Habitat> habitats = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public HabitatRestController() {
        long id1 = nextId.getAndIncrement();
        habitats.put(id1, new Habitat(id1, "Sabana", "Exterior", 10, true));
    }

    @GetMapping("/")
    public Collection<Habitat> getAllHabitats() {
        return habitats.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitat> getHabitat(@PathVariable Long id) {
        Habitat habitat = habitats.get(id);
        return (habitat != null) ? ResponseEntity.ok(habitat) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public Habitat createHabitat(@RequestBody Habitat habitat) {
        habitat.setId(nextId.getAndIncrement());
        habitats.put(habitat.getId(), habitat);
        return habitat;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habitat> updateHabitat(@PathVariable Long id, @RequestBody Habitat updatedHabitat) {
        if (habitats.containsKey(id)) {
            updatedHabitat.setId(id);
            habitats.put(id, updatedHabitat);
            return ResponseEntity.ok(updatedHabitat);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Habitat> deleteHabitat(@PathVariable Long id) {
        Habitat removed = habitats.remove(id);
        return (removed != null) ? ResponseEntity.ok(removed) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Habitat> patchHabitat(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Habitat habitat = habitats.get(id);
        if (habitat != null) {
            if (updates.containsKey("available")) {
                habitat.setAvailable((Boolean) updates.get("available"));
            }
            if (updates.containsKey("name")) {
                habitat.setName((String) updates.get("name"));
            }
            if (updates.containsKey("type")) {
                habitat.setType((String) updates.get("type"));
            }
            if (updates.containsKey("capacity")) {
                habitat.setCapacity(((Number) updates.get("capacity")).intValue());
            }
            return ResponseEntity.ok(habitat);
        }
        return ResponseEntity.notFound().build();
    }
}
