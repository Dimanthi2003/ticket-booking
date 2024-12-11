import { useState, useEffect } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import axios from "axios";
import './Dashboard.css';


const Dashboard = () => {
    const [logs, setLogs] = useState([]);
    const [ticketAvailability, setTicketAvailability] = useState(0);
    const [connectionStatus, setConnectionStatus] = useState("Disconnected");
    const [isProcessing, setIsProcessing] = useState(false);

    const [error, setError] = useState("");

    useEffect(() => {
        const fetchStatus = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/tickets/status");
                if (response.data.isRunning) {
                    setConnectionStatus("Running");
                } else {
                    setConnectionStatus("Stopped");
                }
            } catch (error) {
                console.error("Failed to fetch status:", error);
            }
        };

        fetchStatus();
    }, []);

    useEffect(() => {
        const socket = new SockJS("http://localhost:8080/ticket-websocket");
        const stompClient = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                setConnectionStatus("Connected");
                stompClient.subscribe("/topic/tickets", (message) => {
                    const log = message.body;
                    setLogs((prevLogs) => [...prevLogs, log]);
                    const match = log.match(/Total available: (\d+)/);
                    if (match) setTicketAvailability(Number(match[1]));
                });
            },
            onStompError: (frame) => {
                console.error("WebSocket error: ", frame.headers['message']);
            },
            onDisconnect: () => setConnectionStatus("Disconnected"),
        });

        stompClient.activate();

        return () => {
            stompClient.deactivate();
            setConnectionStatus("Disconnected");
            setLogs([]);
            setTicketAvailability(0);
        };
    }, []);

    const handleStart = async () => {
        setError(""); // Reset error state
        try {
            await axios.post("http://localhost:8080/api/tickets/start");
        } catch (error) {
            setError("Failed to start ticket management");
        }
    };

    const handleStop = async () => {
        setIsProcessing(true);
        try {
            await axios.post("http://localhost:8080/api/tickets/stop");
        } catch (error) {
            console.error("Failed to stop:", error);
        } finally {
            setIsProcessing(false);
        }
    };

    return (
        <div>
            <h2>Dashboard</h2>
            <h4>Connection Status: {connectionStatus}</h4>
            <button onClick={handleStart} disabled={isProcessing}>Start</button>
            <button onClick={handleStop} disabled={isProcessing}>Stop</button>
            <h3>Ticket Availability: {ticketAvailability}</h3>
            <div style={{ maxHeight: "300px", overflowY: "auto", border: "1px solid #ccc", padding: "10px" }}>
                <h4>Logs</h4>
                {logs.map((log, index) => (
                    <p key={index}>{new Date().toLocaleTimeString()}: {log}</p>
                ))}
                {error && <p style={{ color: "red" }}>{error}</p>}
            </div>
        </div>
    );
};

export default Dashboard;