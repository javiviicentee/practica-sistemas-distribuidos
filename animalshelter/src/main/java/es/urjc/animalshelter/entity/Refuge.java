package es.urjc.animalshelter.entity;

import java.util.ArrayList;
import java.util.List;

// clase entidad que representa fisicamente cada refugio o zona
public class Refuge {
    private Long id;
    private String name;
    private String type;
    private int capacity;
    private boolean available;

    // Relaciones
    // volunteer encargado en exclusiva de coordinar este refugio
    private Volunteer coordinator;
    // coleccion de animales asignados actualmente al refugio
    private List<Animal> animals = new ArrayList<>();

    public Refuge() {
        this.available = true;
    }

    public Refuge(Long id, String name, String type, int capacity, boolean available) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.available = available;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public Volunteer getCoordinator() { return coordinator; }
    public void setCoordinator(Volunteer coordinator) { this.coordinator = coordinator; }

    public List<Animal> getAnimals() { return animals; }
    public void setAnimals(List<Animal> animals) { this.animals = animals; }
}
