package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @Test
    public void testSaveStudent() throws Exception {
        JSONObject studentObject = new JSONObject(); // данные, которые отправляем на сервер
        studentObject.put("id", 1L);
        studentObject.put("name", "test name");
        studentObject.put("age", 20);

        // данные, которе должны вернуться
        Student student = new Student(1L, "test name", 20);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test name"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    public void testGetAllStudents() throws Exception{
        when(studentRepository.findAll()).thenReturn(List.of(
                new Student(1L, "name 1", 20),
                new Student(2L, "name 2", 24)
        ));

        // имитируем вызов эндпоинта
        mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // возвращает массив
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("name 1"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("name 2"))
                .andExpect(jsonPath("$[1].age").value(24));

        Mockito.verify(studentRepository, Mockito.times(1)).findAll();
        Mockito.verify(studentService, Mockito.times(1)).getAllStudents();
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student(1L, "test name", 30);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/" + student.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test name"))
                .andExpect(jsonPath("$.age").value(30));

        verify(studentRepository, times(1)).findById(1L);
        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    public void testGetStudentsByAge() throws Exception {
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "name", 20));
        studentList.add(new Student(2L, "name2", 20));

        when(studentRepository.findByAge(20)).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student?age1=20"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStudentsByAgeBetween() throws Exception {
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "name", 20));
        studentList.add(new Student(2L, "name2", 23));

        when(studentRepository.findByAgeBetween(20, 25)).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student?age1=20&age2=25"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStudentsByName() throws Exception {
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "test", 20));
        studentList.add(new Student(2L, "test", 30));

        when(studentRepository.findByNameIgnoreCase("Name")).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/student?name=test"))
                .andExpect(status().isOk());
    }
}
