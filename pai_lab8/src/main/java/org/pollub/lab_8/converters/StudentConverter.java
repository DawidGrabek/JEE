package org.pollub.lab_8.converters;

import org.pollub.lab_8.dtos.StudentDto;
import org.pollub.lab_8.domain.Student;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentConverter implements Converter<Student, StudentDto> {
    @Override
    public StudentDto convert(Student source) {
        return StudentDto.builder()
                .name(source.getName())
                .surname(source.getSurname())
                .age(source.getAge())
                .street(source.getAddress().getStreet())
                .city(source.getAddress().getCity())
                .zip(source.getAddress().getZip())
                .state(source.getAddress().getState())
                .build();
    }
}
