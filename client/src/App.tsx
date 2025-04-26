import React from "react";
import { Button, Box, Container, Avatar, Grid, Stack, Typography } from "@mui/material";
import SimpleDialog from "./components/SimpleDialog";

function App() {
  const emails = ['username@gmail.com', 'user02@gmail.com'];
  const [open, setOpen] = React.useState(false);
  const [selectedValue, setSelectedValue] = React.useState(emails[1]);


  return (
    <Box className="h-screen w-screen">
      <Box className="flex flex-row justify-between w-full h-full">
        <Box className="bg-blue-300 flex-2 w-full">
          <Container>
            <Box className="mt-10">
              <Box className="flex flex-row justify-between">
                <Avatar>MA</Avatar>
                <Button variant="contained">Change User</Button>
                <SimpleDialog
                  selectedValue={selectedValue}
                  open={open}
                  onClose={handleClose}
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
            2
          </Container>
        </Box>
        <Box className="bg-green-300 flex-2">
          <Container>
            3
          </Container>
        </Box>
      </Box>
    </Box>
  );
}

export default App;