
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ConfigurationPage from "./pages/ConfigurationPage";
import Dashboard from "./pages/Dashboard";

const App = () => (
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<ConfigurationPage />} />
            <Route path="/dashboard" element={<Dashboard />} />
        </Routes>
    </BrowserRouter>
);

export default App;
