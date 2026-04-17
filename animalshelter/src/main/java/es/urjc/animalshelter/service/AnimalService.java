package es.urjc.animalshelter.service;

import es.urjc.animalshelter.entity.Animal;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AnimalService {
    private Map<Long, Animal> animals = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public AnimalService() {
    }

    public Collection<Animal> findAll() { return animals.values(); }
    public Animal findById(Long id) { return animals.get(id); }
    public void save(Animal animal) {
        if (animal.getId() == null) {
            animal.setId(nextId.getAndIncrement());
        }
        animals.put(animal.getId(), animal);
    }
    public Animal delete(Long id) { return animals.remove(id); }
}
