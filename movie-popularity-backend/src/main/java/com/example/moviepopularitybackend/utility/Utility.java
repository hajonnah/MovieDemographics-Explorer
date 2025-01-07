package com.example.moviepopularitybackend.utility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.moviepopularitybackend.model.AgeGroup;
import com.example.moviepopularitybackend.model.PopulationStatistics;



/**
 * This is an utility class with methods for parsing data, and other future possible utility methods.
 */
public class Utility {
    /**
     * Parses UN data from a CSV string and adds it to the population list given as parameter.
     * The data is fetched as a CSV to save fetch requests, as to not overload the UN API.
     * (The CSV format served by the API is more compact than the JSON format.)
     * 
     * Splits the data with lines into arrays of values, and then iterates through the values.
     * 
     * @param csvData The CSV data as a string.
     * @param populations The list of PopulationStatistics to which the parsed data will be added year by year.
     * @param countryNumber The country number to set in the PopulationStatistics.
     */
    public static void parseUnData(String csvData, List<PopulationStatistics> populations, String countryNumber) {
        PopulationStatistics population = new PopulationStatistics();
        population.setCountry(countryNumber);
 
        // Temporary map to hold raw population data
        Map<String, Map<String, Integer>> rawPopulationAges = new HashMap<>();

        String[] lines = csvData.split("\n");
        for (String line : lines) {
            //Skipping first row
            if (line.isEmpty() || line.startsWith("sep")) {
                continue;
            }
            //The CSV row as a string array. 
            //Values accessed by index
            String[] values = line.split("\\|");
            if (values.length > 2) {
                if (values[12].equals("Median")) {
                    String sex = values[25].trim();
                    String ageGroup = values[27].trim();
                    int populationCount;
                    int year;
                    try {
                        populationCount = (int) Double.parseDouble(values[31].trim().replaceAll("\"", ""));
                        year = (int) Double.parseDouble(values[16].trim().replaceAll("\"", ""));
                        population.setYear(year);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    //Use existing or create new age map
                    Map<String, Integer> ageGroupMap = rawPopulationAges.computeIfAbsent(ageGroup, k -> new HashMap<>());
                    ageGroupMap.put(sex, populationCount);
                    rawPopulationAges.put(ageGroup, ageGroupMap);
                }
            } else {
                System.out.println("Empty row " + line);
            }
        }
        int indicatorValue = 0;
        // Calculate the indicator value
        // The indicator value is selected by the user, representing one of the following metrics:
        // total population, life expectancy, population change, or total net migration.
        for (Map<String, Integer> ageGroup : rawPopulationAges.values()) {
            for (Map.Entry<String, Integer> entry : ageGroup.entrySet()) {
                if (entry.getKey().equals("Both sexes")) {
                    indicatorValue += entry.getValue();
                }
            }
        }
        population.setIndicatorValue(indicatorValue);

        // Use AgeGroupCombiner to combine age groups from rawPopulationAges
        Map<String, AgeGroup> combinedAgeGroups = AgeGroupCombiner.combineAgeGroups(rawPopulationAges);
        population.setPopulationAges(combinedAgeGroups);
        // Add the population to the list given as parameter
        populations.add(population);
    }
}
