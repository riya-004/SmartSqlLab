package net.engineeringdigest.journalApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health-check")
public class HealthCheck {

    @RequestMapping
    public String healthCheck(){
        return "Okk";
    }
}
