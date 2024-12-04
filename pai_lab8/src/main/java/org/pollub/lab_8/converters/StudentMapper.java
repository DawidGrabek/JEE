package org.pollub.lab_8.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.pollub.lab_8.dtos.StudentDto;
import org.pollub.lab_8.domain.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "street", source = "student.address.street")
    @Mapping(target = "city", source = "student.address.city")
    @Mapping(target = "state", source = "student.address.state")
    @Mapping(target = "zip", source = "student.address.zip")
    StudentDto mapStudentToStudentDto(Student student);
}
