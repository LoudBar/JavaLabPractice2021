package ru.itis.models;

import java.util.ArrayList;

public class Teacher {

    private Integer id;
    private String firstName;
    private String lastName;
    private int experience;
    private ArrayList<Course> courses;

    public Teacher(Integer id, String firstName, String lastName, int experience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getExperience() {
        return experience;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", experience=" + experience +
                ", courses=" + courses +
                '}';
    }
}
