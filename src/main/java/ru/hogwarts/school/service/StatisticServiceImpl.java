package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    //Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);

    public StatisticServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public List<String> getStudentsNameStartWithG() {
        return studentRepository.findAll()
                .stream()
                .map(student -> student.getName().toUpperCase())
                .filter(name -> name.startsWith("Г"))
                .sorted((n1, n2) -> n2.compareTo(n1))
                .collect(Collectors.toList());
    }

    @Override
    public OptionalDouble avgAgeOfStudents() {
        return studentRepository.findAll()
                .stream()
                .mapToInt(student -> student.getAge())
                .average();
    }

    @Override
    public Optional<String> longestFacultyName() {
        return facultyRepository.findAll()
                .stream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparingInt(String::length));
    }
}
