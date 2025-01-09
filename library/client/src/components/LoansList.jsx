import React, { useState } from 'react'
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
  Button,
  TextField,
  Alert,
} from '@mui/material'

function LoansList({ loans, currentUser, onReturn, onDelete }) {
  return (
    <Box sx={{ marginBottom: 4 }}>
      <Typography variant="h5" gutterBottom>
        Wszystkie wypożyczenia
      </Typography>
      {loans.length > 0 ? (
        <List>
          {loans.map((loan) => (
            <ListItem key={loan.id}>
              <ListItemText
                sx={{
                  display: 'flex',
                  flexDirection: 'row',
                  alignItems: 'center',
                  gap: 2,
                }}
                primary={`Tytuł: ${loan.title}`}
                secondary={
                  <>
                    <div>Użytkownik: {loan.username}</div>
                    <div>Data wypożyczenia: {loan.rentalDate}</div>
                    <div>
                      Data zwrotu:{' '}
                      {loan.returnDate ? loan.returnDate : 'Nie zwrócono'}
                    </div>
                  </>
                }
              />
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1 }}>
                <Button
                  variant="contained"
                  color="primary"
                  onClick={() => onReturn(loan.id)}
                  disabled={loan.username !== currentUser || loan.returnDate}
                >
                  Zwróć książkę
                </Button>
                <Button
                  variant="outlined"
                  color="error"
                  onClick={() => onDelete(loan.id)}
                >
                  Usuń wypożyczenie
                </Button>
              </Box>
            </ListItem>
          ))}
        </List>
      ) : (
        <Typography>Brak wypożyczeń.</Typography>
      )}
    </Box>
  )
}

export default LoansList
