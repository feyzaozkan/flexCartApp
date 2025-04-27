import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Typography, Stack, Paper, Divider, Button, Box, Container } from "@mui/material";
import { ToastContainer, toast } from 'react-toastify';
import axios from "axios";

interface CartItem {
    id: number;
    name: string;
    quantity: number;
    price: number;
}

interface User {
    id: string;
    name: string;
    lastYearSpent: number;
    registeredAt: string;
    totalSpent: number;
    type: string;
}

interface LocationState {
    discount: any;
    selectedUser: User;
    cartItems: CartItem[];
}

const PaymentPage: React.FC = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const state = location.state as LocationState;

    const selectedUser = state?.selectedUser || null;
    const cartItems = state?.cartItems || [];
    const discount = state?.discount || {};

    const totalPrice = cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
    const discountAmount = discount.campaignDetails[0]?.discount || 0;
    const freeShipping = discount.shippingFee;
    const appliedDiscount = discount.campaignDetails[0]?.discount === 0 ? "No" : discount.campaignDetails[0]?.name;
    const validDiscounts = discount.campaignDetails.filter((campaign: { discount: number; }) => campaign.discount > 0).slice(1);

    const handleBack = () => {
        navigate(-1);
    };

    const handleCompletePayment = async () => {
        const requestBody = {
            customerId: selectedUser.id,
            amountToPay: (totalPrice - discountAmount + freeShipping).toFixed(2),
        };

        try {
            const response = await axios.patch("http://localhost:8080/shopping/completePayment", requestBody);

            const result = response.data;

            toast.success("Payment completed successfully.", {
                autoClose: 2000
            });
            setTimeout(() => {
                navigate(-1)
            }, 2000);

        } catch (error) {
            console.error("Failed to apply discount:", error);
            alert("Payment failed. Please try again.");
        }

    };

    return (
        <Box className="min-h-screen w-screen bg-[#494d7e] overflow-y-auto">
            <Box className="flex flex-row justify-between w-full h-full">
                <Box className="bg-[#494d7e] flex-6 w-full">
                    <Container>
                        <Box className="mt-10 flex flex-col gap-10">
                            <Box className="flex flex-col">
                                <Box>
                                    <Button variant="outlined" style={{ color: "#fbf5ef" }} onClick={handleBack}>
                                        Back
                                    </Button>
                                </Box>
                                <Box className="text-center">
                                    <Typography variant="h4" className="text-[#f2d3ab]" gutterBottom>
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
                                        <Typography variant="h6" className="text-[#fbf5ef] bg-[#272744] p-2 rounded-lg">Total:</Typography>
                                        <Typography variant="h6" className="text-[#fbf5ef] bg-[#272744] p-2 rounded-lg">
                                            ${totalPrice.toFixed(2)}
                                        </Typography>
                                    </div>
                                    <div className="flex justify-between items-center p-2">
                                        <Typography variant="h6" className="text-[#fbf5ef] bg-[#272744] p-2 rounded-lg">Discount Amount:</Typography>
                                        <Typography variant="h6" className="text-[#f2d3ab] bg-[#272744] p-2 rounded-lg">
                                            -${discountAmount.toFixed(2)}
                                        </Typography>
                                    </div>
                                    <div className="flex justify-between items-center p-2">
                                        <Typography variant="h6" className="text-[#fbf5ef] bg-[#272744] p-2 rounded-lg">Shipping Price:</Typography>
                                        <Typography variant="h6" className="text-[#fbf5ef] bg-[#272744] p-2 rounded-lg">
                                            ${freeShipping}
                                        </Typography>
                                    </div>
                                    <div className="flex justify-between items-center p-2">
                                        <Typography variant="h6" className="text-[#fbf5ef] bg-[#272744] p-2 rounded-lg">Amount to Pay:</Typography>
                                        <Typography variant="h6" className="text-[#fbf5ef] bg-[#272744] p-2 rounded-lg">
                                            ${(totalPrice - discountAmount + freeShipping).toFixed(2)}
                                        </Typography>
                                    </div>
                                </Stack>
                            </Box>

                            <Box className="flex mb-10 justify-center">
                                <Button variant="contained" className="max-w-lg justify-end" onClick={handleCompletePayment} style={{ backgroundColor: "#f2d3ab", color: "#272744" }}>Complete Payment</Button>
                                <ToastContainer />
                            </Box>
                        </Box>
                    </Container>
                </Box>
                <Box className="bg-[#494d7e] flex-6 w-full">
                    <Container>
                        <Box className="mt-10 flex flex-col gap-10 text-[#fbf5ef] bg-[#272744] p-2 rounded-lg">
                            <Box className="flex items-center p-2 gap-10">
                                <Typography variant="h6">Applied Discount:</Typography>
                                <Typography variant="h6" className="text-[#f2d3ab]">
                                    {appliedDiscount}
                                </Typography>
                            </Box>
                            <Box className="flex flex-col p-2 gap-10">
                                <Typography variant="h6" className="mb-10">Other Eligible Discounts</Typography>
                                <Stack spacing={2} className="w-full max-w-md text-[#c69fa5]">
                                    {validDiscounts.map((discount: { id: React.Key | null; name: string; }) => (
                                        <Typography key={discount.id}>{discount.name}</Typography>
                                    ))}
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