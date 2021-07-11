package ru.itis.models;

import java.util.ArrayList;

public class Student {

    private Integer id;
    private String firstName;
    private String lastName;
    private int groupNumber;
    private ArrayList<Course> courses;

    public Student(String firstName, String lastName, int groupNumber, ArrayList<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupNumber = groupNumber;
        this.courses = courses;
    }

    public Student(Integer id, String firstName, String lastName, int groupNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupNumber = groupNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", groupNumber=" + groupNumber +
                ", courses=" + courses +
                '}';
    }
}
