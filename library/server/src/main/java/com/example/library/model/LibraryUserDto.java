package com.example.library.model;

import com.example.library.entity.LibraryUser;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class LibraryUserDto {
    private Long id;
    private String username;
    private String role;
    private List<Long> loanIds;

    // Konstruktor mapujący encję LibraryUser na LibraryUserDto
    public LibraryUserDto(LibraryUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.loanIds = user.getLoans().stream()
                .map(loan -> loan.getId())
                .collect(Collectors.toList());
    }
}