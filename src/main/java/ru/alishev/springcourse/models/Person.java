package ru.alishev.springcourse.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author Neil Alishev
 */
public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30")
    private String name;
    @Min(value = 0, message = "age will be > 0")
    private int age;
    @NotEmpty(message = "email should not be empty")
    @Email(message = "use valid email form")
    private String email;

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
