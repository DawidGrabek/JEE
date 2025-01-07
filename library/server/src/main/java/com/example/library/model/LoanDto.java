package com.example.library.model;

import com.example.library.entity.Loan;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDto {
    private Long id;
    private String username; // Nowe pole zamiast userId
    private String title;    // Nowe pole zamiast bookId
    private LocalDate rentalDate;
    private LocalDate returnDate;

    // Konstruktor mapujący encję Loan na LoanDto
    public LoanDto(Loan loan) {
        this.id = loan.getId();
        this.username = loan.getUser().getUsername(); // Pobranie nazwy użytkownika
        this.title = loan.getBook().getTitle();       // Pobranie tytułu książki
        this.rentalDate = loan.getRentalDate();
        this.returnDate = loan.getReturnDate();
    }
}
