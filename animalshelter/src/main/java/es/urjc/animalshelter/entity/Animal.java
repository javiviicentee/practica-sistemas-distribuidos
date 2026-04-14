package es.urjc.animalshelter.entity;

public class Animal {
    private Long id;
    private String name;
    private String species;
    private int age;
    private boolean adopted;

    // Default constructor
    public Animal() {
    }

    // Constructor with parameters
    public Animal(Long id, String name, String species, int age, boolean adopted) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
        this.adopted = adopted;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isAdopted() { return adopted; }
    public void setAdopted(boolean adopted) { this.adopted = adopted; }
}