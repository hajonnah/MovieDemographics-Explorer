package com.example.moviepopularitybackend.model;

/**
 * Represents a combined data object for a specific year, containing information about
 * a country, genre, population statistics, and the number of movies released.
 * This class encapsulates data relevant to both population and movie statistics
 * for a given year and genre in a specific country.
 */
public class CombinedDataByYear{
    private final String country;
    private final String genre;
    private final int year;
    private final PopulationStatistics populationInformation;
    private final int howManyMovies;

    /**
     * Constructs a new CombinedDataByYear object with the specified country, genre, year, 
     * population statistics, and number of movies.
     * @param country the country for which the data applies (e.g., "USA").
     * @param genre the genre of movies (e.g., "Action", "Drama").
     * @param year the year this data represents.
     * @param populationInformation the population statistics for the specified year and country.
     * @param howManyMovies the number of movies released in the given year and genre for the specified country.
     */
    public CombinedDataByYear(String country, String genre, int year, PopulationStatistics populationInformation, int howManyMovies) {
        this.country = country;
        this.genre = genre;
        this.year = year;
        this.populationInformation = populationInformation;
        this.howManyMovies = howManyMovies;
    }

    /**
     * Gets the country for which the data applies.
     * @return the country name (e.g., "USA", "Canada").
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the genre of movies.
     * @return the genre (e.g., "Action", "Drama", "Comedy").
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets the year this data represents.
     * @return the year as an integer (e.g., 2023).
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the population statistics for the specified year and country.
     * @return the population statistics as a PopulationStatistics object.
     */
    public PopulationStatistics getPopulationInformation() {
        return populationInformation;
    }

    /**
     * Gets the number of movies released in the specified year, country, and genre.
     * @return the number of movies as an integer.
     */
    public int getHowManyMovies() {
        return howManyMovies;
    }
}