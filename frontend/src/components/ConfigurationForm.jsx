import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./ConfigurationForm.css";

const ConfigurationForm = () => {
  const [config, setConfig] = useState({
    maxTickets: "",
    totalTickets: "",
    ticketReleaseRate: "",
    customerRetrievalRate: "",
  });

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/api/tickets/configure", config);
      navigate("/dashboard");
    } catch (error) {
      alert("Failed to submit configuration");
    }
  };

  return (
      <form onSubmit={handleSubmit}>
        <input
            type="number"
            placeholder="Max Tickets"
            value={config.maxTickets}
            onChange={(e) => setConfig({ ...config, maxTickets: e.target.value })}
        />
        <input
            type="number"
            placeholder="Total Tickets"
            value={config.totalTickets}
            onChange={(e) => setConfig({ ...config, totalTickets: e.target.value })}
        />
        <input
            type="number"
            placeholder="Ticket Release Rate"
            value={config.ticketReleaseRate}
            onChange={(e) => setConfig({ ...config, ticketReleaseRate: e.target.value })}
        />
        <input
            type="number"
            placeholder="Customer Retrieval Rate"
            value={config.customerRetrievalRate}
            onChange={(e) => setConfig({ ...config, customerRetrievalRate: e.target.value })}
        />
        <button type="submit">Submit</button>
      </form>
  );
};

export default ConfigurationPage;