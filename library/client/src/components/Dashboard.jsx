import React from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { Box, Typography, Button } from '@mui/material'
import LoansList from './LoansList'
import BooksList from './BooksList'

function Dashboard() {
  const location = useLocation()
  const navigate = useNavigate()
  const username = location.state?.username || 'Gość'

  const handleLogout = () => {
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
          {/* <Typography variant="body1" sx={{ marginRight: 2 }}>
            Witaj, {username}
          </Typography> */}
          <Button variant="contained" color="secondary" onClick={handleLogout}>
            Wyloguj
          </Button>
        </Box>
      </Box>

      {/* Content */}
      <Box sx={{ display: 'flex', flexDirection: 'column', padding: 2 }}>
        <LoansList />
        <BooksList />
      </Box>
    </Box>
  )
}

export default Dashboard
