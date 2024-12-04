package org.pollub.lab_8.repositories;

import org.pollub.lab_8.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Student, Long> {
}
