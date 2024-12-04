package com.pollub.lab_7.entities;


import com.pollub.lab_7.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
