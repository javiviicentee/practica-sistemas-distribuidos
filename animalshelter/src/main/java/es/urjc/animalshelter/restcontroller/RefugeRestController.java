package es.urjc.animalshelter.restcontroller;

import es.urjc.animalshelter.entity.Refuge;
import es.urjc.animalshelter.service.RefugeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

// controlador rest para pruebas y consumo en formato json
@RestController
@RequestMapping("/api/refuges")
public class RefugeRestController {

    private final RefugeService refugeService;

    public RefugeRestController(RefugeService refugeService) {
        this.refugeService = refugeService;
    }

    @GetMapping("/")
    public Collection<Refuge> getAllRefuges() {
        return refugeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Refuge> getRefuge(@PathVariable Long id) {
        Refuge refuge = refugeService.findById(id);
        return (refuge != null) ? ResponseEntity.ok(refuge) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public Refuge createRefuge(@RequestBody Refuge refuge) {
        refugeService.save(refuge);
        return refuge;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Refuge> updateRefuge(@PathVariable Long id, @RequestBody Refuge updatedRefuge) {
        if (refugeService.findById(id) != null) {
            updatedRefuge.setId(id);
            refugeService.save(updatedRefuge);
            return ResponseEntity.ok(updatedRefuge);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Refuge> deleteRefuge(@PathVariable Long id) {
        Refuge removed = refugeService.delete(id);
        return (removed != null) ? ResponseEntity.ok(removed) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Refuge> patchRefuge(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Refuge refuge = refugeService.findById(id);
        if (refuge != null) {
            if (updates.containsKey("available")) {
                refuge.setAvailable((Boolean) updates.get("available"));
            }
            if (updates.containsKey("name")) {
                refuge.setName((String) updates.get("name"));
            }
            if (updates.containsKey("type")) {
                refuge.setType((String) updates.get("type"));
            }
            if (updates.containsKey("capacity")) {
                refuge.setCapacity(((Number) updates.get("capacity")).intValue());
            }
            return ResponseEntity.ok(refuge);
        }
        return ResponseEntity.notFound().build();
    }
}
