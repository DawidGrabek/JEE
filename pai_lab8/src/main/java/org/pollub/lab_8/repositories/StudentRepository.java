package org.pollub.lab_8.repositories;

import org.pollub.lab_8.dtos.StudentDto;
import org.pollub.lab_8.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT new org.pollub.lab_8.dtos.StudentDto(s.name, s.surname, s.age, s.address.street, " +
            "s.address.city, s.address.state, s.address.zip) FROM Student s")
    List<StudentDto> findAllNoAttachment();
}
