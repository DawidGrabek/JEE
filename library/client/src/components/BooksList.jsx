import React, { useState } from 'react'
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
  Chip,
  Button,
  TextField,
} from '@mui/material'

function BooksList({ books, refreshData }) {
  const [editedBooks, setEditedBooks] = useState({}) // Przechowuje dane edytowanych książek

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

  const handleEditBook = (id, field, value) => {
    setEditedBooks((prev) => ({
      ...prev,
      [id]: { ...prev[id], [field]: value },
    }))
  }

  const handleUpdateBook = async (id) => {
    const token = localStorage.getItem('token')

    // Pobierz oryginalne dane książki
    const originalBook = books.find((book) => book.id === id)

    // Połącz oryginalne dane z edytowanymi danymi
    const bookToUpdate = {
      ...originalBook,
      ...editedBooks[id],
    }

    console.log('Aktualizacja książki:', bookToUpdate)

    try {
      const response = await fetch(`http://localhost:8080/api/books/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(bookToUpdate),
      })

      if (response.ok) {
        console.log('Książka została zaktualizowana.')
        setEditedBooks((prev) => {
          const newState = { ...prev }
          delete newState[id]
          return newState
        })
        refreshData() // Odśwież dane
      } else {
        console.error('Nie udało się zaktualizować książki.')
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
              sx={{ display: 'flex', justifyContent: 'space-between', gap: 2 }}
            >
              <Box sx={{ flexGrow: 1 }}>
                <ListItemText
                  primary={book.title}
                  secondary={`Autor: ${book.author}`}
                />
                <TextField
                  label="Tytuł"
                  variant="outlined"
                  size="small"
                  value={editedBooks[book.id]?.title || book.title}
                  onChange={(e) =>
                    handleEditBook(book.id, 'title', e.target.value)
                  }
                  sx={{ marginRight: 1, marginTop: 1 }}
                />
                <TextField
                  label="Autor"
                  variant="outlined"
                  size="small"
                  value={editedBooks[book.id]?.author || book.author}
                  onChange={(e) =>
                    handleEditBook(book.id, 'author', e.target.value)
                  }
                  sx={{ marginRight: 1, marginTop: 1 }}
                />
              </Box>
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
                sx={{ marginRight: 1 }}
              >
                Wypożycz
              </Button>
              <Button
                variant="contained"
                color="secondary"
                onClick={() => handleUpdateBook(book.id)}
                disabled={!book.available}
              >
                Zaktualizuj
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
