import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Box, TextField, Button, Typography, Link } from '@mui/material'

function RegisterForm() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [errors, setErrors] = useState({
    username: '',
    password: '',
    confirmPassword: '',
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

    if (password && password.length < 6) {
      newErrors.password = 'Hasło musi mieć co najmniej 6 znaków.'
    }

    if (password !== confirmPassword) {
      newErrors.confirmPassword = 'Hasła muszą się zgadzać.'
    }

    setErrors(newErrors)

    return Object.keys(newErrors).length === 0
  }

  const handleRegister = async () => {
    if (!validateInputs()) return

    try {
      const response = await fetch('http://localhost:8080/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      })

      const data = await response.json()

      if (response.ok) {
        setErrors({
          username: '',
          password: '',
          confirmPassword: '',
          general: '',
        }) // Resetowanie błędów
        navigate('/')
      } else {
        setErrors({
          ...errors,
          username: '',
          password: '',
          confirmPassword: '',
          general:
            data.errors.username ||
            data.errors.password ||
            'Nie udało się zarejestrować użytkownika.',
        })
      }
    } catch (error) {
      console.log(error)

      setErrors({
        ...errors,
        username: '',
        password: '',
        confirmPassword: '',
        general: 'Wystąpił błąd podczas rejestracji. Spróbuj ponownie później.',
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
        Rejestracja
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
      <TextField
        label="Confirm Password"
        variant="outlined"
        type="password"
        margin="normal"
        value={confirmPassword}
        onChange={(e) => setConfirmPassword(e.target.value)}
        error={!!errors.confirmPassword}
        helperText={errors.confirmPassword}
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
        onClick={handleRegister}
        sx={{ marginTop: 2 }}
      >
        Zarejestruj się
      </Button>
      <Link
        component="button"
        variant="body2"
        onClick={() => navigate('/login')}
        sx={{ marginTop: 2 }}
      >
        Masz już konto? Zaloguj się
      </Link>
    </Box>
  )
}

export default RegisterForm
