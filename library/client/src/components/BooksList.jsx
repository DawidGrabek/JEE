import React, { useEffect, useState } from 'react'
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
  Chip,
  Button,
} from '@mui/material'
import { useNavigate } from 'react-router-dom'

function BooksList({ books, refreshData }) {
  const handleBorrowBook = async (bookId) => {
    const token = localStorage.getItem('token')
    try {
      const response = await fetch(
        `http://localhost:8080/api/loans/loan/${bookId}`,
        {
          method: 'POST',
          headers: { Authorization: `Bearer ${token}` },
        }
      )

      if (response.ok) {
        console.log('Książka została wypożyczona.')
        refreshData() // Odśwież dane
      } else {
        console.error('Nie udało się wypożyczyć książki.')
      }
    } catch (error) {
      console.error('Błąd połączenia z serwerem:', error)
    }
  }

  return (
    <Box>
      <Typography variant="h5" gutterBottom>
        Dostępne Książki
      </Typography>
      {books.length > 0 ? (
        <List>
          {books.map((book) => (
            <ListItem
              key={book.id}
              sx={{ display: 'flex', justifyContent: 'space-between' }}
            >
              <ListItemText
                primary={book.title}
                secondary={`Autor: ${book.author}`}
              />
              <Chip
                label={book.available ? 'Dostępna' : 'Wypożyczona'}
                color={book.available ? 'success' : 'error'}
                sx={{ marginRight: 2 }}
              />
              <Button
                variant="contained"
                color="primary"
                onClick={() => handleBorrowBook(book.id)}
                disabled={!book.available}
              >
                Wypożycz
              </Button>
            </ListItem>
          ))}
        </List>
      ) : (
        <Typography>Brak książek w bibliotece.</Typography>
      )}
    </Box>
  )
}

export default BooksList
