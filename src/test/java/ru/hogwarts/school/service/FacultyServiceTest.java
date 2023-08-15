package ru.hogwarts.school.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Optional;

public class FacultyServiceTest {
    private FacultyService facultyService;
    private FacultyRepository facultyRepository;

    @BeforeEach
    public void setUp() {
        facultyRepository = Mockito.mock(FacultyRepository.class);
        facultyService = new FacultyServiceImpl(facultyRepository);
    }

    @Test
    public void shouldReturnFacultyByIdWhenFacultyExists() {
        Mockito.when(facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty("facultyTest", "colorTest")));
        Faculty faculty = facultyService.getFacultyById(1L);
        Mockito.verify(facultyRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void shouldThrowExceptionWhenFacultyNotExist() {
        Assertions.assertThrows(FacultyNotFoundException.class, () ->
                facultyService.getFacultyById(1L));
    }

}
