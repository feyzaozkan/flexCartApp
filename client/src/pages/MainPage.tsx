import React from "react";
import { Button, Box, Container, Avatar, Stack, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import UserDialog from "../components/UserDialog";
import ProductGrid from "../components/ProductGrid";
import CartList from "../components/CartList";


function MainPage() {
    const [openUserDialog, setOpenUserDialog] = React.useState(false);
    const [selectedUser, setSelectedUser] = React.useState<string | null>(null);
    const [cartItems, setCartItems] = React.useState<CartItem[]>([]);
    const navigate = useNavigate();

    const users = [
        { id: 1, name: "Alice" },
        { id: 2, name: "Bob" },
        { id: 3, name: "Charlie" },
    ];

    interface Product {
        id: number;
        name: string;
        price: number;
    }

    interface CartItem {
        id: number;
        name: string;
        quantity: number;
        price: number;
    }

    const products: Product[] = [
        { id: 1, name: "Cool Shirt", price: 29.99 },
        { id: 2, name: "Sneakers", price: 59.99 },
        { id: 3, name: "Backpack", price: 45.00 },
        { id: 4, name: "Sunglasses", price: 19.99 },
    ];

    const handleSelect = (user: { id: number; name: string }) => {
        setSelectedUser(user.name);
        setOpenUserDialog(false);
    };

    const handleAddToCart = (product: Product) => {
        setCartItems((prevCart) => {
            const existingItem = prevCart.find((item) => item.id === product.id);

            if (existingItem) {
                return prevCart.map((item) =>
                    item.id === product.id
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            } else {
                return [...prevCart, { id: product.id, name: product.name, price: product.price, quantity: 1 }];
            }
        });
    };

    const handleRemoveItem = (id: number) => {
        setCartItems((prevCart) => prevCart.filter((item) => item.id !== id));
    };

    const handlePay = () => {
        if (!isCartEmpty) {
            navigate("/payment", { state: { cartItems } });
        }
    };

    const isCartEmpty = cartItems.length === 0;

    return (
        <Box className="h-screen w-screen">
            <Box className="flex flex-row justify-between w-full h-full">
                <Box className="bg-blue-300 flex-2 w-full">
                    <Container>
                        <Box className="mt-10">
                            <Box className="flex flex-row justify-between">
                                <Avatar>MA</Avatar>
                                {selectedUser && (
                                    <Typography variant="h6">
                                        Selected User: {selectedUser}
                                    </Typography>
                                )}
                                <Button variant="contained" onClick={() => setOpenUserDialog(true)}>Change User</Button>
                                <UserDialog
                                    open={openUserDialog}
                                    users={users}
                                    onClose={() => setOpenUserDialog(false)}
                                    onSelect={handleSelect}
                                />
                            </Box>
                            <Box className="mt-10">
                                <Stack spacing={2}>
                                    <Box className="flex flex-row gap-20">
                                        <Typography className="flex-4">ID:</Typography>
                                        <Typography className="flex-8">MHMT</Typography>
                                    </Box>
                                    <Box className="flex flex-row gap-20">
                                        <Typography className="flex-4">Name:</Typography>
                                        <Typography className="flex-8">Mahmut</Typography>
                                    </Box>
                                    <Box className="flex flex-row gap-20">
                                        <Typography className="flex-4">Type:</Typography>
                                        <Typography className="flex-8">Premium</Typography>
                                    </Box>
                                </Stack>
                            </Box>
                        </Box>
                    </Container>
                </Box>
                <Box className="bg-red-300 flex-4">
                    <Container>
                        <Box className="mt-10 text-center">
                            <Typography variant="h3">
                                FlexCart
                            </Typography>
                            <Typography variant="h6">
                                Flexible Discount and Shopping Cart Engine
                            </Typography>
                        </Box>
                        <Box>
                            <ProductGrid products={products} onAddToCart={handleAddToCart} />
                        </Box>
                    </Container>
                </Box>
                <Box className="bg-green-300 flex-2">
                    <Container className="flex flex-col justify-between h-full">
                        <Box className="mt-10 flex justify-center">
                            <Typography variant="h5" className="mb-4 text-center">Cart</Typography>
                        </Box>
                        <Box className="flex">
                            <CartList items={cartItems} onRemoveItem={handleRemoveItem} />
                        </Box>
                        <Box className="text-center flex mb-10">
                            <Button variant="contained" disabled={isCartEmpty} className="w-full justify-end" onClick={handlePay}>Pay</Button>
                        </Box>
                    </Container>
                </Box>
            </Box>
        </Box>
    );
}

export default MainPage;