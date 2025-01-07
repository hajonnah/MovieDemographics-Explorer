import React, { useState, useEffect } from 'react';
import { Line, Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, PointElement, LineElement, Title, Tooltip, Legend);

const MoviePopularityChart = ({ combinedData, comparisonData, showTotalPopulation, showAgeGroups, indicatorName, codeMappings }) => {
  const [chartData, setChartData] = useState(null);
  // Get the indicator's display text from codeMappings
  const indicatorText = Object.keys(codeMappings).find(
    (key) => codeMappings[key] === indicatorName
  );

  useEffect(() => {
    // Reset chart data when new data comes in
    if (!combinedData || !combinedData.data || Object.keys(combinedData.data).length === 0) {
      setChartData(null);
      return;
    }

    // Determine if the same country is being compared
    const isSameCountry = comparisonData && combinedData.country === comparisonData.country;

    // Initialize datasets
    let datasets = [];

    // Filter countries and add data
    const countries = [combinedData, comparisonData].filter(Boolean);

    countries.forEach((countryData, index) => {
      const { data, country, genre } = countryData;
      const years = Object.keys(data).map(year => parseInt(year, 10));
      const movieCounts = years.map(year => data[year].howManyMovies);

      // Add movie count dataset (as line)
      datasets.push({
        label: `${country} (${genre}) - Number of Movies`,
        data: movieCounts,
        borderColor: `rgba(${75 + index * 50}, ${192 - index * 50}, 192, 0.7)`,
        borderWidth: 2,
        fill: false,
        yAxisID: 'y-axis-movies',
        type: 'line',
      });

      // Add population datasets if selected (as bar), but only once if countries are the same
      if (showTotalPopulation && (!isSameCountry || index === 0)) {
        const totalPopulations = years.map(year => data[year].populationInformation.indicatorValue);
        datasets.push({
          label: `${country} - ${indicatorText}`,
          data: totalPopulations,
          backgroundColor: `rgba(${255 - index * 50}, ${99 + index * 50}, 132, 0.7)`,
          yAxisID: 'y-axis-population',
          type: 'bar',
        });
      }

      if (showAgeGroups) {
        const ageGroups = ['0-14', '15-24', '25-64', '+65'];
        const ageGroupPopulations = ageGroups.map(group => {
          return years.map(year => {
            const groupData = data[year].populationInformation.populationAges[group];
            return groupData ? groupData.bothSexesPopulation : 0;
          });
        });

        ageGroups.forEach((group, idx) => {
          datasets.push({
            label: `${country} - Population ${group}`,
            data: ageGroupPopulations[idx],
            backgroundColor: `rgba(${100 + idx * 50}, ${50 + idx * 50}, 150, 0.7)`,
            yAxisID: 'y-axis-population',
            type: 'bar',
          });
        });
      }
    });

    // Set chart data, clearing old data first
    setChartData({
      labels: Object.keys(combinedData.data).map(year => parseInt(year, 10)),
      datasets: datasets,
    });
  }, [combinedData, comparisonData, showTotalPopulation, showAgeGroups, indicatorName, codeMappings]);

  if (!chartData) {
    return <p>No data available to display</p>;
  }

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: `Movie Popularity and ${indicatorText} by Year`,
      },
    },
    scales: {
      'y-axis-movies': {
        type: 'linear',
        position: 'left',
        title: {
          display: true,
          text: 'Number of Movies',
        },
      },
      'y-axis-population': {
        type: 'linear',
        position: 'right',
        title: {
          display: true,
          text: indicatorText || 'Population',
        },
        grid: {
          drawOnChartArea: false,
        },
      },
    },
  };

  return (
    <div>
      <h3>Movie Popularity and Demographics Overview</h3>
      <Line data={chartData} options={options} />
    </div>
  );
};

export default MoviePopularityChart;
