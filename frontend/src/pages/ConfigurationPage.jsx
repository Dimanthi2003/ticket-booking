import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import './ConfigurationPage.css';

const ConfigurationPage = () => {
    const [config, setConfig] = useState({
        maxTickets: "",
        totalTickets: "",
        ticketReleaseRate: "",
        customerRetrievalRate: "",
    });

    const [error, setError] = useState("");
    const navigate = useNavigate();

    const validate = () => {
        const integerData = {
            maxTickets: parseInt(config.maxTickets, 10),
            totalTickets: parseInt(config.totalTickets, 10),
            releaseRate: parseInt(config.ticketReleaseRate, 10),
            retrievalRate: parseInt(config.customerRetrievalRate, 10),
        };

        if (
            !Number.isInteger(integerData.maxTickets) ||
            !Number.isInteger(integerData.totalTickets) ||
            !Number.isInteger(integerData.releaseRate) ||
            !Number.isInteger(integerData.retrievalRate)
        ) {
            setError("All values must be valid integers.");
            return false;
        }

        if (integerData.totalTickets > integerData.maxTickets) {
            setError("Total number of tickets should be less than or equal to the maximum number of tickets.");
            return false;
        }

        if (integerData.releaseRate > integerData.totalTickets) {
            setError("Release rate should be less than or equal to total number of tickets.");
            return false;
        }

        if (integerData.retrievalRate > integerData.releaseRate) {
            setError("Retrieval rate should be less than or equal to the ticket release rate.");
            return false;
        }

        setError(""); // Clear any previous errors
        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validate()) return;

        try {
            await axios.post("http://localhost:8080/api/tickets/configure", config);
            navigate("/dashboard");
        } catch (error) {
            setError("Failed to submit configuration");
        }
    };

    return (
        <div className="form-container">
            <h1>Ticket Configuration Form</h1>
            <form onSubmit={handleSubmit}>
                {error && <div className="error-message">{error}</div>}
                <div className="form-group">
                    <label htmlFor="maxTickets">Max Tickets</label>
                    <input
                        id="maxTickets"
                        type="number"
                        placeholder="Should be more than the total number of tickets"
                        value={config.maxTickets}
                        onChange={(e) => setConfig({ ...config, maxTickets: e.target.value })}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="totalTickets">Total Tickets</label>
                    <input
                        id="totalTickets"
                        type="number"
                        placeholder="Should be a value between 1 and 10,000"
                        value={config.totalTickets}
                        onChange={(e) => setConfig({ ...config, totalTickets: e.target.value })}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="ticketReleaseRate">Ticket Release Rate</label>
                    <input
                        id="ticketReleaseRate"
                        type="number"
                        placeholder="Should be less than total number of tickets"
                        value={config.ticketReleaseRate}
                        onChange={(e) => setConfig({ ...config, ticketReleaseRate: e.target.value })}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="customerRetrievalRate">Customer Retrieval Rate</label>
                    <input
                        id="customerRetrievalRate"
                        type="number"
                        placeholder="Should be less than the ticket release rate"
                        value={config.customerRetrievalRate}
                        onChange={(e) => setConfig({ ...config, customerRetrievalRate: e.target.value })}
                    />
                </div>
                <button type="submit" className="submit-button">Submit</button>
            </form>
        </div>
    );
};

export default ConfigurationPage;
