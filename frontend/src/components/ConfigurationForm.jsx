import React, { useState } from "react";
import "./ConfigurationForm.css";

const ConfigurationForm = () => {
  const [formData, setFormData] = useState({
    maxTickets: "",
    totalTickets: "",
    releaseRate: "",
    retrievalRate: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    console.log("Form submitted:", formData);
  };

  return (
    <div className="configuration-container">
      <h1>Configuration</h1>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="maxTickets">Maximum number of tickets</label>
          <input
            type="number"
            id="maxTickets"
            name="maxTickets"
            value={formData.maxTickets}
            onChange={handleChange}
            placeholder="Should be more than the total number of tickets"
          />
          <small>Should be more than the total number of tickets</small>
        </div>
        <div className="form-group">
          <label htmlFor="totalTickets">Total number of tickets</label>
          <input
            type="number"
            id="totalTickets"
            name="totalTickets"
            value={formData.totalTickets}
            onChange={handleChange}
            placeholder="Should be a value between 1 and 10,000"
          />
          <small>Should be a value between 1 and 10,000</small>
        </div>
        <div className="form-group">
          <label htmlFor="releaseRate">Ticket release rate</label>
          <input
            type="number"
            id="releaseRate"
            name="releaseRate"
            value={formData.releaseRate}
            onChange={handleChange}
            placeholder="Should be less than total number of tickets"
          />
          <small>Should be less than total number of tickets</small>
        </div>
        <div className="form-group">
          <label htmlFor="retrievalRate">Customer retrieval rate</label>
          <input
            type="number"
            id="retrievalRate"
            name="retrievalRate"
            value={formData.retrievalRate}
            onChange={handleChange}
            placeholder="Should be less than the ticket release rate"
          />
          <small>Should be less than the ticket release rate</small>
        </div>
        <button type="submit" className="submit-button">
          Submit
        </button>
      </form>
    </div>
  );
};

export default ConfigurationForm;
