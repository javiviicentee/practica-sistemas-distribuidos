package es.urjc.animalshelter.service;

import es.urjc.animalshelter.entity.Volunteer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VolunteerService {
    private Map<Long, Volunteer> volunteers = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public VolunteerService() {
    }

    public Collection<Volunteer> findAll() { return volunteers.values(); }
    public Volunteer findById(Long id) { return volunteers.get(id); }
    public void save(Volunteer volunteer) {
        if (volunteer.getId() == null) {
            volunteer.setId(nextId.getAndIncrement());
        }
        volunteers.put(volunteer.getId(), volunteer);
    }
    public Volunteer delete(Long id) { return volunteers.remove(id); }
}
