/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.moviepopularitybackend.model;

/**
 * Represents a single user preference for graph settings in the front end
 * 
 */
public class UserPreferences {

    /** The movie genre shown in graph. */
    private String genre;
    /** The country which the graph shows. */
    private String country;
    /** Start year for the yearly data. */
    private String startYear;
    /** End year for the yearly data. */
    private String endYear;

    private String indicator;

    /**
     * Default constructor.
     * 
     * @param genre     the genre of the movie preference (e.g., Action, Drama,
     *                  Comedy).
     * @param country   the country in which the movies were released.
     * @param startYear the start date for the movie graph.
     * @param endYear   the end year of the movie graph.
     */
    public UserPreferences(String genre, String country, String indicator, String startYear, String endYear) {
        this.genre = genre;
        this.country = country;
        this.indicator = indicator;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    /**
     * Override equals to use set correctly.
     * 
     * @return bool, true if all object parameters match.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserPreferences that = (UserPreferences) o;

        // tests for each data field between objects
        if (!genre.equals(that.genre))
            return false;
        if (!country.equals(that.country))
            return false;
        if (!startYear.equals(that.startYear))
            return false;
        if (!endYear.equals(that.endYear))
            return false;
        return indicator.equals(that.indicator);
    }

    /**
     * Override hashcode to ensure same is returned for two same objects.
     * 
     * @return int, hashcode for the object.
     */
    @Override
    public int hashCode() {

        // if same fields, same hashcode.
        int result = genre.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + startYear.hashCode();
        result = 31 * result + endYear.hashCode();
        result = 31 * result + indicator.hashCode();
        return result;
    }

    /**
     * Gets the genre of the movie preference.
     * 
     * @return the genre as a string.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the movie preference.
     * 
     * @param genre the genre as a string.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets the country where the movies were released.
     * 
     * @return the country as a string.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country where the movies were released.
     * 
     * @param country the country as a string.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    /**
     * Gets the start year for the movie graph.
     * 
     * @return the start year as a string.
     */
    public String getStartYear() {
        return startYear;
    }

    /**
     * Sets the start year for the movie graph.
     * 
     * @param startYear the start year as a string.
     */
    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    /**
     * Gets the end year for the movie graph.
     * 
     * @return the end year as a string.
     */
    public String getEndYear() {
        return endYear;
    }

    /**
     * Sets the end year for the movie graph.
     * 
     * @param endYear the end year as a string.
     */
    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    @Override
    public String toString() {
        return "UserPreferences{" +
                "country='" + country + '\'' +
                ", genre='" + genre + '\'' +
                ", indicator='" + indicator + '\'' +
                ", startYear='" + startYear + '\'' +
                ", endYear='" + endYear + '\'' +
                '}';
    }

}
