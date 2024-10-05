package ar.edu.utn.frc.tup.lciii.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private long population;
    private double area;
    private String code;
    private String region;
    @ElementCollection
    private List<String> borders;

    @ElementCollection
    @CollectionTable(name = "country_languages")
    @MapKeyColumn(name = "language_code")
    @Column(name = "language_name")
    private Map<String, String> languages;
}