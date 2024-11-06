
package com.mycompany.pai_lab5.repositories;

import com.mycompany.pai_lab5.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {

    List<Country> findByContinent(String continent);

    List<Country> findByPopulationBetween(long min, long max);

    List<Country> findByContinentAndSurfaceAreaBetween(String continent, double min, double max);
}

//http://localhost:8081/countries/byContinent?continent=Europe

//http://localhost:8081/countries/byPopulationBetween?min=1000000&max=10000000

//http://localhost:8081/countries/byContinentAndSurfaceAreaBetween?continent=Europe&min=50&max=10000000000