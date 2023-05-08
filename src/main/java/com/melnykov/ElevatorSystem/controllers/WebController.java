package com.melnykov.ElevatorSystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    //End points to do:
    //Creating of ElevatorController with selected count of elevators
    //Visualization of ElevatorController and elevators
    //Prompt for requesting an elevator
    //Prompt for simulation (sending request ex. every 1 second)

}
