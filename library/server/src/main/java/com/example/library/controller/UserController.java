package com.example.library.controller;

import com.example.library.entity.LibraryUser;
import com.example.library.model.LibraryUserDto;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // Pobierz wszystkich użytkowników w formacie DTO
    @GetMapping
    public List<LibraryUserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(LibraryUserDto::new)
                .collect(Collectors.toList());
    }

    // Pobierz użytkownika po ID w formacie DTO
    @GetMapping("/{id}")
    public LibraryUserDto getUserById(@PathVariable Long id) {
        LibraryUser user = userService.getUserById(id);
        return new LibraryUserDto(user);
    }

    // Pobierz użytkownika
    @GetMapping("/username/{username}")
    public LibraryUserDto getUserByUsername(@PathVariable String username) {
        LibraryUser user = userService.getUserByUsername(username);
        return new LibraryUserDto(user);
    }

    // Dodaj nowego użytkownika
    @PostMapping
    public LibraryUserDto createUser(@RequestBody LibraryUser libraryUser) {
        LibraryUser createdUser = userService.createUser(libraryUser);
        return new LibraryUserDto(createdUser);
    }

    // Zaktualizuj dane użytkownika
    @PutMapping("/{id}")
    public LibraryUserDto updateUser(@PathVariable Long id, @RequestBody LibraryUser libraryUser) {
        LibraryUser updatedUser = userService.updateUser(id, libraryUser);
        return new LibraryUserDto(updatedUser);
    }

    // Usuń użytkownika
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
