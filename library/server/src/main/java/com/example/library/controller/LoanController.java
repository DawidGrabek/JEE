package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.entity.LibraryUser;
import com.example.library.entity.Loan;
import com.example.library.model.BookDto;
import com.example.library.model.LoanDto;
import com.example.library.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;

    // Endpoint: Wypożycz książkę
    @PostMapping("/loan/{bookId}")
    public LoanDto rentBook(@PathVariable Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Login użytkownika (np. email lub username)

        // Pobierz użytkownika z bazy na podstawie loginu
        LibraryUser libraryUser = loanService.getUserByUsername(username);

        return new LoanDto(loanService.rentBook(libraryUser, bookId));
    }

    // Endpoint: Zwróć książkę
    @PostMapping("/return/{loanId}")
    public LoanDto returnBook(@PathVariable Long loanId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Login użytkownika (np. email lub username)

        // Pobierz użytkownika z bazy na podstawie loginu
        LibraryUser libraryUser = loanService.getUserByUsername(username);

        return new LoanDto(loanService.returnBook(libraryUser, loanId));
    }

    @DeleteMapping("/{loanId}")
    public void deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
    }

    @GetMapping
    public List<LoanDto> getAllRentals() {
        return loanService.getAllRentals().stream().map(LoanDto::new).toList(); // Zwracamy wszystkie wypożyczenia
    }
}
