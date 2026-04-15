package es.urjc.animalshelter.controller;

import es.urjc.animalshelter.entity.Volunteer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class VolunteerController {

    private Map<Long, Volunteer> volunteers = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(1);

    public VolunteerController() {
        //Sample data for the web interface
        long id1 = nextId.getAndIncrement();
        volunteers.put(id1, new Volunteer(id1, "Ana Garcia", "ana@refugio.com", "Paseador", true));

        long id2 = nextId.getAndIncrement();
        volunteers.put(id2, new Volunteer(id2, "Carlos Ruiz", "carlos@refugio.com", "Veterinario", false));
    }

    @GetMapping("/volunteers")
    public String showVolunteers(Model model) {
        Collection<Volunteer> volunteerList = volunteers.values();
        model.addAttribute("volunteers", volunteerList);
        return "volunteers_list";
    }

    @GetMapping("/volunteers/new")
    public String newVolunteerForm(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        return "volunteer_form";
    }

    @PostMapping("/volunteers/save")
    public String saveVolunteer(@ModelAttribute Volunteer volunteer) {
        if (volunteer.getId() == null) {
            volunteer.setId(nextId.getAndIncrement());
        }
        volunteers.put(volunteer.getId(), volunteer);
        return "redirect:/volunteers"; 
    }

    @GetMapping("/volunteers/edit/{id}")
    public String editVolunteerForm(@PathVariable Long id, Model model) {
        Volunteer volunteer = volunteers.get(id);
        if (volunteer != null) {
            model.addAttribute("volunteer", volunteer);
            return "volunteer_form";
        }
        return "redirect:/volunteers";
    }

    @PostMapping("/volunteers/delete/{id}")
    public String deleteVolunteer(@PathVariable Long id) {
        volunteers.remove(id);
        return "redirect:/volunteers";
    }

    @PostMapping("/volunteers/{id}/toggle-status")
    public String toggleVolunteerStatus(@PathVariable Long id) {
        Volunteer volunteer = volunteers.get(id);
        if (volunteer != null) {
            volunteer.setActive(!volunteer.isActive());
        }
        return "redirect:/volunteers";
    }
}