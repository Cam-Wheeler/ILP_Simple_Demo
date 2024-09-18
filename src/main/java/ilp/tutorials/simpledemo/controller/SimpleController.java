package ilp.tutorials.simpledemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/isAlive")
    public boolean isAlive() {
        return true;
    }

    @GetMapping("/studentId/{name}")
    public String studentId(@PathVariable String name) {
        if (name.equalsIgnoreCase("Cameron")) {
            return "Cameron";
        }
        else {
            return "We do not have your name in our register, sorry!";
        }
    }
}
