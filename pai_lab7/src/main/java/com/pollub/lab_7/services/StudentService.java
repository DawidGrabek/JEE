package com.pollub.lab_7.services;

import com.pollub.lab_7.entities.Student;
import com.pollub.lab_7.entities.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getStudentList() {
        return (List<Student>) studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Integer id, Student newStudent) {
        if (!studentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }

        Student student = studentRepository.findById(id).get();
        student.setName(newStudent.getName());
        student.setSurname(newStudent.getSurname());
        student.setAverage(newStudent.getAverage());
        return studentRepository.save(student);
    }

    public void deleteStudent(Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }

        studentRepository.deleteById(id);
    }
}
