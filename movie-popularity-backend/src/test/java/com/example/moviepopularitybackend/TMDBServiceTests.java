package com.example.moviepopularitybackend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.moviepopularitybackend.model.MovieStatistics;
import com.example.moviepopularitybackend.services.TmdbService;

/**
 * Test class for testing the TMDBService
 */
@SpringBootTest
public class TMDBServiceTests {

    @Autowired
	private TmdbService testtmbd;

	/**
    * Test for fetchMovies. Tries to look for data for real movie parameters. Tests if the total amount of movies
	* for chosen parameters is more than 0.
    */
    @Test
	void testfetchMoviesRealData() {
		String genre = "28"; //action
		String cc = "de";
		int sty = 1980;
		int endy = 2020;

		List<MovieStatistics> movies = testtmbd.fetchMovies(genre,cc,sty, endy);

		int movietotal = 0;

		for(MovieStatistics movie:movies) {
			movietotal += movie.getMovieCount();
		}

		assertTrue(movietotal > 0);
	}

	/**
    * Test for fetchMovies. Tries to look for data for fake movie parameters. Tests if the total amount of movies
	* for first year in parameters is equal to zero.
    */
	@Test
	void testTMDBserviceFetchMoviesFakeData() {
		String genre = "testgenre";
		String cc = "fakecode";
		int sty = 1999;
		int endy = 2000;

		List<MovieStatistics> movies = testtmbd.fetchMovies(genre,cc,sty, endy);

		assertTrue(movies.getFirst().getMovieCount() == 0);
	}
}
