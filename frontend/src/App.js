import "./App.css";
import LandingPage from "./pages/LandingPage";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LogPage from "./pages/LogPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/log" element={<LogPage />} />
      </Routes>
    </Router>
  );
}

export default App;
