package es.urjc.animalshelter.restcontroller;

import es.urjc.animalshelter.entity.Volunteer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerRestController {

    private Map<Long, Volunteer> volunteers = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public VolunteerRestController() {
        //Initial mock data for API testing
        long id1 = nextId.getAndIncrement();
        volunteers.put(id1, new Volunteer(id1, "Laura Perez", "laura@api.com", "Limpieza", true));
    }

    @GetMapping("/")
    public Collection<Volunteer> getAllVolunteers() {
        return volunteers.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteer(@PathVariable Long id) {
        Volunteer volunteer = volunteers.get(id);
        return (volunteer != null) ? ResponseEntity.ok(volunteer) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public Volunteer createVolunteer(@RequestBody Volunteer volunteer) {
        volunteer.setId(nextId.getAndIncrement());
        volunteers.put(volunteer.getId(), volunteer);
        return volunteer;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(@PathVariable Long id, @RequestBody Volunteer updatedVolunteer) {
        if (volunteers.containsKey(id)) {
            updatedVolunteer.setId(id);
            volunteers.put(id, updatedVolunteer);
            return ResponseEntity.ok(updatedVolunteer);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Volunteer> deleteVolunteer(@PathVariable Long id) {
        Volunteer removed = volunteers.remove(id);
        return (removed != null) ? ResponseEntity.ok(removed) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Volunteer> patchVolunteer(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Volunteer volunteer = volunteers.get(id);
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