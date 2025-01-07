package com.example.moviepopularitybackend.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the population data for a specific country and year.
 */
public class PopulationStatistics {
    /** The country for which the population data applies. */
    private String country;
    /** The year of the population data. */
    private int year;
    /**
     * The indicator value selected by the user, representing one of the following metrics:
     * total population, life expectancy, population change, or total net migration.
     */
    private int indicatorValue;
    /**
     * A map to store categorized population data by age groups.
     * The keys represent age group categories: "0-14", "15-24", "25-64", "+65".
     * The values are AgeGroup objects representing the population counts for each group.
     */
    private Map<String, AgeGroup> populationAgeGroups;
    
    /**
     * Constructs an empty Population object and initializes the age group categories map.
     */
    public PopulationStatistics() {
        this.populationAgeGroups = new HashMap<>();
    }

    /**
     * Gets the country for which the population data applies.
     * @return the country name.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country for which the population data applies.
     * @param country the country name.
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * Gets the year of the population data.
     * @return the year as an integer.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the population data.
     * @param year the year as an integer.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Retrieves the value of the selected indicator. The indicator is chosen by the user 
     * and can represent one of the following: total population, life expectancy, 
     * population change, or total net migration.
     * @return the selected indicator's value as an integer.
     */
    public int getIndicatorValue() {
        return indicatorValue;
    }
    
    /**
     * Sets the value of the selected indicator. The indicator value is an integer 
     * representing one of the following metrics: total population, life expectancy, 
     * population change, or total net migration.
     * @param indicatorValue the value to set for the selected indicator.
     */
    public void setIndicatorValue(int indicatorValue) {
        this.indicatorValue = indicatorValue;
    }

    /**
     * Gets the categorized population data by age groups.
     * @return a map where the key is the age group category (e.g., "0-14") and the value is the AgeGroup object.
     */
    public Map<String, AgeGroup> getPopulationAges() {
        return populationAgeGroups;
    }

    /**
     * Sets the categorized population data by age groups.
     * @param populationAgeGroups a map where the key is the age group category 
     * and the value is the AgeGroup object representing population data.
     */
    public void setPopulationAges(Map<String, AgeGroup> populationAgeGroups) {
        this.populationAgeGroups = populationAgeGroups;
    }
}
