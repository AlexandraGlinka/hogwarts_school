package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(Integer age);

    Collection<Student> findByAgeBetween(Integer age1, Integer age2);

    Collection<Student> findByNameIgnoreCase(String name);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getCountOfStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer getAverageAgeOfStudents();

    @Query(value = "SELECT * FROM (SELECT * FROM student ORDER BY id DESC LIMIT 5) AS t ORDER BY 1", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
