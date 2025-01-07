package com.example.library.service;

import com.example.library.entity.LibraryUser;
import com.example.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Pobierz wszystkich użytkowników
    public List<LibraryUser> getAllUsers() {
        return (List<LibraryUser>) userRepository.findAll();
    }

    // Pobierz użytkownika po ID
    public LibraryUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Pobierz użytkownika po username
    public LibraryUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    // Dodaj nowego użytkownika
    public LibraryUser createUser(LibraryUser libraryUser) {
        if (userRepository.findByUsername(libraryUser.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + libraryUser.getUsername());
        }
        return userRepository.save(libraryUser);
    }

    // Zaktualizuj dane użytkownika
    public LibraryUser updateUser(Long id, LibraryUser updatedUser) {
        LibraryUser existingUser = getUserById(id);

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }

    // Usuń użytkownika
    public void deleteUser(Long id) {
        LibraryUser user = getUserById(id);
        userRepository.delete(user);
    }
}
