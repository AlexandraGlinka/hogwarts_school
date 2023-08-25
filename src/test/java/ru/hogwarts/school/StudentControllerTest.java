package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudent() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student testStudent = setData();

        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", testStudent, String.class))
                .isNotNull();

//        Assertions
//                .assertThat(this.testRestTemplate.delete("http://localhost:" + port + "/student/" + testStudent.getId()));
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student testStudent = setData();
        studentController.addStudent(testStudent);

        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/" +
                        testStudent.getId(), String.class))
                .isEqualTo("{\"id\":" + testStudent.getId()
                        + ",\"name\":\"" + testStudent.getName()
                        + "\",\"age\":" + testStudent.getAge() + "}");
    }

    @Test
    public void testGetStudentByAge() throws Exception {
        Student testStudent = setData();
        studentController.addStudent(testStudent);

        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student?age1=" +
                        testStudent.getAge(), String.class))
                .isEqualTo("[{\"id\":" + testStudent.getId()
                        + ",\"name\":\"" + testStudent.getName()
                        + "\",\"age\":" + testStudent.getAge() + "}]");
    }

    @Test
    public void testGetStudentByName() throws Exception {
        Student testStudent = setData();
        studentController.addStudent(testStudent);

        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student?name=" +
                        testStudent.getName(), String.class))
                .isEqualTo("[{\"id\":" + testStudent.getId()
                        + ",\"name\":\"" + testStudent.getName()
                        + "\",\"age\":" + testStudent.getAge() + "}]");
    }

    @Test
    public void testPutStudent() throws Exception {
        Student testStudent = setData();
        studentController.addStudent(testStudent);

        Student updateStudent = new Student("test student 2", 20000)

        Assertions
                .assertThat(this.testRestTemplate.put("http://localhost:" + port + "/student/" +
                        testStudent.getId(), updateStudent))
                .

    }


    public Student setData() {
        Student testStudent = new Student();
        testStudent.setName("test student");
        testStudent.setAge(1000000);
        //testStudent.setId(1L);

        return testStudent;
    }
}
