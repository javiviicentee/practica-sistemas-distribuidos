package es.urjc.animalshelter.controller;

import es.urjc.animalshelter.entity.Habitat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class HabitatController {

    private Map<Long, Habitat> habitats = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public HabitatController() {
        long id1 = nextId.getAndIncrement();
        habitats.put(id1, new Habitat(id1, "Sabana", "Exterior", 10, true));

        long id2 = nextId.getAndIncrement();
        habitats.put(id2, new Habitat(id2, "Acuario Principal", "Acuático", 20, false));
    }

    @GetMapping("/habitats")
    public String showHabitats(Model model) {
        Collection<Habitat> habitatList = habitats.values();
        model.addAttribute("habitats", habitatList);
        return "habitats_list";
    }

    @GetMapping("/habitats/new")
    public String newHabitatForm(Model model) {
        model.addAttribute("habitat", new Habitat());
        return "habitat_form";
    }

    @PostMapping("/habitats/save")
    public String saveHabitat(@ModelAttribute Habitat habitat) {
        if (habitat.getId() == null) {
            habitat.setId(nextId.getAndIncrement());
        }
        habitats.put(habitat.getId(), habitat);
        return "redirect:/habitats"; 
    }

    @GetMapping("/habitats/edit/{id}")
    public String editHabitatForm(@PathVariable Long id, Model model) {
        Habitat habitat = habitats.get(id);
        if (habitat != null) {
            model.addAttribute("habitat", habitat);
            return "habitat_form";
        }
        return "redirect:/habitats";
    }

    @PostMapping("/habitats/delete/{id}")
    public String deleteHabitat(@PathVariable Long id) {
        habitats.remove(id);
        return "redirect:/habitats";
    }

    @PostMapping("/habitats/{id}/toggle-availability")
    public String toggleAvailability(@PathVariable Long id) {
        Habitat habitat = habitats.get(id);
        if (habitat != null) {
            habitat.setAvailable(!habitat.isAvailable());
        }
        return "redirect:/habitats";
    }
}
