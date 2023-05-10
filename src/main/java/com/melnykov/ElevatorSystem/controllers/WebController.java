package com.melnykov.ElevatorSystem.controllers;

import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {
    @Autowired
    private ElevatorSystem elevatorSystem;

    @GetMapping("/")
    public String getElevatorControllerInfo(Model model) {
        model.addAttribute("elevatorController", elevatorSystem);
        return "ElevatorSystem";
    }

    @GetMapping("/elevator/{id}")
    public String getElevatorInfo(Model model, @PathVariable("id") int id) {
        try {
            model.addAttribute("elevator", elevatorSystem.getElevators().get(id));
            return "Elevator";
        } catch (IndexOutOfBoundsException e) {
            model.addAttribute("errorMessage", "There is no elevator with id " + id);
            model.addAttribute("availableElevators", elevatorSystem.getElevators().size() + 1);
            return "ElevatorErrorPage";
        }
    }

    public void setElevatorSystem(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }
}
