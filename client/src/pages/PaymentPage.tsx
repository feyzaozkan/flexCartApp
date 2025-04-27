import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Typography, Stack, Paper, Divider, Button, Box, Container } from "@mui/material";

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
    const discountAmount = 10;
    const freeShipping = false;
    const appliedDiscount = "Multi-buy discount";

    const handleBack = () => {
        navigate(-1);
    };

    const handleCompletePayment = () => {
        navigate(-1);
    };

    return (
        <Box className="h-screen w-screen">
            <Box className="flex flex-row justify-between w-full h-full">
                <Box className="bg-red-300 flex-6 w-full">
                    <Container>
                        <Box className="mt-10 flex flex-col gap-10">
                            <Box className="flex flex-col">
                                <Box>
                                    <Button variant="outlined" onClick={handleBack}>
                                        Back
                                    </Button>
                                </Box>
                                <Box className="text-center">
                                    <Typography variant="h4" gutterBottom>
                                        Payment Page
                                    </Typography>
                                </Box>
                            </Box>

                            <Box className="flex flex-col items-center">
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
                                    <div className="flex justify-between items-center p-2">
                                        <Typography variant="h6">Discount Amount:</Typography>
                                        <Typography variant="h6">
                                            -${discountAmount.toFixed(2)}
                                        </Typography>
                                    </div>
                                    <div className="flex justify-between items-center p-2">
                                        <Typography variant="h6">Amount to Pay:</Typography>
                                        <Typography variant="h6">
                                            ${(totalPrice - discountAmount).toFixed(2)}
                                        </Typography>
                                    </div>
                                    <div className="flex justify-between items-center p-2">
                                        <Typography variant="h6">Free shipping:</Typography>
                                        <Typography variant="h6">
                                            {freeShipping ? "Yes" : "No"}
                                        </Typography>
                                    </div>
                                </Stack>
                            </Box>

                            <Box className="flex mb-10 justify-center">
                                <Button variant="contained" className="max-w-lg justify-end" onClick={handleCompletePayment}>Complete Payment</Button>
                            </Box>
                        </Box>
                    </Container>
                </Box>
                <Box className="bg-blue-300 flex-6 w-full">
                    <Container>
                        <Box className="mt-10 flex flex-col gap-10">
                            <Box className="flex items-center p-2 gap-10">
                                <Typography variant="h6">Applied Discount:</Typography>
                                <Typography variant="h6">
                                    {appliedDiscount}
                                </Typography>
                            </Box>
                            <Box className="flex flex-col p-2 gap-10">
                                <Typography variant="h6" className="mb-10">Other Eligible Discounts</Typography>
                                <Stack spacing={2} className="w-full max-w-md">
                                    <Typography className="">discount 1</Typography>
                                    <Typography>discount 2</Typography>
                                    <Typography>discount 3</Typography>
                                </Stack>
                            </Box>
                        </Box>
                    </Container>
                </Box>
            </Box>
        </Box>
    );
};

export default PaymentPage;