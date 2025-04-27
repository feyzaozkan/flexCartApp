import React from "react";
import { Stack, Typography, Paper, IconButton, Divider, Box } from "@mui/material";
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
                <Box key={item.id} className="bg-[#fbf5ef] p-4 flex justify-between items-center rounded-lg">
                    <div>
                        <Typography variant="body1" className="text-[#494d7e]">{item.name}</Typography>
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
                </Box>
            ))}

            {/* Divider and Total */}
            <Divider />
            <div className="flex justify-between items-center p-2 bg-[#494d7e] p-2 rounded-lg">
                <Typography variant="h6" className="text-[#fbf5ef]">Total: </Typography>
                <Typography variant="h6" className="text-[#fbf5ef]"> ${totalPrice.toFixed(2)}</Typography>
            </div>
        </Stack>
    );
};

export default CartList;