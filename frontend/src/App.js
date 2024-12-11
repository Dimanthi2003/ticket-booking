import { BrowserRouter, Routes, Route } from "react-router-dom";
import ConfigurationPage from "./src/components/ConfigurationPage.jsx";
import Dashboard from "./src/components/Dashboard.jsx";

const App = () => (
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<ConfigurationPage />} />
            <Route path="/dashboard" element={<Dashboard />} />
        </Routes>
    </BrowserRouter>
);

export default App;
