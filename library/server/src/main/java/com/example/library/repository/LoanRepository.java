package com.example.library.repository;

import com.example.library.entity.LibraryUser;
import com.example.library.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAllByUser(LibraryUser user);
//    Optional<Loan> findById(Long id);
}