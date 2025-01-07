package com.example.library.model;

import com.example.library.entity.Book;
import com.example.library.entity.Loan;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private boolean available;
    private List<Long> loanIds;

    // Konstruktor z mapowaniem loans na listę identyfikatorów
    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.available = book.isAvailable();
        this.loanIds = book.getLoans().stream()
                .map(Loan::getId) // Mapowanie każdego Loan na jego id
                .collect(Collectors.toList());
    }
}