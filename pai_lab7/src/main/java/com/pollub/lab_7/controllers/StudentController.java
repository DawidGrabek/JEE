package com.pollub.lab_7.controllers;

import com.pollub.lab_7.entities.Student;
import com.pollub.lab_7.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping()
    public List<Student> getAll() {
        return studentService.getStudentList();
    }

    @PostMapping()
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Integer id, @RequestBody Student student) throws NotFoundException {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Integer id) throws NotFoundException {
        studentService.deleteStudent(id);
    }
}
