import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage";
import PaymentPage from "./pages/PaymentPage";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css"; // Make sure to import this

function App() {
    return (
        <Router>

            <Routes>
                <Route path="/" element={<MainPage />} />
                <Route path="/payment" element={<PaymentPage />} />
            </Routes>
            <ToastContainer position="top-right" autoClose={5000} hideProgressBar={false} /> {/* Place ToastContainer inside Router */}
        </Router>
    );
}
export default App;