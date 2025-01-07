package com.example.moviepopularitybackend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.moviepopularitybackend.controller.DataController;
import com.example.moviepopularitybackend.model.CombinedDataByYear;
import com.example.moviepopularitybackend.model.UserPreferences;

/**
 * Test class for testing the DataController
 */
@SpringBootTest
public class DataControllerTests {

    @Autowired
    private DataController testDC;

    /**
    * Test for GetCombinedData. Tests made for an arbituary testyear of 1999,
    * tests if there are any movies in given genre and language for that year, and if there is
    * populationagedata for that year.
    */
    @Test
    void testGetCombinedData() {
        String genre = "Action";
		String cc = "Germany";
		int sty = 1980;
		int endy = 2020;
        String indicator = "46"; //population

        TreeMap<Integer, CombinedDataByYear> result = testDC.getCombinedData(cc, genre, sty, endy, indicator);
        
        CombinedDataByYear testyear = result.get(1999);
        
		assertTrue(testyear.getHowManyMovies() > 0);
        assertTrue(testyear.getPopulationInformation().getPopulationAges().get("15-24").getBothSexesPopulation() > 0);
    }

    /**
    * Test for SaveAndGetPreferences. Saves data to file, and retrieves its information to check
    * if the saved preference is included in there.
    */
    @Test
    void testSaveAndGetPreferences() {

        String genre = "testgenre";
		String country = "testCountry";
		String indicator = "testIndicator";
		String startYear = "1999";
		String endYear = "2005";

		UserPreferences newUp = new UserPreferences(genre, country, indicator, startYear, endYear);

        testDC.savePreferences(newUp);

        Set<UserPreferences> savedup = testDC.getPreferences();

        assertTrue(savedup.contains(newUp));
    }

    /**
    * Test for DeletePreference. Saves data to file, deletes it and checks that the preference is
    * not included in returned data.
    */
    @Test
    void testDeletePreference() {
        String genre = "testgenre2";
		String country = "testCountry2";
		String indicator = "testIndicator";
		String startYear = "1999";
		String endYear = "2005";

		UserPreferences newUp = new UserPreferences(genre, country, indicator, startYear, endYear);

        testDC.savePreferences(newUp);

        testDC.deletePreferences(testDC.getPreferences().size() - 1);

        assertTrue(!testDC.getPreferences().contains(newUp));
    }

}
