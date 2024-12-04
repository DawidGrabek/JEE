package org.pollub.lab_8.controllers;

import lombok.RequiredArgsConstructor;
import org.pollub.lab_8.dtos.StudentDto;
import org.pollub.lab_8.services.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/students")
@RestController
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> getStudents() {
        return studentService.getAllStudents();
    }
}
