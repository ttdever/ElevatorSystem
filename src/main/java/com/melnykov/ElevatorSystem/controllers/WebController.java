package com.melnykov.ElevatorSystem.controllers;

import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    @Autowired
    ElevatorSystem elevatorSystem;

    @GetMapping("/")
    public String getElevatorControllerInfo(Model model) {
        model.addAttribute("elevatorController", elevatorSystem);
        return "index";
    }

    @PostMapping("/pick-up")
    public void pickUp() {

    }

    //End points to do:
    //Creating of ElevatorController with selected count of elevators
    //Visualization of ElevatorController and elevators
    //Prompt for requesting an elevator
    //Prompt for simulation (sending request ex. every 1 second)

}
