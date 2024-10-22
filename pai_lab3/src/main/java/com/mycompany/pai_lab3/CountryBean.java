package com.mycompany.pai_lab3;

import java.io.Serializable;

public class CountryBean implements Serializable {
    private String code;
    private String name; 
    private int population;

    public CountryBean() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
