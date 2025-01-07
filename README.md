MovieDemographics Explorer

This project is a full-stack web application designed to display movie popularity using data from the TMDB API and demographic data from the UN WPP API. It consists of a React-based frontend and a Spring Boot-based backend.

Documentation

The project documentation is available in English and can be found in the Documentation folder.

Project Details

This project was developed as part of the Software Design course at Tampere University during Autumn 2024.

Frontend development: Developed by Elias Penkkimäki.
AgeGroup.java, ApiCodes.java, CombinedDataByYear.java, DataCombiner.java, MovieStatistics.java, PopulationStatistics.java, and AgeGroupCombiner.java: Developed by Jonna Hartikka (@hajonnah).
TmdbService.java, UnPopulationService.java, and Utility.java: Developed by Lotte Karlsson.
DataController.java: Collaboratively developed by Jonna Hartikka and Elias Penkkimäki.
UserPreferences.java, SaveService.java, and the test classes (DataControllerTests.java, SaveServiceTests.java, TMDBServiceTests.java, UnPopulationServiceTests.java): Developed by JAarni Heikkilä.

Setup

To use this program, follow these steps:

Frontend:

1. Clone the repository: git clone <repository-url>
2. Navigate to the frontend folder: cd react-movie-popularity-app
3. Install dependencies: npm install
4. Start the development server: npm run dev
5. Open your browser and navigate to: http://localhost:5173

Backend

1. Prerequisites: install Java 23 and SDK and Maven
2. cmd: cd movie-popularity-backend
3. mvn spring-boot:run

Setting Up API Keys:
The application requires API keys for external services. Follow these steps to configure them:

1. Create an application.properties File:
- Go to the movie-popularity-backend/src/main/resources directory.
- Create a new file named application.properties (if it doesn’t already exist).
- Copy the content from application.properties.example

2. Get API Keys:
TMDB API Key:
- Visit TMDB API website.
- Create an account, generate an API key, and replace YOUR_API_KEY_HERE with your TMDB API key.
UN WPP API Key:
- Visit UN Population API.
- Sign up, generate an API key, and replace YOUR_API_KEY_HERE with your UN WPP API key.

How to run tests:
1. in cmd: cd movie-popularity-backend
2. mvn test

JAVADOCS file can be located at:
movie-popularity-backend\apidocs\index.html

To view JAVADOCS, open index.html in your browser