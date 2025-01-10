package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.model.BookDto;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book) {
        try {
            Book savedBook = bookService.addBook(book);
            return ResponseEntity.ok(new BookDto(savedBook));
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Endpoint: Zaktualizuj książkę
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook) {
        Book existingBook = bookService.getBookById(id);

        if (!existingBook.isAvailable()) {
            return ResponseEntity.badRequest()
                    .body("Cannot edit a book that is currently loaned out.");
        }

        Book savedBook = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(new BookDto(savedBook));
    }

    // Endpoint: Usuń książkę
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Book existingBook = bookService.getBookById(id);

        if (!existingBook.getLoans().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Cannot delete a book that is currently loaned out.");
        }

        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully.");
    }

    // Endpoint: Generuj kilka książek
//    @PostMapping("/generate")
//    public List<BookDto> generateBooks() {
//        return bookService.generateBooks(10).stream().map(BookDto::new).toList();
//    }
}
