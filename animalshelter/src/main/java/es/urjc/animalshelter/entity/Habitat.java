package es.urjc.animalshelter.entity;

public class Habitat {
    private Long id;
    private String name;
    private String type;
    private int capacity;
    private boolean available;

    public Habitat() {
    }

    public Habitat(Long id, String name, String type, int capacity, boolean available) {
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
}
