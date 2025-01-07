package com.example.moviepopularitybackend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.moviepopularitybackend.model.PopulationStatistics;
import com.example.moviepopularitybackend.services.UnPopulationService;

/**
 * Test class for testing the UnPopulationService
 */
@SpringBootTest
public class UnPopulationServiceTests {

    @Autowired
	private UnPopulationService testunp;

	/**
    * Test for FetchIntervalData. Tries to look for data for real indicator parameters. 
	* Tests if the total population of both sexes over the chosen period is more than 0.
    */
    @Test
	void testFetchIntervalDataRealData() {
		String countryNumber = "276"; //Germany
		String indicator = "46"; //population
		Integer startYear = 1985;
		Integer endYear = 2010;

		List<PopulationStatistics> popdata = testunp.fetchIntervalData(countryNumber, indicator, startYear, endYear);
		
		int poptotal = 0;
		
		for(PopulationStatistics popstat:popdata) {
			poptotal += popstat.getPopulationAges().get("15-24").getBothSexesPopulation();
		}

		assertTrue(poptotal > 0);
	}

}
