import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Box, Typography, Button } from '@mui/material'
import LoansList from './LoansList'
import BooksList from './BooksList'
import AddFormBook from './AddFormBook'

function Dashboard() {
  const [loans, setLoans] = useState([])
  const [books, setBooks] = useState([])
  const [currentUser, setCurrentUser] = useState('')
  const navigate = useNavigate()

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem('token')
      const userData = JSON.parse(localStorage.getItem('userData'))

      try {
        // Pobierz dane użytkownika
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
          const loansData = await loansResponse.json()
          setLoans(loansData)
        }

        // Pobierz książki
        const booksResponse = await fetch('http://localhost:8080/api/books', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        if (booksResponse.ok) {
          const booksData = await booksResponse.json()
          setBooks(booksData)
        }
      } catch (error) {
        console.error('Błąd połączenia z serwerem:', error)
      }
    }

    fetchData()
  }, [navigate])

  const handleReturnBook = async (loanId) => {
    const token = localStorage.getItem('token')
    try {
      const response = await fetch(
        `http://localhost:8080/api/loans/return/${loanId}`,
        {
          method: 'POST',
          headers: { Authorization: `Bearer ${token}` },
        }
      )

      if (response.ok) {
        console.log('Książka została zwrócona.')
        refreshData() // Odśwież dane
      } else {
        console.error('Błąd podczas zwracania książki.')
      }
    } catch (error) {
      console.error('Błąd połączenia z serwerem:', error)
    }
  }

  const handleDeleteLoan = async (loanId) => {
    const token = localStorage.getItem('token')
    try {
      const response = await fetch(
        `http://localhost:8080/api/loans/${loanId}`,
        {
          method: 'DELETE',
          headers: { Authorization: `Bearer ${token}` },
        }
      )

      if (response.ok) {
        console.log('Wypożyczenie zostało usunięte.')
        refreshData() // Synchronizacja danych po usunięciu
      } else {
        console.error('Nie udało się usunąć wypożyczenia.')
        alert(
          'Nie udało się usunąć wypożyczenia. To chyba nie twoje wypożyczenie'
        )
      }
    } catch (error) {
      console.error('Błąd usuwania wypożyczenia:', error)
      alert('Nie udało się usunąć wypożyczenia.')
    }
  }

  const refreshData = async () => {
    const token = localStorage.getItem('token')
    try {
      // Pobierz wypożyczenia
      const loansResponse = await fetch('http://localhost:8080/api/loans', {
        headers: { Authorization: `Bearer ${token}` },
      })
      if (loansResponse.ok) {
        const loansData = await loansResponse.json()
        setLoans(loansData)
      }

      // Pobierz książki
      const booksResponse = await fetch('http://localhost:8080/api/books', {
        headers: { Authorization: `Bearer ${token}` },
      })
      if (booksResponse.ok) {
        const booksData = await booksResponse.json()
        setBooks(booksData)
      }
    } catch (error) {
      console.error('Błąd połączenia z serwerem:', error)
    }
  }

  // Wywołanie `refreshData` przy montowaniu komponentu
  useEffect(() => {
    refreshData()
  }, [])

  const handleLogout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('userData')
    navigate('/')
  }

  return (
    <Box>
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
          padding: 2,
          backgroundColor: 'primary.main',
          color: 'white',
        }}
      >
        <Typography variant="h4">Library App</Typography>
        <Box>
          <Button variant="contained" color="secondary" onClick={handleLogout}>
            Wyloguj
          </Button>
        </Box>
      </Box>

      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          padding: 2,
          maxWidth: 800,
          margin: 'auto',
        }}
      >
        <LoansList
          loans={loans}
          currentUser={currentUser}
          onReturn={handleReturnBook}
          onDelete={handleDeleteLoan}
        />
        <BooksList books={books} refreshData={refreshData} />
        <AddFormBook refreshData={refreshData} />
      </Box>
    </Box>
  )
}

export default Dashboard
