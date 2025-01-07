# MovieDemographics Explorer

This project is a full-stack web application designed to display movie popularity using data from the TMDB API and demographic data from the UN WPP API. It consists of a React-based frontend and a Spring Boot-based backend.

__Documentation__

The project documentation is available in English and can be found in the Documentation folder.

__Project Details__

This project was developed as part of the Software Design course at Tampere University during Autumn 2024. The primary goal of this project was to gain hands-on experience with the MVC architectural model and its implementation in a real-world application.

* __Frontend development__: Developed by Elias Penkkimäki.
* __Backend Development:__
  *  __AgeGroup.java, ApiCodes.java, CombinedDataByYear.java, DataCombiner.java, MovieStatistics.java, PopulationStatistics.java__ and __AgeGroupCombiner.java__: Developed by Jonna Hartikka (@hajonnah).
  * __TmdbService.java, UnPopulationService.java__ and __Utility.java__: Developed by Lotte Karlsson.
  * __DataController.java__: Collaboratively developed by Jonna Hartikka and Elias Penkkimäki.
  * __UserPreferences.java, SaveService.java__ and the test classes (__DataControllerTests.java, SaveServiceTests.java, TMDBServiceTests.java, UnPopulationServiceTests.java__): Developed by Aarni Heikkilä.

__Setup__

To use this program, follow these steps:

__Frontend:__

1. Clone the repository: git clone <repository-url>
2. Navigate to the frontend folder: cd react-movie-popularity-app
3. Install dependencies: npm install
4. Start the development server: npm run dev
5. Open your browser and navigate to: http://localhost:5173

__Backend__

1. Prerequisites: install Java 23 and SDK and Maven
2. Navigate to the backend folder: cd movie-popularity-backend
3. mvn spring-boot:run

__Setting Up API Keys:__
The application requires API keys for external services. Follow these steps to configure them:

1. Create an application.properties File:
  * Go to the movie-popularity-backend/src/main/resources directory.
  * Create a new file named application.properties (if it doesn’t already exist).
  * Copy the content from application.properties.example

2. Get API Keys:

TMDB API Key:
  * Visit TMDB API website.
  * Create an account, generate an API key, and replace YOUR_API_KEY_HERE with your TMDB API key.

UN WPP API Key:
  * Visit UN Population API.
  * Sign up, generate an API key, and replace YOUR_API_KEY_HERE with your UN WPP API key.

__Running Tests:__
1. Navigate to the backend directory: cd movie-popularity-backend
2. mvn test

__JAVADOCS__
* The Javadocs for the backend can be found at: movie-popularity-backend\apidocs\index.html
* To view JAVADOCS, open index.html in your browser
