import React from 'react'
import {
  Box,
  Typography,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Button,
} from '@mui/material'

function LoansList() {
  const loans = [
    {
      id: 1,
      title: 'Book 1',
      loanDate: '2023-12-01',
      returnDate: '2024-01-01',
    },
    {
      id: 2,
      title: 'Book 2',
      loanDate: '2023-11-01',
      returnDate: '2023-12-01',
    },
  ]

  return (
    <Box>
      <Typography variant="h5">Lista wypożyczeń</Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Tytuł</TableCell>
            <TableCell>Data wypożyczenia</TableCell>
            <TableCell>Data zwrotu</TableCell>
            <TableCell>Akcje</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {loans.map((loan) => (
            <TableRow key={loan.id}>
              <TableCell>{loan.title}</TableCell>
              <TableCell>{loan.loanDate}</TableCell>
              <TableCell>{loan.returnDate}</TableCell>
              <TableCell>
                <Button variant="contained" color="primary">
                  Przedłuż
                </Button>
                <Button
                  variant="contained"
                  color="secondary"
                  sx={{ marginLeft: 1 }}
                >
                  Oddaj
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Box>
  )
}

export default LoansList
