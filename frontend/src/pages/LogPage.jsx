import React, { useState } from "react";
import "./LogPage.css";

const LogPage = () => {
  const [isRunning, setIsRunning] = useState(false);
  const [ticketCount, setTicketCount] = useState(0);
  const [logs, setLogs] = useState([]);

  const handleStart = () => {
    setIsRunning(true);
    setLogs((prevLogs) => [...prevLogs, "Process started"]);
  };

  const handleStop = () => {
    setIsRunning(false);
    setLogs((prevLogs) => [...prevLogs, "Process stopped"]);
  };

  return (
    <div className="container">
      <div className="header">
        <button
          className={`control-btn ${isRunning ? "disabled" : ""}`}
          onClick={handleStart}
          disabled={isRunning}
        >
          Start
        </button>
        <button
          className={`control-btn ${!isRunning ? "disabled" : ""}`}
          onClick={handleStop}
          disabled={!isRunning}
        >
          Stop
        </button>
        <div className="ticket-info">
          <span>Ticket availability:</span>
          <span className="ticket-count">{ticketCount} left</span>
        </div>
      </div>

      <div className="logs-section">
        <h3>Logs</h3>
        <div className="logs-box">
          {logs.length === 0 ? (
            <p>No logs available</p>
          ) : (
            logs.map((log, index) => <p key={index}>{log}</p>)
          )}
        </div>
      </div>
    </div>
  );
};

export default LogPage;
