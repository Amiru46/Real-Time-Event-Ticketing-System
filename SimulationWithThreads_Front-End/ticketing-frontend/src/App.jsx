import React from 'react';
import Navbar from './components/Navbar';
import ConfigurationForm from './components/ConfigurationForm';
import SimulationStatus from './components/SimulationStatus';

const App = () => {
  return (
    <div>
      <Navbar />
      
      <SimulationStatus />
    </div>
  );
};

export default App;
