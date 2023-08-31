package ru.hogwarts.school.service;


import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student getStudentById(Long id);

    Collection<Student> getAllStudents();

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    Collection<Student> findStudentsByAge(int age);

    Collection<Student> findStudentsByAgeBetween(Integer age1, Integer age2);

    Collection<Student> findStudentsByName(String name);

    Integer getCountOfStudents();

    Integer getAverageAgeOfStudents();

    List<Student> getLastFiveStudents();
}
