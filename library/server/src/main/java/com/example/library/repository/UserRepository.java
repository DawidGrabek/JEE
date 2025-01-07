package com.example.library.repository;

import com.example.library.entity.LibraryUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<LibraryUser, Long> {
//    LibraryUser findByUsername(String username);
    Optional<LibraryUser> findByUsername(String username);
}
