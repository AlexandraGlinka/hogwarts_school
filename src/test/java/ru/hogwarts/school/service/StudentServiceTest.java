package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Optional;

public class StudentServiceTest {
    private StudentService studentService;
    private StudentRepository studentRepository;
    @BeforeEach
    public void setUp() {
        studentRepository = Mockito.mock(StudentRepository.class);
        studentService = new StudentServiceImpl(studentRepository);
    }

    public void shouldReturnStudentByIdWhenStudentExists() {
        Mockito.when(studentRepository.);
    }
}
