
import React, { useState } from 'react';

const ConfigurationForm = () => {
  const [config, setConfig] = useState({
    maxTicketCapacity: 0,
    totalNoOfTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setConfig({ ...config, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Call API to start the simulation
    fetch('http://localhost:8081/api/semiApp/start', {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(config),
    })
      .then((res) => res.json())
      .then((data) => console.log(data))
      .catch((err) => console.error(err));
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="number"
        name="maxTicketCapacity"
        placeholder="Max Ticket Capacity"
        onChange={handleChange}
      />
      <input
        type="number"
        name="totalNoOfTickets"
        placeholder="Total Tickets"
        onChange={handleChange}
      />
      <input
        type="number"
        name="ticketReleaseRate"
        placeholder="Ticket Release Rate"
        onChange={handleChange}
      />
      <input
        type="number"
        name="customerRetrievalRate"
        placeholder="Customer Retrieval Rate"
        onChange={handleChange}
      />
      
    </form>
  );
};

export default ConfigurationForm;
