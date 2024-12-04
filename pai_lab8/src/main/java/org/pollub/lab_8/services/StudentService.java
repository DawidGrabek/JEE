package org.pollub.lab_8.services;

import org.pollub.lab_8.dtos.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> getAllStudents();

    List<StudentDto> getAllStudentsNoAttachment();
}
