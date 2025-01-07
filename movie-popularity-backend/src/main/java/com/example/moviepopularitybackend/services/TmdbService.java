package com.example.moviepopularitybackend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.moviepopularitybackend.model.MovieStatistics;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for interacting with the TMDB (The Movie Database)
 *
 * <p>
 * Example request:
 * </p>
 * {@code https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&with_original_language=fi&api_key=YOUR_API_KEY}
 */
@Service
public class TmdbService {
    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    @Value("${tmdb.api.url}")
    private String tmdbApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Asynchronously fetches movie data for specific year from the TMDB API.
     * This method is used by the fetchMovies method, and not used separately.
     *
     * @param genre          The genre code of the movies to fetch.
     * @param countryLetters The country code for the original language of the
     *                       movies.
     * @param year           The year for which to fetch the movies.
     * @return A CompletableFuture containing the JSON data as a string, or
     *         {@code null} if
     *         the fetch operation fails.
     */
    private CompletableFuture<String> fetchMovieByYear(String genre, String countryLetters, int year) {

        return CompletableFuture.supplyAsync(() -> {

            String jsonData = null;
            try {
                String urlString = tmdbApiUrl +
                        "/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&with_original_language="
                        + countryLetters + "&api_key=" + tmdbApiKey + "&year=" + year + "&with_genres=" + genre;

                HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
                ResponseEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET, entity,
                        String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    jsonData = response.getBody();
                } else {
                    System.out.println("GET request failed. Response Code: " + response.getStatusCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonData;
        });

    }

    /**
     * Asynchronously fetches movie data from an interval of (startDate -> endDate)
     * from the TMDB API.
     *
     * @param genre          The genre code of the movies to fetch.
     * @param countryLetters The country code for the original language of the
     *                       movies.
     * @param startDate      The start year for which to fetch the movies.
     * @param endDate        The end year for which to fetch the movies.
     * @return A list of {@link MovieStatistics} objects containing the fetched data
     *         for each year.
     */
    public List<MovieStatistics> fetchMovies(String genre, String countryLetters, int startDate, int endDate) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = startDate; i <= endDate; i++) {
            futures.add(fetchMovieByYear(genre, countryLetters, i));
        }
        List<MovieStatistics> movies = new ArrayList<>();
        int counter = 0;
        for (CompletableFuture<String> future : futures) {
            try {
                String jsonData = future.get();
                if (jsonData != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> map = mapper.readValue(jsonData, Map.class);
                    Integer totalResults = (Integer) map.get("total_results");
                    int year = startDate + counter;
                    MovieStatistics movie = new MovieStatistics(genre, Integer.toString(year), countryLetters,
                            totalResults);
                    movies.add(movie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            counter++;
        }
        return movies;
    }

}
