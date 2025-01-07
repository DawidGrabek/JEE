import React, { useEffect, useState } from 'react'
import { Box, Typography, List, ListItem, ListItemText } from '@mui/material'
import { useNavigate } from 'react-router-dom'

function LoansList() {
  const [loans, setLoans] = useState([])
  const navigate = useNavigate()

  useEffect(() => {
    const fetchLoans = async () => {
      const token = localStorage.getItem('token') // Pobierz token z localStorage

      try {
        const response = await fetch('http://localhost:8080/api/loans', {
          headers: {
            Authorization: `Bearer ${token}`, // Dodanie tokena do nagłówka
          },
        })

        if (response.ok) {
          const data = await response.json()
          setLoans(data)
        } else if (response.status === 401 || response.status === 403) {
          // Obsługa braku uprawnień
          console.error('Brak autoryzacji. Przekierowanie do logowania.')
          navigate('/') // Przekierowanie na stronę logowania
        } else {
          console.error('Błąd podczas pobierania danych.')
        }
      } catch (error) {
        console.error('Błąd połączenia z serwerem:', error)
      }
    }

    fetchLoans()
  }, [navigate])

  return (
    <Box sx={{ marginBottom: 4 }}>
      <Typography variant="h5" gutterBottom>
        Twoje Wypożyczenia
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
                    <div>Data zwrotu: {loan.returnDate}</div>
                  </>
                }
              />
            </ListItem>
          ))}
        </List>
      ) : (
        <Typography>Brak aktywnych wypożyczeń.</Typography>
      )}
    </Box>
  )
}

export default LoansList
