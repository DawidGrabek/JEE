package com.pollub.lab_6.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "^[A-Z].*", message = "Name must start with capital letter")
    private String name;

    @NotEmpty
    @Pattern(regexp = "^[A-Z].*", message = "Surname must start with capital letter")
    private String surname;

    @NotEmpty
    @Size(min = 3, max = 10)
    private String login;

    @NotEmpty
    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    @Builder
    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }
}
