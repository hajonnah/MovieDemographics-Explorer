package com.example.moviepopularitybackend.model;

/**
 * Represents movie statistics for a specific genre, year, and country.
 * This class encapsulates data about the number of movies produced
 * in a particular genre for a specific year and country.
 */
public class MovieStatistics {
    private final String genre;
    private final String year;
    private final String country;
    private final int movieCount;

    /**
     * Constructs a new MovieStatistics object with the specified genre, year, country, and movie count.
     * @param genre      the genre of the movies (e.g., Action, Drama, Comedy).
     * @param year       the year in which the movies were released.
     * @param country    the country where the movies were produced.
     * @param movieCount the total number of movies.
     */
    public MovieStatistics(String genre, String year, String country, int movieCount) {
        this.genre = genre;
        this.year = year;
        this.country = country;
        this.movieCount = movieCount;
    }
    /**
     * Gets the genre of the movies.
     * @return the genre of the movies (e.g., Action, Drama, Comedy).
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets the year in which the movies were produced or released.
     * @return the year as a String (e.g., "2023").
     */
    public String getYear() {
        return year;
    }

    /**
     * Gets the country where the movies were produced or released.
     * @return the country name (e.g., "USA", "Canada").
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the total number of movies produced or released for the given genre, year, and country.
     * @return the movie count as an integer.
     */
    public int getMovieCount() {
        return movieCount;
    }
}
