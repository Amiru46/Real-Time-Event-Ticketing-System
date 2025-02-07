import React, { useState } from "react";
import "./SimulationStatus.css";  // Importing a new CSS file for custom styles

const SimulationStatus = () => {
  const [status, setStatus] = useState("Idle");
  const [loading, setLoading] = useState(false);

  // State for user input
  const [inputs, setInputs] = useState({
    maxTicketCapacity: "",
    totalNoOfTickets: "",
    ticketReleaseRate: "",
    customerRetrievalRate: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setInputs((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const startSimulation = async () => {
    const { maxTicketCapacity, totalNoOfTickets, ticketReleaseRate, customerRetrievalRate } = inputs;

    if (!maxTicketCapacity || !totalNoOfTickets || !ticketReleaseRate || !customerRetrievalRate) {
      setStatus("Please fill all fields");
      return;
    }

    setLoading(true);
    setStatus("Starting...");

    try {
      const response = await fetch(
        `http://localhost:8081/api/semiApp/start?maxTicketCapacity=${maxTicketCapacity}&totalNoOfTickets=${totalNoOfTickets}&ticketReleaseRate=${ticketReleaseRate}&customerRetrievalRate=${customerRetrievalRate}`,
        {
          method: "PUT",
        }
      );

      if (response.ok) {
        const data = await response.text();
        setStatus(data);
      } else {
        setStatus("Failed to start simulation");
      }
    } catch (error) {
      console.error("Error starting simulation:", error);
      setStatus("Error starting simulation");
    } finally {
      setLoading(false);
    }
  };

  const stopSimulation = async () => {
    setLoading(true);
    setStatus("Stopping...");

    try {
      const response = await fetch("/api/simulation/stop", {
        method: "POST",
      });

      if (response.ok) {
        const data = await response.text();
        setStatus(data);
      } else {
        setStatus("stopping");
      }
    } catch (error) {
      console.error("Error stopping simulation:", error);
      setStatus("Error stopping simulation");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="simulation-status">
      <h3>Ticket Booking System Simulation</h3>
      <div className="input-container">
        <fieldset>
          <legend><b>User Inputs</b></legend>
          <div className="inputs">
            <label>
              Max Ticket Capacity:
              <input
                type="number"
                name="maxTicketCapacity"
                value={inputs.maxTicketCapacity}
                onChange={handleChange}
                placeholder="Enter max capacity"
              />
            </label>
            <label>
              Total Tickets:
              <input
                type="number"
                name="totalNoOfTickets"
                value={inputs.totalNoOfTickets}
                onChange={handleChange}
                placeholder="Enter total tickets"
              />
            </label>
            <label>
              Ticket Release Rate:
              <input
                type="number"
                name="ticketReleaseRate"
                value={inputs.ticketReleaseRate}
                onChange={handleChange}
                placeholder="Enter release rate"
              />
            </label>
            <label>
              Customer Retrieval Rate:
              <input
                type="number"
                name="customerRetrievalRate"
                value={inputs.customerRetrievalRate}
                onChange={handleChange}
                placeholder="Enter retrieval rate"
              />
            </label>
          </div>
        </fieldset>
      </div>

      <div className="status-container">
        {loading ? <p className="loading">Loading...</p> : <p>Status: {status}</p>}
      </div>

      <div className="actions">
        <button onClick={startSimulation} disabled={loading}>
          Start Simulation
        </button>
        <button onClick={stopSimulation} disabled={loading}>
          Stop Simulation
        </button>
      </div>
    </div>
  );
};

export default SimulationStatus;
