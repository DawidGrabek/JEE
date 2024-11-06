package com.mycompany.pai_lab5.controllers;

import com.mycompany.pai_lab5.entities.Zadanie;
import com.mycompany.pai_lab5.repositories.ZadanieRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {

    @Autowired
    public ZadanieRepository rep;

    @RequestMapping("/")
    @ResponseBody
    public String mainPage() {
        return "Hello Spring Boot from mainPage() method!";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String pageTwo() {
        return "Hello Spring Boot from pageTwo() method!";
    }

    @RequestMapping("/listaZadan")
    @ResponseBody
    public String listaZadan() {
        StringBuilder odp = new StringBuilder();
        Zadanie zadanie = new Zadanie();
        rep.save(zadanie);
        for (Zadanie i : rep.findAll()) {
            odp.append(i).append("<br>");
        }
        return odp.toString();
    }

    @GetMapping("/generateTasks")
    public String generateTasks() {
        double koszt = 1000;
        boolean wykonane = false;
        for (int i = 1; i <= 10; i++) {
            Zadanie z = new Zadanie();
            z.setNazwa("zadanie " + i);
            z.setOpis("Opis czynności do wykonania w zadaniu " + i);
            z.setKoszt(koszt);
            z.setWykonane(wykonane);
            rep.save(z);
            wykonane = !wykonane;
            koszt += 200.50;
        }
        return "redirect:/listaZadan";
    }

    @RequestMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        Zadanie zadanie = rep.findById(id).orElse(null);
        if (zadanie != null) {
            rep.delete(zadanie);
            return "redirect:/listaZadan";
        }
        return "redirect:/listaZadan";
    }
    
//    @DeleteMapping("/delete/{id}")
//    public String deleteTask(@PathVariable Long id) {
//        rep.deleteById(id);
//        return "redirect:/listaZadan";
//    }
    
    @RequestMapping("/deleteAll")
    @ResponseBody
    public String deleteAllTasks() {
        rep.deleteAll();
        return "Wszystkie zadania zostały usunięte.";
    }

    
    @RequestMapping("/find/{id}")
    @ResponseBody
    public String findTask(@PathVariable Long id) {
        Zadanie zadanie = rep.findById(id).orElse(null);
        if (zadanie == null) {
            return "Zadanie o id " + id + " nie zostało znalezione.";
        }
        return "Znaleziono zadanie: " + zadanie;
    }
    
    @RequestMapping("/count")
    @ResponseBody
    public String countTasks() {
        long count = rep.count();
        return "Liczba zadań w bazie: " + count;
    }

    @GetMapping("/tasks/byWykonane")
    public @ResponseBody List<Zadanie> getTaxsksByWykonane(@RequestParam boolean wykonane) {
        return rep.findByWykonane(wykonane);
    }

    @GetMapping("/tasks/byCostLessThan")
    public @ResponseBody List<Zadanie> getTasksByCostLessThan(@RequestParam double koszt) {
        return rep.findByKosztLessThan(koszt);
    }

    @GetMapping("/koszt/{min}/{max}")
    public @ResponseBody List<Zadanie> getTasksByCostBetween(@PathVariable double min, @PathVariable double max) {
        return rep.findByKosztBetween(min, max);
    }
}
