package com.example.moviepopularitybackend.model;

/**
 * Represents the population data for a specific age group.
 * This class stores the population of males, females, and both sexes
 * within a specific age range.
 */
public class AgeGroup {
    private String ageRange;
    private int malePopulation;
    private int femalePopulation;
    private int bothSexesPopulation;

    /**
     * Constructs a new AgeGroup with the specified age range and population counts.
     * @param ageRange the age range this group represents (e.g., "0-14").
     * @param malePopulation the population count for males in this age group.
     * @param femalePopulation the population count for females in this age group.
     * @param bothSexesPopulation the population count for both sexes in this age group.
     */
    public AgeGroup(String ageRange, int malePopulation, int femalePopulation, int bothSexesPopulation) {
        this.ageRange = ageRange;
        this.malePopulation = malePopulation;
        this.femalePopulation = femalePopulation;
        this.bothSexesPopulation = bothSexesPopulation;
    }

    /**
     * Gets the age range this group represents.
     * @return the age range (e.g., "0-14", "15-24").
     */
    public String getAgeRange() {
        return ageRange;
    }
    /**
     * Sets the age range this group represents.
     * @param ageRange the age range (e.g., "0-14", "15-24").
     */
    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    /**
     * Gets the population count for males in this age group.
     * @return the male population count.
     */
    public int getMalePopulation() {
        return malePopulation;
    }
    /**
     * Sets the population count for males in this age group.
     * @param malePopulation the male population count.
     */
    public void setMalePopulation(int malePopulation) {
        this.malePopulation = malePopulation;
    }

    /**
     * Gets the population count for females in this age group.
     * @return the female population count.
     */
    public int getFemalePopulation() {
        return femalePopulation;
    }

    /**
     * Sets the population count for females in this age group.
     * @param femalePopulation the female population count.
     */
    public void setFemalePopulation(int femalePopulation) {
        this.femalePopulation = femalePopulation;
    }

    /**
     * Gets the population count for both sexes combined in this age group.
     * @return the population count for both sexes.
     */
    public int getBothSexesPopulation() {
        return bothSexesPopulation;
    }

    /**
     * Sets the population count for both sexes combined in this age group.
     * @param bothSexesPopulation the population count for both sexes.
     */
    public void setBothSexesPopulation(int bothSexesPopulation) {
        this.bothSexesPopulation = bothSexesPopulation;
    }
}
