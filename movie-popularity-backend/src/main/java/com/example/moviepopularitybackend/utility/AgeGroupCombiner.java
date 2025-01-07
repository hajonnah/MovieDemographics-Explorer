package com.example.moviepopularitybackend.utility;

import java.util.HashMap;
import java.util.Map;

import com.example.moviepopularitybackend.model.AgeGroup;

/**
 * A utility class responsible for combining smaller age groups into broader age
 * categories.
 * This class processes raw population data by age group and gender, and
 * aggregates the data
 * into predefined age categories: "0-14", "15-24", "25-64", and "+65".
 */
public class AgeGroupCombiner {
    /**
     * Default constructor for AgeGroupCombiner.
     * Initializes a new instance of the utility class for combining age groups.
     */
    public AgeGroupCombiner() {
        // Default constructor
    }

    /**
     * Combines smaller age groups into broader age categories and returns the
     * result.
     * This method processes the raw population data obtained from an external
     * source,
     * and aggregates it into the categories: "0-14", "15-24", "25-64", and "+65".
     * 
     * @param rawPopulationAges a map where the first key is the age group (e.g.,
     *                          "0-4", "5-9"),
     *                          and the second key represents gender ("Male",
     *                          "Female", "Both sexes").
     *                          The value represents the population count for that
     *                          age group and gender.
     * @return a map where the key is the age category (e.g., "0-14", "15-24"), and
     *         the value is
     *         an AgeGroup object containing the aggregated population data for each
     *         category.
     */
    public static Map<String, AgeGroup> combineAgeGroups(Map<String, Map<String, Integer>> rawPopulationAges) {

        // Define categories and the age ranges they will aggregate
        String[] childrenAges = { "0-4", "5-9", "10-14" };
        String[] youthAges = { "15-19", "20-24" };
        String[] adultsAges = { "25-29", "30-34", "35-39", "40-44", "45-49", "50-54", "55-59", "60-64" };
        String[] eldersAges = { "65-69", "70-74", "75-79", "80-84", "85-89", "90-94", "95-99", "100+" };

        // Initialize categories in categorizedPopulationAges
        Map<String, AgeGroup> categorizedPopulationAges = new HashMap<>();
        categorizedPopulationAges.put("0-14", new AgeGroup("0-14", 0, 0, 0));
        categorizedPopulationAges.put("15-24", new AgeGroup("15-24", 0, 0, 0));
        categorizedPopulationAges.put("25-64", new AgeGroup("25-64", 0, 0, 0));
        categorizedPopulationAges.put("+65", new AgeGroup("+65", 0, 0, 0));

        // Combine data for each category "0-14", "15-24", "25-64", "+65"
        combineAgeGroupCategory("0-14", childrenAges, rawPopulationAges, categorizedPopulationAges);
        combineAgeGroupCategory("15-24", youthAges, rawPopulationAges, categorizedPopulationAges);
        combineAgeGroupCategory("25-64", adultsAges, rawPopulationAges, categorizedPopulationAges);
        combineAgeGroupCategory("+65", eldersAges, rawPopulationAges, categorizedPopulationAges);

        return categorizedPopulationAges;
    }

    /**
     * Helper method to combine the population data for a specific age category.
     * This method sums up the male, female, and both sexes populations for the
     * specified
     * age ranges and stores the result in the provided age category.
     *
     * @param category                  the broader age category (e.g., "0-14",
     *                                  "15-24").
     * @param ageRanges                 an array of smaller age ranges that belong
     *                                  to the category (e.g., "0-4", "5-9").
     * @param rawPopulationAges         a map containing the raw population data for
     *                                  each age range and gender.
     * @param categorizedPopulationAges a map to store the aggregated population
     *                                  data by category.
     */
    private static void combineAgeGroupCategory(String category, String[] ageRanges,
            Map<String, Map<String, Integer>> rawPopulationAges,
            Map<String, AgeGroup> categorizedPopulationAges) {
        AgeGroup ageGroup = categorizedPopulationAges.get(category);

        for (String ageRange : ageRanges) {
            Map<String, Integer> genderMap = rawPopulationAges.get(ageRange);
            if (genderMap != null) {
                // Sum male, female, and both sexes populations for the category
                ageGroup.setMalePopulation(ageGroup.getMalePopulation() + genderMap.getOrDefault("Male", 0));
                ageGroup.setFemalePopulation(ageGroup.getFemalePopulation() + genderMap.getOrDefault("Female", 0));
                ageGroup.setBothSexesPopulation(
                        ageGroup.getBothSexesPopulation() + genderMap.getOrDefault("Both sexes", 0));
            }
        }
    }
}