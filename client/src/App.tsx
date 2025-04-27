import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import PaymentPage from "./pages/PaymentPage";

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/payment" element={<PaymentPage />} />
        </Routes>
      </Router>
  );
}
export default App;