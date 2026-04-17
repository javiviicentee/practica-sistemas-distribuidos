package es.urjc.animalshelter.controller;

import es.urjc.animalshelter.entity.Volunteer;
import es.urjc.animalshelter.service.VolunteerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("/volunteers")
    public String showVolunteers(Model model) {
        model.addAttribute("volunteers", volunteerService.findAll());
        return "volunteers_list";
    }

    @GetMapping("/volunteers/new")
    public String newVolunteerForm(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        return "volunteers_form";
    }

    @PostMapping("/volunteers/save")
    public String saveVolunteer(@ModelAttribute Volunteer volunteer) {
        volunteerService.save(volunteer);
        return "redirect:/volunteers"; 
    }

    @GetMapping("/volunteers/edit/{id}")
    public String editVolunteerForm(@PathVariable Long id, Model model) {
        Volunteer volunteer = volunteerService.findById(id);
        if (volunteer != null) {
            model.addAttribute("volunteer", volunteer);
            return "volunteers_form";
        }
        return "redirect:/volunteers";
    }

    @PostMapping("/volunteers/delete/{id}")
    public String deleteVolunteer(@PathVariable Long id) {
        volunteerService.delete(id);
        return "redirect:/volunteers";
    }

    @PostMapping("/volunteers/{id}/toggle-status")
    public String toggleActiveStatus(@PathVariable Long id) {
        Volunteer volunteer = volunteerService.findById(id);
        if (volunteer != null) {
            volunteer.setActive(!volunteer.isActive());
        }
        return "redirect:/volunteers";
    }
}