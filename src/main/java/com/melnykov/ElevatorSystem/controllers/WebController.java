package com.melnykov.ElevatorSystem.controllers;

import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @Autowired
    private ElevatorSystem elevatorSystem;

    @GetMapping("/")
    public String getElevatorControllerInfo(Model model) {
        model.addAttribute("elevatorController", elevatorSystem);
        return "ElevatorSystem";
    }

    public void setElevatorSystem(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }
}
