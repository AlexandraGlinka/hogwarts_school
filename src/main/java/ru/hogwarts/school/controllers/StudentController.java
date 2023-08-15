package ru.hogwarts.school.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity findStudents(@RequestParam(required = false) Integer age1,
                                       @RequestParam(required = false) Integer age2,
                                       @RequestParam(required = false) String name) {
        if (age1 != null && age2 != null) {
            return ResponseEntity.ok(studentService.findStudentsByAgeBetween(age1, age2));
        }
        if (age1 != null && age2 == null) {
            return ResponseEntity.ok(studentService.findStudentsByAge(age1));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(studentService.findStudentsByName(name));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
//    @GetMapping(params = {"age"})
//    public Collection<Student> getStudentByAge(@RequestParam int age) {
//        return studentService.getStudentsByAge(age);
//    }


}
