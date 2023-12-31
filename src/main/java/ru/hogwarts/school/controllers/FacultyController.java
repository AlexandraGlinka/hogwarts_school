package ru.hogwarts.school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping // READ / GET http://localhost:8080/faculty
    public ResponseEntity findFaculties(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String color) {
        if (name != null && !name.isBlank() || color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByNameOrColor(name, color));
        }
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }
    @PostMapping // CREATE / POST http://localhost:8080/faculty
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }
    @GetMapping("/{id}") // READ / GET http://localhost:8080/faculty/23
    public Faculty getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }
    @PutMapping("/{id}") // UPDATE / PUT http://localhost:8080/faculty/23
    public Faculty updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(id, faculty);
    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8080/faculty/23
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

//    @GetMapping(params = {"color"})
//    public Collection<Faculty> findFacultiesByColor(@RequestParam String color) {
//        return facultyService.findFacultiesByColor(color);
//    }
}
