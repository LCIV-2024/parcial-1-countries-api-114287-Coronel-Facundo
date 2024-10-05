package ar.edu.utn.frc.tup.lciii.configs;

import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeneralBeanConfig {
    CountryRepository countryRepository;
    RestTemplate restTemplate;
    @Bean
    public CountryService myService() {
        // set properties, etc.
        return new CountryService(countryRepository, restTemplate);
    }

}
