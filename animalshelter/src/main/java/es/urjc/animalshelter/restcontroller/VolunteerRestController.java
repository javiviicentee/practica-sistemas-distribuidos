package es.urjc.animalshelter.restcontroller;

import es.urjc.animalshelter.entity.Volunteer;
import es.urjc.animalshelter.service.VolunteerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerRestController {

    private final VolunteerService volunteerService;

    public VolunteerRestController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("/")
    public Collection<Volunteer> getAllVolunteers() {
        return volunteerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteer(@PathVariable Long id) {
        Volunteer volunteer = volunteerService.findById(id);
        return (volunteer != null) ? ResponseEntity.ok(volunteer) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public Volunteer createVolunteer(@RequestBody Volunteer volunteer) {
        volunteerService.save(volunteer);
        return volunteer;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(@PathVariable Long id, @RequestBody Volunteer updatedVolunteer) {
        if (volunteerService.findById(id) != null) {
            updatedVolunteer.setId(id);
            volunteerService.save(updatedVolunteer);
            return ResponseEntity.ok(updatedVolunteer);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Volunteer> deleteVolunteer(@PathVariable Long id) {
        Volunteer removed = volunteerService.delete(id);
        return (removed != null) ? ResponseEntity.ok(removed) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Volunteer> patchVolunteer(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Volunteer volunteer = volunteerService.findById(id);
        if (volunteer != null) {
            if (updates.containsKey("active")) {
                volunteer.setActive((Boolean) updates.get("active"));
            }
            if (updates.containsKey("name")) {
                volunteer.setName((String) updates.get("name"));
            }
            if (updates.containsKey("email")) {
                volunteer.setEmail((String) updates.get("email"));
            }
            if (updates.containsKey("role")) {
                volunteer.setRole((String) updates.get("role"));
            }
            return ResponseEntity.ok(volunteer);
        }
        return ResponseEntity.notFound().build();
    }
}