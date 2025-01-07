import React from 'react'
import { Card, CardContent, Typography } from '@mui/material'

// Przykładowe dane książek
const books = [
  { id: 1, title: 'The Great Gatsby', author: 'F. Scott Fitzgerald' },
  { id: 2, title: 'To Kill a Mockingbird', author: 'Harper Lee' },
  { id: 3, title: '1984', author: 'George Orwell' },
  { id: 4, title: 'Moby Dick', author: 'Herman Melville' },
]

const BooksList = () => {
  return (
    <div>
      {books.map((book) => (
        <Card key={book.id} style={{ marginBottom: '16px' }}>
          <CardContent>
            <Typography variant="h6">{book.title}</Typography>
            <Typography variant="body2" color="textSecondary">
              Author: {book.author}
            </Typography>
          </CardContent>
        </Card>
      ))}
    </div>
  )
}

export default BooksList
