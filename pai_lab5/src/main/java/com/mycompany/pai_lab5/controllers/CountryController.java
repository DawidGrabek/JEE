package com.mycompany.pai_lab5.controllers;

import com.mycompany.pai_lab5.entities.Country;
import com.mycompany.pai_lab5.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryRepository repo;

    @GetMapping("/byContinent")
    public List<Country> getCountriesByContinent(@RequestParam String continent) {
        return repo.findByContinent(continent);
    }

    @GetMapping("/byPopulationBetween")
    public List<Country> getCountriesByPopulationBetween(@RequestParam long min, @RequestParam long max) {
        return repo.findByPopulationBetween(min, max);
    }

    @GetMapping("/byContinentAndSurfaceAreaBetween")
    public List<Country> getCountriesByContinentAndSurfaceAreaBetween(
            @RequestParam String continent, @RequestParam double min, @RequestParam double max) {
        return repo.findByContinentAndSurfaceAreaBetween(continent, min, max);
    }
}
