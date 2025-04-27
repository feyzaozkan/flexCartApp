import React from "react";
import { Stack, Typography, Paper, IconButton, Divider } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';

interface CartItem {
    id: string;
    name: string;
    quantity: number;
    price: number;
}

interface CartListProps {
    items: CartItem[];
    onRemoveItem: (id: string) => void;
}

const CartList: React.FC<CartListProps> = ({ items, onRemoveItem }) => {
    const totalPrice = items.reduce((total, item) => total + item.price * item.quantity, 0);

    return (
        <Stack spacing={2} className="p-4">
            {items.map((item) => (
                <Paper key={item.id} className="p-4 flex justify-between items-center">
                    <div>
                        <Typography variant="body1">{item.name}</Typography>
                        <Typography variant="body2" color="text.secondary">
                            Quantity: {item.quantity}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Price: ${(item.price * item.quantity).toFixed(2)}
                        </Typography>
                    </div>
                    <IconButton onClick={() => onRemoveItem(item.id)} color="error">
                        <CloseIcon />
                    </IconButton>
                </Paper>
            ))}

            {/* Divider and Total */}
            <Divider />
            <div className="flex justify-between items-center p-2">
                <Typography variant="h6">Total:</Typography>
                <Typography variant="h6">${totalPrice.toFixed(2)}</Typography>
            </div>
        </Stack>
    );
};

export default CartList;