package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController {

    @Autowired
    private final CountryService countryService;

    @GetMapping("api/countries")
    public List<Country> getAllCountries(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String code) {
        return countryService.getAllCountries(name, code);
    }
    @GetMapping("/continent/{continentName}")
    public List<Country> getCountriesByContinent(@PathVariable String continentName) {
        return countryService.getCountriesByContinent(continentName);
    }

    @GetMapping("/{language}/language")
    public List<Country> getCountriesByLanguage(@PathVariable String language) {
        return countryService.getCountriesByLanguage(language);
    }

    @GetMapping("/most-borders")
    public Country getCountryWithMostBorders() {
        return countryService.getCountryWithMostBorders();
    }
}