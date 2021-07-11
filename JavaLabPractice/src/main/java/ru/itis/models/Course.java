package ru.itis.models;

import java.util.ArrayList;

public class Course {

    private Integer id;
    private String name;
    private String startDate;
    private String endDate;
    private Teacher teacher;
    private ArrayList<Student> students;

    public Course(Integer id, String name, String startDate, String endDate, Teacher teacher, ArrayList<Student> students) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
        this.students = students;
    }

    public Course(Integer id, String name, String startDate, String endDate, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
    }
    public Course(String name, String startDate, String endDate, Teacher teacher) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
    }

    public Course(Integer id, String name, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public ArrayList<Student> getStudents() {
       return students;
   }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", teacherId=" + teacher +
                ", students=" + students +
                '}';
    }
}
