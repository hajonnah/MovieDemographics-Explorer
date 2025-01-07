package com.example.moviepopularitybackend.model;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A service class responsible for combining movie and population data into a unified data structure.
 * This class processes two lists: one containing movie statistics by genre and year, and another
 * containing population statistics for a specific country and year. The combined result is stored
 * in a map, where the key is the year, and the value is a CombinedDataByYear object.
 */
@Service
public class DataCombiner {
    /** A TreeMap to store combined data by year, where the key is the year and the value is CombinedDataByYear. */
    private TreeMap<Integer, CombinedDataByYear> combinedData;

    /**
     * Constructs a new DataCombiner object and initializes the TreeMap for storing combined data by year.
     */
    public DataCombiner() {
        this.combinedData = new TreeMap<>();
    }

    /**
     * Combines movie statistics and population statistics based on the year, creating a unified data structure
     * for each year that includes both population and movie data. The result is stored in a TreeMap with the year as the key.
     *
     * @param moviesData a list of MovieStatistics objects representing movie data for spesific years.
     * @param populationData a list of PopulationStatistics objects representing population data for specific years
     * @return a TreeMap where the key is the year (as an Integer) and the value is a CombinedDataByYear object containing combined movie and population data.
     */
    public TreeMap<Integer, CombinedDataByYear> combineData(List<MovieStatistics> moviesData, List<PopulationStatistics> populationData) {
            // Reset the combinedData map to ensure no old data is retained
            this.combinedData = new TreeMap<>();
        // Temporary map to store population data by year
        Map<Integer, PopulationStatistics> populationMap = new HashMap<>();

        // Populate the populationMap with year as the key and PopulationStatistics as the value
        for (PopulationStatistics population : populationData) {
            populationMap.put(population.getYear(), population);
        }
        // Combine movie and population data based on the year
        for (MovieStatistics movie : moviesData) {
            int year = Integer.parseInt(movie.getYear());  // Convert year to int for easier matching

            // Find population data for the same year
            PopulationStatistics population = populationMap.get(year);

            if (population != null) {
                // Create a CombinedDataByYear object
                CombinedDataByYear combinedDataByYear = new CombinedDataByYear(
                    population.getCountry(),
                    movie.getGenre(),  
                    year,
                    population,        
                    movie.getMovieCount()     
                );
                // Store the CombinedDataByYear object in the TreeMap
                combinedData.put(year, combinedDataByYear);
            }
        }      
        return combinedData; 
    }
}