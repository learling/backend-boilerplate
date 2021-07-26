package de.boilerplate.springbootbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:3000", "https://main.d26991zspaafgq.amplifyapp.com/", "https://reactstrap-ts.netlify.app"})
@RestController
public class DefaultController {

    @GetMapping
    public String entry() {
        return "This site is for experimenting purposes.";
    }

    @GetMapping({"/marquee"})
    public String marquee() {
        return "</marquee>SPRING BOOT REST API</marquee>";
    }
}
