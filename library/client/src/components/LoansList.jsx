import React, { useEffect, useState } from 'react'
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
  Button,
} from '@mui/material'
import { useNavigate } from 'react-router-dom'

function LoansList() {
  const [loans, setLoans] = useState([])
  const [currentUser, setCurrentUser] = useState('')
  const navigate = useNavigate()

  useEffect(() => {
    const fetchLoans = async () => {
      const token = localStorage.getItem('token')
      const userData = JSON.parse(localStorage.getItem('userData'))

      try {
        // Pobierz aktualnego użytkownika
        const userResponse = await fetch(
          `http://localhost:8080/api/users/${userData.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        )

        if (userResponse.ok) {
          const userData = await userResponse.json()
          setCurrentUser(userData.username)
        } else {
          console.error('Nie udało się pobrać zalogowanego użytkownika.')
          navigate('/')
          return
        }

        // Pobierz wypożyczenia
        const loansResponse = await fetch('http://localhost:8080/api/loans', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })

        if (loansResponse.ok) {
          const data = await loansResponse.json()
          setLoans(data)
        } else if (
          loansResponse.status === 401 ||
          loansResponse.status === 403
        ) {
          console.error('Brak autoryzacji. Przekierowanie do logowania.')
          navigate('/')
        } else {
          console.error('Błąd podczas pobierania danych.')
        }
      } catch (error) {
        console.error('Błąd połączenia z serwerem:', error)
      }
    }

    fetchLoans()
  }, [navigate])

  const handleReturnBook = async (loanId) => {
    const token = localStorage.getItem('token')

    try {
      const response = await fetch(
        `http://localhost:8080/api/loans/return/${loanId}`,
        {
          method: 'POST',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )

      if (response.ok) {
        // Odśwież listę wypożyczeń
        setLoans((prevLoans) =>
          prevLoans.map((loan) =>
            loan.id === loanId
              ? { ...loan, returnDate: new Date().toISOString() }
              : loan
          )
        )
      } else {
        console.error('Błąd podczas zwracania książki.')
      }
    } catch (error) {
      console.error('Błąd połączenia z serwerem:', error)
    }
  }

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
              {!loan.returnDate && loan.username === currentUser && (
                <Button
                  variant="contained"
                  color="primary"
                  onClick={() => handleReturnBook(loan.id)}
                >
                  Zwróć książkę
                </Button>
              )}
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
