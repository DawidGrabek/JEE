import React, { useState } from 'react'
import { Box, TextField, Button, Typography } from '@mui/material'

function AddBookForm({ refreshData }) {
  const [title, setTitle] = useState('')
  const [author, setAuthor] = useState('')
  const [errors, setErrors] = useState({
    title: '',
    author: '',
    general: '',
  })

  const validateInputs = () => {
    const newErrors = {}

    if (!title.trim()) {
      newErrors.title = 'Tytuł książki nie może być pusty.'
    }

    if (!author.trim()) {
      newErrors.author = 'Autor książki nie może być pusty.'
    } else if (!/^[a-zA-Z\s]+$/.test(author)) {
      newErrors.author = 'Autor może zawierać tylko litery i spacje.'
    }

    setErrors(newErrors)
    return Object.keys(newErrors).length === 0
  }

  const handleAddBook = async () => {
    if (!validateInputs()) return

    const token = localStorage.getItem('token')

    try {
      const response = await fetch('http://localhost:8080/api/books', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ title, author, available: true }),
      })

      if (response.ok) {
        setTitle('')
        setAuthor('')
        setErrors({ title: '', author: '', general: '' })
        refreshData()
      } else {
        const errorMessage = await response.text()
        setErrors((prevErrors) => ({
          ...prevErrors,
          general: errorMessage || 'Nie udało się dodać książki.',
        }))
      }
    } catch (error) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        general: 'Błąd połączenia z serwerem.',
      }))
    }
  }

  return (
    <Box sx={{ marginBottom: 4 }}>
      <Typography variant="h5" gutterBottom>
        Dodaj nową książkę
      </Typography>
      <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
        <TextField
          label="Tytuł książki"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          error={!!errors.title}
          helperText={errors.title}
          fullWidth
        />
        <TextField
          label="Autor książki"
          value={author}
          onChange={(e) => setAuthor(e.target.value)}
          error={!!errors.author}
          helperText={errors.author}
          fullWidth
        />
        {errors.general && (
          <Typography color="error" sx={{ marginTop: 1 }}>
            {errors.general}
          </Typography>
        )}
        <Button variant="contained" color="primary" onClick={handleAddBook}>
          Dodaj książkę
        </Button>
      </Box>
    </Box>
  )
}

export default AddBookForm
