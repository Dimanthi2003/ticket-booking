import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/tickets';

export const configureTickets = (data) => axios.post(`${API_BASE_URL}/configure`, data);
export const startSimulation = () => axios.post(`${API_BASE_URL}/start`);
export const stopSimulation = () => axios.post(`${API_BASE_URL}/stop`);
