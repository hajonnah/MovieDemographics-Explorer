package com.example.moviepopularitybackend.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * The ApiCodes class is a Spring component that holds mappings for various codes
 * related to countries, movies, genres, and indicators. These mappings are used to convert 
 * human-readable values into corresponding code values that are used in the system.
 */
@Component
public class ApiCodes {
    // Map to store country names as keys and their respective country codes as values
    private final Map<String, String> countryCodeMap = new HashMap<>();
    // Map to store movie country names as keys and their respective movie language codes as values
    private final Map<String, String> movieCountryCodeMap = new HashMap<>();
    // Map to store movie genres as keys and their respective genre codes as values
    private final Map<String, String> genreCodeMap = new HashMap<>();
    // Map to store population indicators as keys and their respective indicator codes as values
    private final Map<String, String> indicatorCodeMap = new HashMap<>();

    /**
     * Constructor to initialize the ApiCodes object with default mappings.
     * It populates the countryCodeMap, movieCountryCodeMap, genreCodeMap, 
     * and indicatorCodeMap with predefined values.
     */
    public ApiCodes() {
        // Initialize countryCodeMap with country names and their respective codes      
        countryCodeMap.put("Finland", "246");
        countryCodeMap.put("Germany", "276");
        countryCodeMap.put("Poland", "616");
        countryCodeMap.put("Sweden", "752");
        countryCodeMap.put("Japanese", "392");
        countryCodeMap.put("Netherlands", "528");
        countryCodeMap.put("Greece", "300");

        // Initialize movieCountryCodeMap with country names and their respective movie language codes
        movieCountryCodeMap.put("Finland", "fi");
        movieCountryCodeMap.put("Germany", "de");
        movieCountryCodeMap.put("Poland", "pl");
        movieCountryCodeMap.put("Sweden", "sv");
        movieCountryCodeMap.put("Japanese", "ja");
        movieCountryCodeMap.put("Netherlands", "nl");
        movieCountryCodeMap.put("Greece", "el");

        // Initialize genreCodeMap with movie genres and their respective genre codes
        genreCodeMap.put("Action", "28");
        genreCodeMap.put("Adventure", "12");
        genreCodeMap.put("Animation", "16");
        genreCodeMap.put("Comedy", "35");
        genreCodeMap.put("Crime", "80");
        genreCodeMap.put("Documentary", "99");
        genreCodeMap.put("Drama", "18");
        genreCodeMap.put("Family", "10751");
        genreCodeMap.put("Fantasy", "14");
        genreCodeMap.put("History", "36");
        genreCodeMap.put("Horror", "27");
        genreCodeMap.put("Romance", "10749");
        genreCodeMap.put("Science Fiction", "878");
        genreCodeMap.put("Thriller", "53");
        genreCodeMap.put("War", "10752");
        genreCodeMap.put("Western", "37");

        // Initialize indicatorCodeMap with population indicators and their respective codes
        indicatorCodeMap.put("Total Population", "46");
        indicatorCodeMap.put("Life expectancy at birth", "61");
        indicatorCodeMap.put("Population change", "50");
        indicatorCodeMap.put("Total net migration", "65");
    }

    /**
     * Returns the map that contains country names and their respective country codes.
     * @return a map of country names as keys and their respective country codes as values
     */
    public Map<String, String> getCountryCodeMap() {
        return countryCodeMap;
    }

    /**
     * Returns the map that contains movie country names and their respective movie language codes.
     * @return a map of movie country names as keys and their respective movie language codes as values
     */
    public Map<String, String> getMovieCountryCodeMap() {
        return movieCountryCodeMap;
    }

    /**
     * Returns the map that contains movie genres and their respective genre codes.
     * @return a map of movie genres as keys and their respective genre codes as values
     */
    public Map<String, String> getGenreCodeMap() {
        return genreCodeMap;
    }

    /**
     * Returns the map that contains population indicators and their respective indicator codes.
     * @return a map of population indicators as keys and their respective indicator codes as values
     */
    public Map<String, String> getIndicatorCodeMap() {
        return indicatorCodeMap;
    }
}