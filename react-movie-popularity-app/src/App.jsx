import React, { useState } from 'react';
import Dashboard from './components/Dashboard';
import './App.css';

function App() {
  const [activeTab, setActiveTab] = useState('dashboard');

  return (
    <div>
      {/* Navigation Tabs */}
      <div className="tabs">
        <button onClick={() => setActiveTab('dashboard')}>Dashboard</button>
      </div>

      {/* Render the selected tab */}
      <div className="content">
        {activeTab === 'dashboard' && <Dashboard />}
      </div>
    </div>
  );
}

export default App;
