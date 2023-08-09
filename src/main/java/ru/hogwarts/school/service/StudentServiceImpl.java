package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
@Service
public class StudentServiceImpl implements StudentService {
    //Map<Long, Student> students = new HashMap<>();

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
//        students.put(student.getId(), student);
//        return student;
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
//        if (!students.containsKey(id)) {
//            throw new StudentNotFoundException("Student not found");
//        }
//        return students.get(id);
        return studentRepository.findById(id).get();
    }

    @Override
    public Collection<Student> getAllStudents() {
//        return Collections.unmodifiableCollection(students.values());
        return studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Long id, Student student) {
//        if (!students.containsKey(id)) {
//            throw new StudentNotFoundException("Student not found");
//        }
//        Student studentUpdate = students.get(id);
//        studentUpdate.setName(student.getName());
//        studentUpdate.setAge(student.getAge());
//        students.put(id, student);
//        return studentUpdate;
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
//        if (!students.containsKey(id)) {
//            throw new StudentNotFoundException("Student not found");
//        }
//        students.remove(id);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
//        return getAllStudents().stream()
//                .filter(student -> student.getAge() == age)
//                .collect(Collectors.toList());
        return studentRepository.findByAge(age);
    }
}
