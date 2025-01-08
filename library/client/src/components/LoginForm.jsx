import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Box, TextField, Button, Typography, Link } from '@mui/material'

function LoginForm() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [errors, setErrors] = useState({
    username: '',
    password: '',
    general: '',
  })
  const navigate = useNavigate()

  const validateInputs = () => {
    const newErrors = {}

    if (!username.trim()) {
      newErrors.username = 'Nazwa użytkownika nie może być pusta.'
    }

    if (!password) {
      newErrors.password = 'Hasło nie może być puste.'
    }

    setErrors(newErrors)

    return Object.keys(newErrors).length === 0
  }

  const handleLogin = async () => {
    if (!validateInputs()) return

    try {
      const response = await fetch('http://localhost:8080/authenticate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      })

      const data = await response.json()

      if (response.ok) {
        setErrors({ username: '', password: '', general: '' }) // Resetowanie błędów
        localStorage.setItem('token', data.token)

        // Pobranie danych użytkownika
        const userResponse = await fetch(
          `http://localhost:8080/api/users/username/${data.username}`,
          {
            headers: { Authorization: `Bearer ${data.token}` },
          }
        )

        if (userResponse.ok) {
          const userData = await userResponse.json()
          localStorage.setItem('userData', JSON.stringify(userData))

          navigate('/dashboard')
        } else {
          setErrors({
            ...errors,
            general: 'Nie udało się pobrać danych użytkownika.',
          })
        }
      } else {
        setErrors({
          ...errors,
          username: '',
          password: '',
          general: 'Nieprawidłowe dane logowania.',
        })
      }
    } catch (error) {
      setErrors({
        ...errors,
        username: '',
        password: '',
        general: 'Błąd połączenia z serwerem.',
      })
    }
  }

  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        height: '100vh',
      }}
    >
      <Typography variant="h4" gutterBottom>
        Logowanie
      </Typography>
      <TextField
        label="Username"
        variant="outlined"
        margin="normal"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        error={!!errors.username}
        helperText={errors.username}
        sx={{ width: '100%', maxWidth: 300 }}
      />
      <TextField
        label="Password"
        variant="outlined"
        type="password"
        margin="normal"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        error={!!errors.password}
        helperText={errors.password}
        sx={{ width: '100%', maxWidth: 300 }}
      />
      {errors.general && (
        <Typography color="error" sx={{ marginTop: 1 }}>
          {errors.general}
        </Typography>
      )}
      <Button
        variant="contained"
        color="primary"
        onClick={handleLogin}
        sx={{ marginTop: 2 }}
      >
        Zaloguj
      </Button>
      <Link
        component="button"
        variant="body2"
        onClick={() => navigate('/register')}
        sx={{ marginTop: 2 }}
      >
        Nie masz konta? Zarejestruj się
      </Link>
    </Box>
  )
}

export default LoginForm
