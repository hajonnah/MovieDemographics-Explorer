/**
 * React component for displaying the frontend Dashboard for our MovieDemographics Explorer app.
 * Manages state for country, genre, start and end year, indicator, and comparison data.
 * Includes functionality to fetch and display movie popularity and demographic data from an API.
 * Also allows users to save and load preferences, and compare data between two countries or genres.
 * Utilizes MoviePopularityChart.jsx for dislpaying the charts
 */
import React, { useState, useEffect } from 'react';
import MoviePopularityChart from './visualizations/MoviePopularityChart';
import { savePreferences, fetchCombinedData, deletePreference, fetchPreferences } from '../services/api';
import './Dashboard.css'; 
import mdbLogo from '../assets/mdbLogo.png';
import { fetchCodeMappings } from '../services/api'; 



const Dashboard = () => {
  const [combinedData, setCombinedData] = useState(null);
  const [comparisonData, setComparisonData] = useState(null); // New state for comparison data
  const [showComparison, setShowComparison] = useState(false); // State to show/hide comparison options
  const [country, setCountry] = useState('Finland');
  const [genre, setGenre] = useState('Action');
  const [startYear, setStartYear] = useState(2020); 
  const [endYear, setEndYear] = useState(2025); 
  const [country2, setCountry2] = useState('Finland'); // State for second country
  const [genre2, setGenre2] = useState('Action'); // State for second genre
  const [preferences, setPreferences] = useState([]); 
  const [showModal, setShowModal] = useState(false); 
  const [indicator, setIndicator] = useState("46"); // Default indicator set to "Population by 5-year age groups"
  const [showTotalPopulation, setShowTotalPopulation] = useState(true); // State for showing total population
  const [showAgeGroups, setShowAgeGroups] = useState(false); // State for showing age groups
  const [indicatorName, setIndicatorName] = useState("Total Population");
  const [tempIndicator, setTempIndicator] = useState(indicator); // Temporary indicator state
  const [isAgeGroupsChecked, setIsAgeGroupsChecked] = useState(false);
  const [loadingPrimaryData, setLoadingPrimaryData] = useState(false);
  const [loadingComparisonData, setLoadingComparisonData] = useState(false);
  const [showSaveNotification, setShowSaveNotification] = useState(false);
  const [isComparisonFetchEnabled, setIsComparisonFetchEnabled] = useState(true); // New state for disabling/enabling button
  const [codeMappings, setCodeMappings] = useState({
    countries: {},
    genres: {},
    indicators: {}
  });

  useEffect(() => {
    const loadCodeMappings = async () => {
      const mappings = await fetchCodeMappings();
      if (mappings) {
        setCodeMappings(mappings);
        setCountry(Object.keys(mappings.countries)[0]); // Set a default country
        setGenre(Object.keys(mappings.genres)[0]); // Set a default genre
        setIndicator(Object.keys(mappings.indicators)[0]); // Set a default indicator
      }
    };

    loadCodeMappings();
  }, []);
  /**
 * Map of demographic indicator names to their corresponding API codes in the backend.
 */

  const [INDICATOR_CODE_MAP, setIndicatorCodeMap] = useState({});

  useEffect(() => {
    const loadCodeMappings = async () => {
      const mappings = await fetchCodeMappings();
      if (mappings) {
        setCodeMappings(mappings);
  
        // Set dynamic code map
        setIndicatorCodeMap(mappings.indicators);
  
        // Set default values
        const defaultIndicatorName = Object.keys(mappings.indicators)[0];
        const defaultIndicatorCode = mappings.indicators[defaultIndicatorName];
        setTempIndicator(defaultIndicatorCode); // Numeric code
        setIndicatorName(defaultIndicatorName); // Text name
      }
    };
  
    loadCodeMappings();
  }, []);


  // Disable the "Fetch Comparison Data" button when certain fields change
  useEffect(() => {
    setIsComparisonFetchEnabled(false);
  }, [tempIndicator, startYear, endYear])
  
  const enableComparisonFetch = () => {
    setIsComparisonFetchEnabled(true);
  };
  

  /**
   * Handles the change of the selected indicator.
   * @param {Event} e The event triggered by changing the indicator dropdown.
   */
  const handleIndicatorChange = (e) => {
    const selectedIndicator = e.target.value;
    setTempIndicator(selectedIndicator);
      const indicatorName = Object.keys(codeMappings.indicators).find(
    (key) => codeMappings.indicators[key] === selectedIndicator
    );
    setIndicatorName(indicatorName);

  
    if (selectedIndicator === INDICATOR_CODE_MAP["Total Population"]) {
      setShowAgeGroups(isAgeGroupsChecked);
    } else {
      setShowAgeGroups(false);
    }
  };
  
  /**
   * Fetches the combined data for the selected settings (country, genre, year range, indicator).
   * Updates the state with the fetched data or logs errors.
   */
  const handleFetchData = async () => {
    const currentIndicator = typeof tempIndicator === 'string' ? tempIndicator : tempIndicator.toString();
    setCombinedData(null);
    setLoadingPrimaryData(true);  // Start loading
    const params = { country, genre, startYear, endYear, indicator: currentIndicator };
    setIndicator(tempIndicator);
    try {
      // Fetch main data
      const fetchedData = await fetchCombinedData(params);
      if (fetchedData && typeof fetchedData === 'object') {
        const wrappedData = {
          country,
          genre,
          data: fetchedData,
          indicator:indicator,
        };
        setCombinedData(wrappedData);
      } else {
        setCombinedData(null);
      }
  
      // Fetch comparison data if comparison is ongoing
      if (showComparison) {
        await handleFetchComparisonData();
      }
    } catch (error) {
      console.error("Error fetching data:", error);
      setCombinedData(null);
    }finally {
      setLoadingPrimaryData(false);  // End loading
      enableComparisonFetch(); 
    }
  };
  
  /**
   * Fetches the comparison data for the second set of country and genre settings.
   * @param {string} currentIndicator The demographic indicator for comparison.
   */
  const handleFetchComparisonData = async () => {
    const currentIndicator = typeof tempIndicator === 'string' ? tempIndicator : tempIndicator.toString();
    // Pass the correct indicator value
    const params = {
      country: country2,
      genre: genre2,
      startYear,
      endYear,
      indicator: currentIndicator,
    };
    
   // Clear the current comparisonData before fetching new data
   setComparisonData(null);  // Clear the old comparison data
   setLoadingComparisonData(true);  // Start loading comparison data
    try {
      const fetchedData = await fetchCombinedData(params);
      if (fetchedData && typeof fetchedData === 'object') {
        const wrappedData = {
          country: country2,
          genre: genre2,
          data: fetchedData,
        };
        setComparisonData(wrappedData);
      } else {
        setComparisonData(null);
      }
    } catch (error) {
      console.error("Error fetching comparison data:", error);
      setComparisonData(null);
    }finally {
      setLoadingComparisonData(false);  // End loading comparison data
      enableComparisonFetch(); 
    }
  };
  
  /**
   * Removes the comparison data and resets the comparison state.
   */
  const handleRemoveComparison = () => {
    setComparisonData(null);
    setShowComparison(false);
    setCountry2('Finland');
    setGenre2('');
  };
  
  /**
   * Saves the current preferences for the selected settings.
   */
  const handleSavePreferences = async () => {
    const indicatorSaveName = Object.keys(INDICATOR_CODE_MAP).find(
      key => INDICATOR_CODE_MAP[key] === tempIndicator
    );
  
    const params = { country, genre, indicator: indicatorSaveName, startYear, endYear };
    const response = await savePreferences(params); 
     // Show "Preferences Saved" notification
     setShowSaveNotification(true);

     // Hide notification after 3 seconds
     setTimeout(() => setShowSaveNotification(false), 3000);
  };

  // Show the modal to display saved preferences
  const handleShowPreferences = async () => {
    const savedPreferences = await fetchPreferences();
    setPreferences(savedPreferences);
    setShowModal(true); 
  };
  // Closes the modal 
  const closeModal = () => {
    setShowModal(false);
  };

  /**
   * Populates the form fields with the values from a saved preference.
   * @param {Object} pref The selected preference object.
   */
  const handlePopulateFields = (pref) => {
    setCountry(pref.country);
    setGenre(pref.genre);
    const indicatorCode = INDICATOR_CODE_MAP[pref.indicator];
    setTempIndicator(indicatorCode);
    setIndicatorName(pref.indicator);
    setStartYear(pref.startYear);
    setEndYear(pref.endYear);
    closeModal(); 
  };

  /**
   * Deletes a selected preference and refreshes the preferences list.
   * @param {number} index The index of the preference to delete.
   */
  const handleDeletePreference = async (index) => {
    try {
      const response = await deletePreference(index); 
      handleShowPreferences();  
    } catch (error) {
      console.error("Error deleting preference:", error);
    }
  };

  return (
    <div className="dashboard-container">
      <div className="logobar">
      <div className="header">
        <img src={mdbLogo}style={{position:'absolute', marginLeft:'-100px', marginBottom:'46px'}} alt="MDB Logo" className="mdb-logo" />
        <h1 > MovieDemographics Explorer</h1>
        </div>
      </div>
      {/* Notification Popup */}
      {showSaveNotification && (
        <div className="notification-popup">
          <p>Preferences Saved</p>
          </div>
        )}
      {/* Loading modal */}
      {(loadingPrimaryData || loadingComparisonData) && (
        <div className="loading-modal">
          <div className="notification-popup">
            <h3>{loadingPrimaryData ? 'Fetching primary data...' : 'Fetching comparison data...'}</h3>
            {loadingComparisonData && <p>Comparison data is still loading...</p>}
          </div>
        </div>
      )}
      {/* Display Charts */}
      <div className="main-content">
      <div className="movieChart">
          {combinedData ? (
            <MoviePopularityChart
              combinedData={combinedData}
              comparisonData={comparisonData}
              showTotalPopulation={showTotalPopulation}
              showAgeGroups={showAgeGroups}
              indicatorName={indicator}
              codeMappings={INDICATOR_CODE_MAP}
            />
          ) : (
            <p>No data available. Adjust the settings and click "Fetch Data" to load data.</p>
          )}
        </div>

        <div className="preferences-buttons">
          <button type="button" onClick={handleSavePreferences}>Add Favourite</button>
          <button type="button" onClick={handleShowPreferences}>Favourites</button>
          {tempIndicator == indicator && (
          <label>
            <input
              type="checkbox"
              checked={showTotalPopulation}
              onChange={e => setShowTotalPopulation(e.target.checked)}
            />
            {indicatorName}
          </label>
          )}
          {tempIndicator != indicator && (
            <p style={{ color: 'gray', fontStyle: 'italic' }}>You need to fetch in order to see new indicator!</p>
          )} 
            {indicator === INDICATOR_CODE_MAP["Total Population"] && tempIndicator === INDICATOR_CODE_MAP["Total Population"] && (
            <label>
              <input
                type="checkbox"
                checked={showAgeGroups}
                onChange={e => {
                  setShowAgeGroups(e.target.checked);
                  setIsAgeGroupsChecked(e.target.checked);
                }}
              />
              Show Population by Age Groups
            </label>
          )}
          
          {tempIndicator == 46 && indicator != 46 && (
            <p style={{ color: 'gray', fontStyle: 'italic' }}>You need to fetch Total Population</p>
          )}
        </div>
      </div>

      <div className="settings-form">
      <label>Country:</label>
      <select value={country} onChange={(e) => setCountry(e.target.value)}>
        {Object.entries(codeMappings.countries).map(([countryName, code]) => (
          <option key={code} value={countryName}>{countryName}</option>
        ))}
      </select>

      <label>Movie Genre:</label>
      <select value={genre} onChange={(e) => setGenre(e.target.value)}>
        {Object.entries(codeMappings.genres).map(([genreName, code]) => (
          <option key={code} value={genreName}>{genreName}</option>
        ))}
      </select>

        <label>Indicator:</label>
        <select value={tempIndicator} onChange={handleIndicatorChange}>
          {Object.entries(INDICATOR_CODE_MAP).map(([key, value]) => (
            <option key={value} value={value}>{key}</option>
          ))}
        </select>


        <label>Start Year:</label>
        <select value={startYear} onChange={e => setStartYear(parseInt(e.target.value, 10))}>
          {Array.from({ length: 2025 - 1970 + 1 }, (_, i) => 1970 + i).map(year => (
            <option key={year} value={year}>{year}</option>
          ))}
        </select>

        <label>End Year:</label>
        <select value={endYear} onChange={e => setEndYear(parseInt(e.target.value, 10))}>
          {Array.from({ length: 2025 - 1970 + 1 }, (_, i) => 1970 + i).map(year => (
            <option key={year} value={year}>{year}</option>
          ))}
        </select>
        <button type="button" onClick={handleFetchData}>Fetch Primary Data</button>
        </div>
        <div className="settings-form">
        {showComparison && (
          <>
            <h3>Comparison Settings</h3>
            <label>Country:</label>
            <select value={country2} onChange={e => setCountry2(e.target.value)}>
              {Object.entries(codeMappings.countries).map(([countryName, code]) => (
                <option key={code} value={countryName}>{countryName}</option>
              ))}
            </select>

            <label>Movie Genre:</label>
            <select value={genre2} onChange={e => setGenre2(e.target.value)}>
            {Object.entries(codeMappings.genres).map(([genreName, code]) => (
              <option key={code} value={genreName}>{genreName}</option>
            ))}
            </select>
            <button
              type="button"
              onClick={handleFetchComparisonData}
              disabled={!isComparisonFetchEnabled} // Disable button based on state
            >
              Fetch Comparison Data
            </button>
            <button type="button" onClick={handleRemoveComparison}>Remove Comparison</button>
          {/* Conditional message for disabled button */}
          {!isComparisonFetchEnabled && (
              <p style={{ color: 'red', marginTop: '10px' }}>
                Fetch Comparison Data is disabled due to changes in settings of either indicator / start year / end year. Please perform the search via primary fetch button.
              </p>
            )}
          </>
          
        )}

        {combinedData && !showComparison && (
          <button type="button" onClick={() => setShowComparison(true)}>Compare</button>
        )}
        
      </div>

      {/* Modal for Preferences */}
      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <h3>Saved Preferences</h3>
            <ul>
              {preferences.map((pref, index) => (
                <li key={index}>
                  {`Country: ${pref.country}, Genre: ${pref.genre}, Indicator: ${pref.indicator}, Start Date: ${pref.startYear}, End Date: ${pref.endYear}`}
                  <button type="button" onClick={() => handlePopulateFields(pref)}>Use</button>
                  <button type="button" onClick={() => handleDeletePreference(index)}>Delete</button>
                </li>
              ))}
            </ul>
            <button type="button" onClick={closeModal}>Close</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
