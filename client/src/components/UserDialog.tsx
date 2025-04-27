import React, { useEffect, useState } from "react";
import { Dialog, DialogTitle, DialogContent, List, ListItem, ListItemText, DialogActions, Button } from "@mui/material";
import api from "../api/axios";

interface User {
    id: string;
    name: string;
    lastYearSpent: number;
    registeredAt: string;
    totalSpent: number;
    type: string;
}

interface UserDialogProps {
    open: boolean;
    users: User[];
    onClose: () => void;
    onSelect: (user: User) => void;
}

const UserDialog: React.FC<UserDialogProps> = ({ open, users, onClose, onSelect }) => {


    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Select a User</DialogTitle>
            <DialogContent>
                <List>
                    {users.map((user) => (
                        <ListItem
                            key={user.id}
                            component="button"
                            onClick={() => onSelect(user)}
                        >
                            <ListItemText primary={user.name} />
                        </ListItem>
                    ))}
                </List>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} color="secondary">
                    Cancel
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default UserDialog;