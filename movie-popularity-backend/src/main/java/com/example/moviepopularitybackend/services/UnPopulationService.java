package com.example.moviepopularitybackend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.moviepopularitybackend.model.PopulationStatistics;
import com.example.moviepopularitybackend.utility.Utility;

/**
 * Service with methods for fetching population data from the UN API.
 */
@Service
public class UnPopulationService {

    @Value("${un.api.key}")
    private String unApiKey;

    @Value("${un.api.url}")
    private String unApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Asynchronously fetches population data for specific year from the UN API.
     * Not intended to be used separately.
     *
     * @param countryNumber The country number for which to fetch the data.
     * @param indicator     The indicator number for which to fetch the data.
     * @param year          The year for which to fetch the data.
     * @return A CompletableFuture containing the CSV data as a string.
     */
    private CompletableFuture<String> fetchOneYearData(String countryNumber, String indicator, Integer year) {
        return CompletableFuture.supplyAsync(() -> {
            String csvData = null;
            // TBD: retry function in order to recover from UN API being unresponsive for
            // first request (their service might be at sleep).
            try {
                String urlString = unApiUrl + "/v1/data/indicators/" + indicator + "/locations/" + countryNumber
                        + "/start/" + year.toString() + "/end/" + year.toString() + "?pagingInHeader=true&format=csv";
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", unApiKey);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET, entity,
                        String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    csvData = response.getBody();
                } else {
                    System.out.println("GET request failed. Response Code: " + response.getStatusCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return csvData;
        });
    }

    /**
     * Fetches UN data from a timeperiod of (startYear -> endYear) asynchronously
     * and parses it into a list of PopulationStatistics.
     *
     * @param countryNumber The country number for which to fetch the data.
     * @param indicator     The indicator number for which to fetch the data.
     * @param startYear     The year for which to fetch the data.
     * @param endYear       The year for which to fetch the data.
     * @return A CompletableFuture containing the CSV data as a string.
     */
    public List<PopulationStatistics> fetchIntervalData(String countryNumber, String indicator, Integer startYear,
            Integer endYear) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int year = startYear; year <= endYear; year++) {
            futures.add(fetchOneYearData(countryNumber, indicator, year));
        }

        List<PopulationStatistics> populations = new ArrayList<>();
        List<String> csvDataList;
        try {
            csvDataList = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return populations;
        }

        for (String csvData : csvDataList) {
            if (csvData != null) {
                Utility.parseUnData(csvData, populations, countryNumber);
            }
        }
        return populations;
    }

}