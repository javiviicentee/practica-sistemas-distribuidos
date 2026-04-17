package es.urjc.animalshelter.restcontroller;

import es.urjc.animalshelter.entity.Habitat;
import es.urjc.animalshelter.service.HabitatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/habitats")
public class HabitatRestController {

    private final HabitatService habitatService;

    public HabitatRestController(HabitatService habitatService) {
        this.habitatService = habitatService;
    }

    @GetMapping("/")
    public Collection<Habitat> getAllHabitats() {
        return habitatService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitat> getHabitat(@PathVariable Long id) {
        Habitat habitat = habitatService.findById(id);
        return (habitat != null) ? ResponseEntity.ok(habitat) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public Habitat createHabitat(@RequestBody Habitat habitat) {
        habitatService.save(habitat);
        return habitat;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habitat> updateHabitat(@PathVariable Long id, @RequestBody Habitat updatedHabitat) {
        if (habitatService.findById(id) != null) {
            updatedHabitat.setId(id);
            habitatService.save(updatedHabitat);
            return ResponseEntity.ok(updatedHabitat);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Habitat> deleteHabitat(@PathVariable Long id) {
        Habitat removed = habitatService.delete(id);
        return (removed != null) ? ResponseEntity.ok(removed) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Habitat> patchHabitat(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Habitat habitat = habitatService.findById(id);
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
