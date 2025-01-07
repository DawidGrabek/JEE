package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.model.BookDto;
import com.example.library.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Endpoint: Pobierz wszystkie książki
    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks().stream()
                .map(BookDto::new)
                .toList();
    }

    // Endpoint: Pobierz książkę po ID
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new BookDto(book);
    }

    // Endpoint: Dodaj książkę
    @PostMapping
    public BookDto addBook(@RequestBody Book book) {
        return new BookDto(bookService.addBook(book));
    }

    // Endpoint: Zaktualizuj książkę
    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody Book book) {
        return new BookDto(bookService.updateBook(id, book));

    }

    // Endpoint: Usuń książkę
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    // Endpoint: Generuj kilka książek
    @PostMapping("/generate")
    public List<BookDto> generateBooks() {
        return bookService.generateBooks(10).stream().map(BookDto::new).toList();
    }
}
