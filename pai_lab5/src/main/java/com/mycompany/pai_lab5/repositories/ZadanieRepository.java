package com.mycompany.pai_lab5.repositories;

import com.mycompany.pai_lab5.entities.Zadanie;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ZadanieRepository
        extends CrudRepository<Zadanie, Long> {

    List<Zadanie> findByWykonane(boolean wykonane);

    List<Zadanie> findByKosztLessThan(double koszt);

    List<Zadanie> findByKosztBetween(double min, double max);

}
