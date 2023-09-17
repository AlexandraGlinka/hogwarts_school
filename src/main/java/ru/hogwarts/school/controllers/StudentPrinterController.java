package ru.hogwarts.school.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.StudentNamePrinter;

@RestController
@RequestMapping("/printer")
public class StudentPrinterController {
    private final StudentNamePrinter studentNamePrinter;

    public StudentPrinterController(StudentNamePrinter studentNamePrinter) {
        this.studentNamePrinter = studentNamePrinter;
    }

    @GetMapping
    public void printName() {
        studentNamePrinter.print();
    }
}
