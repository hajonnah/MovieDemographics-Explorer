package com.example.moviepopularitybackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviepopularitybackend.model.ApiCodes;
import com.example.moviepopularitybackend.model.CombinedDataByYear;
import com.example.moviepopularitybackend.model.DataCombiner;
import com.example.moviepopularitybackend.model.MovieStatistics;
import com.example.moviepopularitybackend.model.PopulationStatistics;
import com.example.moviepopularitybackend.model.UserPreferences;
import com.example.moviepopularitybackend.services.SaveService;
import com.example.moviepopularitybackend.services.TmdbService;
import com.example.moviepopularitybackend.services.UnPopulationService;

/**
 * The DataController class handles API requests related to combining
 * movie and population data, as well as managing user preferences. It acts as a
 * REST
 * controller that provides endpoints for accessing and manipulating the
 * combined data
 * and user preferences.
 */
@RestController
public class DataController {

    @Autowired
    private DataCombiner dataCombiner;

    @Autowired
    private TmdbService tmdbService;

    @Autowired
    private UnPopulationService unPopulationService;

    @Autowired
    private SaveService saveService;

    @Autowired
    private ApiCodes apiCodes;

    /**
     * Fetches combined data for movies and population statistics based on the
     * provided
     * parameters such as country, genre, start year, end year, and indicator.
     * 
     * @param country   The name of the country for which to fetch data.
     * @param genre     The genre of the movies for which to fetch data.
     * @param startYear The starting year of the data range.
     * @param endYear   The ending year of the data range.
     * @param indicator The population indicator for which to fetch data.
     * @return A TreeMap with the combined data by year, where the key is the year
     *         and the value is the combined movie and population statistics for
     *         that year.
     */
    @GetMapping("/combinedData")
    public TreeMap<Integer, CombinedDataByYear> getCombinedData(@RequestParam String country,
            @RequestParam String genre,
            @RequestParam int startYear,
            @RequestParam int endYear,
            @RequestParam String indicator) {

        // Convert country name and genre to corresponding codes using ApiCodes
        String countryCode = apiCodes.getCountryCodeMap().getOrDefault(country, "246");
        String movieCountryCode = apiCodes.getMovieCountryCodeMap().getOrDefault(country, "fi");
        String genreCode = apiCodes.getGenreCodeMap().getOrDefault(genre, "28");

        // Fetch data for movies and population using the provided parameters
        List<MovieStatistics> moviesData = tmdbService.fetchMovies(genreCode, movieCountryCode, startYear, endYear);
        List<PopulationStatistics> populationData = unPopulationService.fetchIntervalData(countryCode, indicator,
                startYear, endYear);

        // Combine the fetched movie and population data
        TreeMap<Integer, CombinedDataByYear> combinedData = dataCombiner.combineData(moviesData, populationData);
        System.out.println("GET /Fetched Country and Movie data.");
        return combinedData;
    }

    /**
     * Saves user preferences provided in the request body.
     * 
     * @param preferences The UserPreferences object containing the user's settings.
     * @return A map with the status of the save operation.
     */
    @PostMapping("/api/preferences")
    public Map<String, String> savePreferences(@RequestBody UserPreferences preferences) {
        saveService.SavetoFile(preferences);
        System.out.println("POST /Preferences saved to system.");
        return Map.of("status", "Preferences saved");
    }

    /**
     * Fetches all saved user preferences.
     * 
     * @return A set of UserPreferences objects representing all saved preferences.
     */
    @GetMapping("/api/preferences")
    public Set<UserPreferences> getPreferences() {
        Set<UserPreferences> preferences = saveService.GetFileContents();
        System.out.println("GET /Preferences fetched from system.");
        return preferences;
    }

    /**
     * Deletes a user preference based on the provided index.
     * 
     * @param index The index of the preference to delete.
     * @return A map indicating whether the deletion was successful or if the index
     *         was invalid.
     */
    @DeleteMapping("/api/preferences")
    public Map<String, String> deletePreferences(@RequestParam int index) {
        Set<UserPreferences> preferences = saveService.GetFileContents();
        if (index >= 0 && index < preferences.size()) {
            UserPreferences[] prefArray = preferences.toArray(new UserPreferences[0]);
            preferences.remove(prefArray[index]);
            saveService.SaveAllPreferences(preferences);
            System.out.println("DELETE /Preferences deleted from system");
            return Map.of("status", "Preference deleted");
        }
        return Map.of("status", "Invalid index");
    }

    /*
     * Endpoint to retrieve code mappings used for dropdown selection options in the
     * frontend.
     * This method provides mappings for country codes, movie country codes, genres,
     * and demographic indicators.
     * The mappings are sourced from the {@link ApiCodes} Model class.
     *
     * @return A map containing multiple mappings:
     * - "countries": a mapping of country names to their ISO country codes.
     * - "movieCountries": a mapping of country names to movie language codes.
     * - "genres": a mapping of movie genres to their corresponding genre codes.
     * - "indicators": a mapping of demographic indicators to their respective
     * codes.
     */
    @GetMapping("/api/codeMappings")
    public Map<String, Map<String, String>> getCodeMappings() {
        Map<String, Map<String, String>> codeMappings = new HashMap<>();
        codeMappings.put("countries", apiCodes.getCountryCodeMap());
        codeMappings.put("movieCountries", apiCodes.getMovieCountryCodeMap());
        codeMappings.put("genres", apiCodes.getGenreCodeMap());
        codeMappings.put("indicators", apiCodes.getIndicatorCodeMap());
        System.out.println("GET /CodeMappings. Program has loaded succesfully and is ready to operate!");
        return codeMappings;
    }
}
