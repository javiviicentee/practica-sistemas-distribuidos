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
        long id1 = nextId.getAndIncrement();
        animals.put(id1, new Animal(id1, "Rex", "Perro", 3, false, java.time.LocalDate.now().minusDays(5)));

        long id2 = nextId.getAndIncrement();
        animals.put(id2, new Animal(id2, "Luna", "Gato", 2, true, java.time.LocalDate.now().minusDays(2)));
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
