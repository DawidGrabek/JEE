import React, { useEffect, useState } from 'react'
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
  Chip,
} from '@mui/material'
import { useNavigate } from 'react-router-dom'

function BooksList() {
  const [books, setBooks] = useState([])
  const navigate = useNavigate()

  useEffect(() => {
    const fetchBooks = async () => {
      const token = localStorage.getItem('token') // Pobierz token z localStorage

      try {
        const response = await fetch('http://localhost:8080/api/books', {
          headers: {
            Authorization: `Bearer ${token}`, // Dodanie tokena do nagłówka
          },
        })

        if (response.ok) {
          const data = await response.json()
          setBooks(data) // Aktualizuj stan książek
        } else if (response.status === 401 || response.status === 403) {
          console.error('Brak autoryzacji. Przekierowanie do logowania.')
          navigate('/') // Przekierowanie na stronę logowania
        } else {
          console.error('Błąd podczas pobierania danych.')
        }
      } catch (error) {
        console.error('Błąd połączenia z serwerem:', error)
      }
    }

    fetchBooks()
  }, [navigate])

  return (
    <Box>
      <Typography variant="h5" gutterBottom>
        Dostępne Książki
      </Typography>
      {books.length > 0 ? (
        <List>
          {books.map((book) => (
            <ListItem key={book.id}>
              <ListItemText
                primary={book.title}
                secondary={`Autor: ${book.author}`}
              />
              <Chip
                label={book.available ? 'Dostępna' : 'Wypożyczona'}
                color={book.available ? 'success' : 'error'}
              />
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
