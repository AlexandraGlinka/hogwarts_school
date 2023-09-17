package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {
    //Map<Long, Student> students = new HashMap<>();

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student addStudent(Student student) {
//        students.put(student.getId(), student);
//        return student;
        logger.debug("Student is added: {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
//        if (!students.containsKey(id)) {
//            throw new StudentNotFoundException("Student not found");
//        }
//        return students.get(id);
        logger.debug("Student get by id {}", id);
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found"));
    }

    @Override
    public Collection<Student> getAllStudents() {
//        return Collections.unmodifiableCollection(students.values());
        logger.debug("Get all students");
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
        logger.debug("Update student by id {}: {}", id, student);
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
//        if (!students.containsKey(id)) {
//            throw new StudentNotFoundException("Student not found");
//        }
//        students.remove(id);
        logger.debug("Student delete by id {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> findStudentsByAge(int age) {
//        return getAllStudents().stream()
//                .filter(student -> student.getAge() == age)
//                .collect(Collectors.toList());
        logger.debug("Get students with age {}", age);
        return studentRepository.findByAge(age);
    }

    @Override
    public Collection<Student> findStudentsByAgeBetween(Integer age1, Integer age2) {
        logger.debug("Get students between age {} and {}", age1, age2);
        return studentRepository.findByAgeBetween(age1, age2);
    }

    @Override
    public Collection<Student> findStudentsByName(String name) {
        logger.debug("Find student by name {}", name);
        return studentRepository.findByNameIgnoreCase(name);
    }

    @Override
    public Integer getCountOfStudents() {
        logger.debug("Get count of all students");
        return studentRepository.getCountOfStudents();
    }

    @Override
    public Integer getAverageAgeOfStudents() {
        logger.debug("Get average age of students");
        return studentRepository.getAverageAgeOfStudents();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.debug("Get last 5 students");
        return studentRepository.getLastFiveStudents();
    }

}
