package es.urjc.animalshelter.service;

import es.urjc.animalshelter.entity.Refuge;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// servicio encargado de la logica de negocio de los refugios (persistencia en memoria)
@Service
public class RefugeService {
    // estructura de datos para simular una base de datos
    private Map<Long, Refuge> refuges = new ConcurrentHashMap<>();
    // generador auto-incremental de identificadores
    private AtomicLong nextId = new AtomicLong(1);

    public RefugeService() {
    }

    public Collection<Refuge> findAll() { return refuges.values(); }
    public Refuge findById(Long id) { return refuges.get(id); }
    public void save(Refuge refuge) {
        if (refuge.getId() == null) {
            refuge.setId(nextId.getAndIncrement());
        }
        refuges.put(refuge.getId(), refuge);
    }
    public Refuge delete(Long id) { return refuges.remove(id); }
}
