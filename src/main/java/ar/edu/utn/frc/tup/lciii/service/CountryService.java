package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        @Autowired
        private final CountryRepository countryRepository;

        @Autowired
        private final RestTemplate restTemplate;

        public List<Country> getAllCountries(String name, String code) {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                if (code != null && !code.isBlank()) {
                        return response.stream()
                                .filter(country -> country.get("cca3") != null && country.get("cca3").toString().equalsIgnoreCase(code))
                                .map(this::mapToCountry)
                                .collect(Collectors.toList());
                }
                if (name != null && !name.isBlank()) {
                        return response.stream()
                                .filter(country -> country.get("name") != null &&
                                        ((Map<String, String>) country.get("name")).get("common").equalsIgnoreCase(name))
                                .map(this::mapToCountry)
                                .collect(Collectors.toList());
                }
                return response.stream()
                        .map(this::mapToCountry)
                        .collect(Collectors.toList());
        }

        public List<Country> GetByName(String code){
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                var result = response.stream().map(this::mapToCountry).collect(Collectors.toList());
                return result.stream().filter(country -> country.getCode().equals(code)).collect(Collectors.toList());
        }
        public List<Country> getCountriesByLanguage(String language) {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

                return response.stream()
                        .filter(country -> {
                                Map<String, String> languages = (Map<String, String>) country.get("languages");
                                return languages != null && languages.containsValue(language);
                        })
                        .map(this::mapToCountry)
                        .collect(Collectors.toList());
        }

        public Country getCountryWithMostBorders() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream()
                        .max((country1, country2) -> {
                                List<String> borders1 = (List<String>) country1.get("borders");
                                List<String> borders2 = (List<String>) country2.get("borders");
                                return Integer.compare(borders1.size(), borders2.size());
                        })
                        .map(this::mapToCountry)
                        .orElse(null);
        }

        public List<Country> saveCountries(int amount) {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream()
                        .limit(amount)
                        .map(this::mapToCountry)
                        .collect(Collectors.toList());
        }
        public List<Country> getCountriesByContinent(String continent) {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

                return response.stream()
                        .filter(country -> country.get("region") != null &&
                                country.get("region").toString().equalsIgnoreCase(continent))
                        .map(this::mapToCountry)
                        .collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                List<String> borders = (List<String>) countryData.get("borders");
                return Country.builder()
                        .code((String) countryData.get("cca3")) // Mapeo de cca3
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .borders(borders)
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }
}