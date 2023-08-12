package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.integration.ClasspathOpenApiConfigurationLoader;
import org.hibernate.annotations.SQLDelete;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
//@SQLDelete(sql = "UPDATE faculty SET deleted = TRUE where id = ?")
public class Faculty {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String color;

    //private  static Long counter = 0L;

    @JsonIgnore
    @OneToMany(mappedBy = "faculty") // поле, которое добавили в Student
    private Set<Student> students;
    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
        //this.id = counter++;
        //this.id = id;
    }
    public Faculty() {

    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(id, faculty.id) && Objects.equals(name, faculty.name) && Objects.equals(color, faculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
