import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';

const emails = ['username@gmail.com', 'user02@gmail.com'];

export interface SimpleDialogProps {
  open: boolean;
  selectedValue: string;
  onClose: (value: string) => void;
}

function SimpleDialog(props: SimpleDialogProps) {
  const { onClose, selectedValue, open } = props;

  const handleClose = () => {
    onClose(selectedValue);
  };

  const handleListItemClick = (value: string) => {
    onClose(value);
  };

  return (
    <Dialog onClose={handleClose} open={open}>
      <DialogTitle>Set backup account</DialogTitle>
      <List sx={{ pt: 0 }}>
        {emails.map((email) => (
          <ListItem disablePadding key={email}>
            <ListItemButton onClick={() => handleListItemClick(email)}>
              <ListItemText primary={email} />
            </ListItemButton>
          </ListItem>
        ))}
        <ListItem disablePadding>
          <ListItemButton
            autoFocus
            onClick={() => handleListItemClick('addAccount')}
          >
            <ListItemText primary="Add account" />
          </ListItemButton>
        </ListItem>
      </List>
    </Dialog>
  );
}

export default SimpleDialog;