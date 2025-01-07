/**
 * Fetches combined data for movies and population from the backend.
 * 
 * @param {Object} params - The parameters used for fetching the combined data, including country, genre, startYear, endYear, and indicator.
 * @returns {Object|null} The combined data fetched from the backend or null if an error occurs.
 */
export const fetchCombinedData = async (params) => {

  try {
    const query = new URLSearchParams(params).toString();
    const response = await fetch(`http://localhost:8080/combinedData?${query}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'cors',
    });
    
    if (!response.ok) {
      throw new Error(`Error fetching data: ${response.statusText}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching combined data:", error);
    return null;
  }
};

/**
 * Saves user preferences to the backend.
 * 
 * @param {Object} params - The preferences to be saved, including country, genre, startYear, and endYear.
 * @returns {Object} The response from the backend after saving preferences.
 */
export const savePreferences = async (params) => {
  try {
    const response = await fetch('http://localhost:8080/api/preferences', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'cors',
      body: JSON.stringify(params),
    });
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error saving preferences:", error);
  }
};

/**
 * Fetches saved user preferences from the backend.
 * 
 * @returns {Array<Object>} The list of saved user preferences fetched from the backend.
 */
export const fetchPreferences = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/preferences', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'cors',
    });
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching preferences:", error);
  }
};

/**
 * Deletes a user preference at a specified index.
 * 
 * @param {number} index - The index of the preference to be deleted.
 * @returns {Object} The response from the backend after deleting the preference.
 */
export const deletePreference = async (index) => {
  try {
    const response = await fetch(`http://localhost:8080/api/preferences?index=${index}`, {
      method: 'DELETE',
      mode: 'cors',
    });
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error deleting preference:", error);
  }
};

/**
 * Fetches code mappings from the backend for countries, genres, and indicators.
 * 
 * @returns {Object|null} The code mappings fetched from the backend or null if an error occurs.
 */
export const fetchCodeMappings = async () => {
  try {
    const response = await fetch("http://localhost:8080/api/codeMappings", {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'cors',
    });

    if (!response.ok) {
      throw new Error(`Error fetching code mappings: ${response.statusText}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching code mappings:", error);
    return null;
  }
};