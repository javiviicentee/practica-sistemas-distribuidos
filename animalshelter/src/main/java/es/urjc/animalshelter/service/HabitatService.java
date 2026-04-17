package es.urjc.animalshelter.service;

import es.urjc.animalshelter.entity.Habitat;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class HabitatService {
    private Map<Long, Habitat> habitats = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public HabitatService() {
        // Inicializaremos un hábitat de prueba desde el Controller para poder enlazarle un coordinador si queremos
    }

    public Collection<Habitat> findAll() { return habitats.values(); }
    public Habitat findById(Long id) { return habitats.get(id); }
    public void save(Habitat habitat) {
        if (habitat.getId() == null) {
            habitat.setId(nextId.getAndIncrement());
        }
        habitats.put(habitat.getId(), habitat);
    }
    public Habitat delete(Long id) { return habitats.remove(id); }
}
