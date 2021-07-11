package ru.itis.models;

public class Lesson {

    private Integer id;
    private String name;
    private String time;
    private Course course;

    public Lesson(String name, String time, Course course) {
        this.name = name;
        this.time = time;
        this.course = course;
    }

    public Lesson(Integer id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", course=" + course.toString() +
                '}';
    }
}
