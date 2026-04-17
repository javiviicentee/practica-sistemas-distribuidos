package es.urjc.animalshelter.entity;

import java.time.LocalDate;

// entidad principal del sistema, representa a un animal rescatado
public class Animal {
    private Long id;
    private String name;
    private Species species;
    private String customSpecies;
    private int age;
    private boolean adopted;
    private LocalDate entryDate;

    public Animal() {
        // fecha de registro por defecto al crear el animal
        this.entryDate = LocalDate.now();
    }

    public Animal(Long id, String name, Species species, String customSpecies, int age, boolean adopted, LocalDate entryDate) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.customSpecies = customSpecies;
        this.age = age;
        this.adopted = adopted;
        this.entryDate = entryDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Species getSpecies() { return species; }
    public void setSpecies(Species species) { this.species = species; }

    public String getCustomSpecies() { return customSpecies; }
    public void setCustomSpecies(String customSpecies) { this.customSpecies = customSpecies; }

    // metodo auxiliar para mostrar la especie real si seleccionaron 'otro'
    public String getDisplaySpecies() {
        if (species == Species.OTRO && customSpecies != null && !customSpecies.isEmpty()) {
            return customSpecies;
        }
        return species != null ? species.name() : "Desconocido";
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isAdopted() { return adopted; }
    public void setAdopted(boolean adopted) { this.adopted = adopted; }

    public LocalDate getEntryDate() { return entryDate; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }
}