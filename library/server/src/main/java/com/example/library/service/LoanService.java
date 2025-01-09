package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.LibraryUser;
import com.example.library.entity.Loan;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Loan rentBook(LibraryUser libraryUser, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is already rented");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setUser(libraryUser);
        loan.setBook(book);
        loan.setRentalDate(LocalDate.now());
        return loanRepository.save(loan);
    }

    public Loan returnBook(LibraryUser libraryUser, Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (!loan.getUser().equals(libraryUser)) {
            throw new RuntimeException("This loan does not belong to the current user");
        }

        if (loan.getReturnDate() != null) {
            throw new RuntimeException("This book has already been returned");
        }

        // Oznacz książkę jako dostępną
        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        // Ustaw datę zwrotu
        loan.setReturnDate(LocalDate.now());
        return loanRepository.save(loan);
    }

    public LibraryUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Loan> getRentalsByUser(LibraryUser libraryUser) {
        return loanRepository.findAllByUser(libraryUser);
    }

    public List<Loan> getAllRentals() {
        return loanRepository.findAll();
    }

    public LibraryUser getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void deleteLoan(LibraryUser libraryUser, Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        // Sprawdź, czy wypożyczenie należy do zalogowanego użytkownika
        if (!loan.getUser().equals(libraryUser)) {
            throw new SecurityException("You are not authorized to delete this loan");
        }

        // Sprawdź, czy książka została zwrócona
        if (loan.getReturnDate() == null) {
            throw new SecurityException("You can't delete a loan when the book has not been returned");
        }


        loanRepository.delete(loan);
    }

}