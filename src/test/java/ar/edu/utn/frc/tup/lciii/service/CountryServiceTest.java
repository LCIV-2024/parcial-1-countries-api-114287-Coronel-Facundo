package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CountryServiceTest {

    @InjectMocks
    private CountryService countryService;

    @Autowired
    private CountryService countryService2;

    @MockBean
    private CountryRepository countryRepository;

    @MockBean
    private CountryService countryRepository3;

    @Mock
    private RestTemplate restTemplate;

    private List<Map<String, Object>> mockResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockResponse = new ArrayList<>();

        Map<String, Object> country1 = new HashMap<>();
        country1.put("cca3", "USA");
        country1.put("name", Collections.singletonMap("common", "United States"));
        country1.put("languages", Collections.singletonMap("eng", "English"));
        country1.put("borders", Arrays.asList("CAN", "MEX"));

        Map<String, Object> country2 = new HashMap<>();
        country2.put("cca3", "CAN");
        country2.put("name", Collections.singletonMap("common", "Canada"));
        country2.put("languages", Collections.singletonMap("eng", "English"));
        country2.put("borders", Arrays.asList("USA"));

        Map<String, Object> country3 = new HashMap<>();
        country3.put("cca3", "MEX");
        country3.put("name", Collections.singletonMap("common", "Mexico"));
        country3.put("languages", Collections.singletonMap("spa", "Spanish"));
        country3.put("borders", Arrays.asList("USA"));

        mockResponse.add(country1);
        mockResponse.add(country2);
        mockResponse.add(country3);
    }
    @Test
    public void testGetAllCountries() {
        countryService2 = new CountryService(null, new RestTemplate());
        List<Country> countries = countryService2.getAllCountries(null, null);
        assertNotNull(countries);
        assertTrue(countries.size() > 0);
    }

    @Test
    public void testGetByName() {
        countryService2 = new CountryService(null, new RestTemplate());
        List<Country> countries = countryService2.GetByName("USA");
        assertNotNull(countries);
        assertEquals(1, countries.size());
        assertEquals("USA", countries.get(0).getCode());
    }

    @Test
    public void testGetCountriesByLanguage() {
//        when(restTemplate.getForObject(anyString(), (Class<List>) any())).thenReturn(mockResponse);
        countryService2 = new CountryService(null, new RestTemplate());
        List<Country> countries = countryService2.getCountriesByLanguage("Spanish");
        assertNotNull(countries);
        assertFalse(countries.isEmpty());
        assertTrue(countries.stream().anyMatch(country -> country.getLanguages().containsValue("Spanish")));
    }

    @Test
    public void testGetCountryWithMostBorders() {
//        when(restTemplate.getForObject(anyString(), (Class<List>) any())).thenReturn(mockResponse);
        countryService2 = new CountryService(null, new RestTemplate());
        Country country = countryService2.getCountryWithMostBorders();
        assertNotNull(country);
        assertNotNull(country.getBorders());
    }

    @Test
    public void testSaveCountries() {
        List<Country> countries = countryService.saveCountries(2);
        assertNotNull(countries);
        assertEquals(2, countries.size());
    }

    @Test
    public void testGetCountriesByContinent() {
        when(restTemplate.getForObject(anyString(), (Class<List>) any())).thenReturn(mockResponse);

        List<Country> countries = countryService.getCountriesByContinent("Asia");
        assertEquals(0, countries.size());
    }
}
