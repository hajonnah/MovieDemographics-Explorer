package com.example.moviepopularitybackend;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.moviepopularitybackend.controller.DataController;
import com.example.moviepopularitybackend.model.UserPreferences;
import com.example.moviepopularitybackend.services.SaveService;

/**
 * Test class for testing the SaveService
 */

@SpringBootTest
class SaveServiceTests {

	@TempDir 
	Path tempDir;

	@Autowired
	private SaveService testSS;

	//used for deletion method
	@Autowired
    private DataController testDC;

	//resets savefile after running any test
	@AfterEach
	void setUp() {
		testDC.deletePreferences(testDC.getPreferences().size() - 1);
	}
	
	/**
    * Test for SaveToFile. Arbituary testpreference is saved to the file, and file is checked to exist
	* and have more than one preference saved in it.
    */
	@Test
	void testSaveToFile() {

		String genre = "testgenre";
		String country = "testCountry";
		String indicator = "testIndicator";
		String startYear = "1999";
		String endYear = "2005";

		UserPreferences newUp = new UserPreferences(genre, country, indicator, startYear, endYear);
		
		testSS.SavetoFile(newUp);

		File f = new File("preferences.json");

		assertTrue(f.exists() );
		assertTrue(f.length() > 0);
	}

	/**
    * Test for SaveAndGetFileContents. Arbituary testpreference is saved to the file, file is checked to contain
	* this preference
    */
	@Test
	void testSaveAndGetFileContents() {
		
		String genre = "testgenre";
		String country = "testCountry";
		String indicator = "testIndicator";
		String startYear = "1999";
		String endYear = "2005";

		UserPreferences newUp = new UserPreferences(genre, country, indicator, startYear, endYear);
		
		testSS.SavetoFile(newUp);

		Set<UserPreferences> up2 = testSS.GetFileContents();

		assertTrue(up2.contains(newUp));
	}
}
