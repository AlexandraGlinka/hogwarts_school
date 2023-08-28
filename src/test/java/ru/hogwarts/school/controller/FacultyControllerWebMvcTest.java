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
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class) // явно указать какой контроллер тестируем, иначе подумает, что все
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean // фейковая реализация, которую делает сам мокито (в сам репозиторий не лезем)
    private FacultyRepository facultyRepository;

    @SpyBean // наш метод, чтобы мы могли делать проверки, но основан на мокито
    private FacultyServiceImpl facultyService;

//    @InjectMocks // не нужно инжектить, т.к. сам настраивает на контроллер
//    private FacultyController facultyController;

    @Test
    public void testSaveFaculty() throws Exception {
        JSONObject facultyObject = new JSONObject(); // данные, которые отправляем на сервер
        facultyObject.put("id", 1L);
        facultyObject.put("name", "test name");
        facultyObject.put("color", "test color");

        // данные, которе должны вернуться
        Faculty faculty = new Faculty(1L, "test name", "test color");

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test name"))
                .andExpect(jsonPath("$.color").value("test color"));
    }

    @Test
    public void testGetAllFaculties() throws Exception{
        when(facultyRepository.findAll()).thenReturn(List.of(
                new Faculty(1L, "name 1", "color 1"),
                new Faculty(2L, "name 2", "color 2")
        ));

        // имитируем вызов эндпоинта
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // возвращает массив
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("name 1"))
                .andExpect(jsonPath("$[0].color").value("color 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("name 2"))
                .andExpect(jsonPath("$[1].color").value("color 2"));

        Mockito.verify(facultyRepository, Mockito.times(1)).findAll();
        Mockito.verify(facultyService, Mockito.times(1)).getAllFaculties();
    }

    @Test
    public void testGetFacultyById() throws Exception {
        Faculty faculty = new Faculty(1L, "test name", "test color");

        //    when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + faculty.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test name"))
                .andExpect(jsonPath("$.color").value("test color"));

        verify(facultyRepository, times(1)).findById(1L);
        verify(facultyService, times(1)).getFacultyById(1L);
    }

    @Test
    public void testGetFacultiesByName() throws Exception {
        ArrayList<Faculty> facultyList = new ArrayList<>();
        facultyList.add(new Faculty(1L, "ttt", "white"));
        facultyList.add(new Faculty(2L, "ttt", "black"));

        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("ttt", null)).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty?name=ttt"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFacultiesByColor() throws Exception {
        ArrayList<Faculty> facultyList = new ArrayList<>();
        facultyList.add(new Faculty(1L, "ttt", "white"));
        facultyList.add(new Faculty(2L, "vvv", "white"));

        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(null, "white")).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty?color=white"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFacultyById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/1"))
                .andExpect(status().isOk());
    }
}
