package ru.itis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.models.Course;
import ru.itis.models.Lesson;
import ru.itis.models.Teacher;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class Main {

    public static void main(String[] args)  {

        Properties properties = new Properties();

        try {
            properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.pool-size")));

        DataSource dataSource = new HikariDataSource(config);

        CourseRepository courseRepository = new CourseRepositoryJdbcTemplateImpl(dataSource);
        LessonRepository lessonRepository = new LessonRepositoryJdbcTemplateImpl(dataSource);


        Teacher teacher = new Teacher(1, "Natalya", "Konopleva", 21);
        Course course1 = new Course(2,"a", "01.01.2010", "02.01.2010", teacher);


        Course course2 = new Course("course", "18.12.2000", "28.12.2000", teacher);

        Lesson lesson = new Lesson("English", "Thursday, 8:30", course1);

        //findById course
        System.out.println(courseRepository.findById(1));

        //findByName course
        System.out.println(courseRepository.findByName("av"));

        //save course
        courseRepository.save(course2);

       //update course
       Optional<Course> optionalCourse = courseRepository.findById(1);
       Course course3 = optionalCourse.get();
       course3.setName("IT");
       courseRepository.update(course3);

        //save lesson
        lessonRepository.save(lesson);

        //findByName lesson
        System.out.println(lessonRepository.findByName("JDBC template"));

        //findById lesson
        System.out.println(lessonRepository.findById(5));

        //update lesson
        Optional<Lesson> optionalLesson = lessonRepository.findById(5);
        Lesson lesson1 = optionalLesson.get();
        lesson1.setName("Deutsch");
        lessonRepository.update(lesson1);
    }
}
