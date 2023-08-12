package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

public class StudentServiceTest {
    private StudentService studentService;
    private StudentRepository studentRepository;
    @BeforeEach
    public void setUp() {
        studentRepository = Mockito.mock(StudentRepository.class);
        studentService = new StudentServiceImpl(studentRepository);
    }
    @Test
    public void shouldReturnStudentByIdWhenStudentExists() {
        Mockito.when(studentRepository.findAllById(1)).thenReturn((List<Student>) new Student("nameTest", 30));
    }
}
