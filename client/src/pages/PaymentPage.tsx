import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Typography, Stack, Paper, Divider, Button } from "@mui/material";

interface CartItem {
    id: number;
    name: string;
    quantity: number;
    price: number;
}

interface LocationState {
    cartItems: CartItem[];
}

const PaymentPage: React.FC = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const state = location.state as LocationState;

    const cartItems = state?.cartItems || [];

    const totalPrice = cartItems.reduce((total, item) => total + item.price * item.quantity, 0);

    const handleBack = () => {
        navigate(-1);
    };

    return (
        <div className="min-h-screen p-8 flex flex-col items-center bg-gray-100">
            <Button variant="outlined" onClick={handleBack}>
                Back
            </Button>
            <Typography variant="h4" gutterBottom>
                Payment Page
            </Typography>

            <Stack spacing={2} className="w-full max-w-md">
                {cartItems.map((item) => (
                    <Paper key={item.id} className="p-4 flex justify-between items-center">
                        <div>
                            <Typography variant="body1">{item.name}</Typography>
                            <Typography variant="body2" color="text.secondary">
                                Quantity: {item.quantity}
                            </Typography>
                        </div>
                        <Typography variant="body1">
                            ${(item.price * item.quantity).toFixed(2)}
                        </Typography>
                    </Paper>
                ))}

                <Divider />

                <div className="flex justify-between items-center p-2">
                    <Typography variant="h6">Total:</Typography>
                    <Typography variant="h6">
                        ${totalPrice.toFixed(2)}
                    </Typography>
                </div>
            </Stack>
        </div>
    );
};

export default PaymentPage;