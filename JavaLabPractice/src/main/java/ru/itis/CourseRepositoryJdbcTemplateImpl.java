package ru.itis;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.Course;
import ru.itis.models.Student;
import ru.itis.models.Teacher;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class CourseRepositoryJdbcTemplateImpl implements CourseRepository {

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select *, " +
            " s.first_name as student_first_name, s.last_name as student_last_name," +
            " t.first_name as teacher_first_name, t.last_name as teacher_last_name" +
            " from course as c " +
            "left join course_student cs on c.id = cs.course_id " +
            "left join student s on cs.student_id = s.id " +
            "left join teacher as t on t.id = c.teacher_id where c.id =? order by c.id ";

    //language=SQL
    private static final String SQL_FIND_BY_NAME = "select *, " +
            " s.first_name as student_first_name, s.last_name as student_last_name," +
            " t.first_name as teacher_first_name, t.last_name as teacher_last_name" +
            " from course as c " +
            "left join course_student cs on c.id = cs.course_id " +
            "left join student s on cs.student_id = s.id " +
            "left join teacher as t on t.id = c.teacher_id where c.name =? order by c.id ";

    //language=SQL
    private static final String SQL_INSERT = "insert into course(name, start_date, end_date, teacher_id) values (?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_UPDATE = "update course set name = ?, start_date = ?, end_date = ?, teacher_id = ? where id = ?";

    private JdbcTemplate jdbcTemplate;

    public CourseRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Teacher> teacherRowMapper = (row, rowNumber) -> {
        Integer id = row.getInt("teacher_id");
        String firstName = row.getString("teacher_first_name");
        String lastname = row.getString("teacher_last_name");
        Integer exp = row.getInt("experience");

        return new Teacher(id, firstName, lastname, exp);
    };

    private final RowMapper<Course> courseRowMapper = (row, rowNumber) -> {
        Integer id = row.getInt("id");
        String name = row.getString("name");
        String startDate = row.getString("start_date");
        String endDate = row.getString("end_date");
        Teacher teacher = teacherRowMapper.mapRow(row, rowNumber);

        Course course = new Course(id, name, startDate, endDate, teacher);
        course.setStudents(new ArrayList<>());
        return course;
    };

    private final RowMapper<Student> studentRowMapper = (row, rowNumber) -> {
        Integer id = row.getInt("student_id");
        String firstName = row.getString("student_first_name");
        String lastName = row.getString("student_last_name");
        Integer groupNumber = row.getInt("group_number");

        return new Student(id, firstName, lastName, groupNumber);
    };

    private final ResultSetExtractor<Course> courseExtractor2 = resultSet -> {
        Course course = null;
        if (resultSet.next()) {
            course = courseRowMapper.mapRow(resultSet, resultSet.getRow());
            course.setStudents(new ArrayList<>());

            do {
                Integer id = resultSet.getObject("student_id", Integer.class);
                if (id != null) {
                    Student student = studentRowMapper.mapRow(resultSet, resultSet.getRow());
                    course.getStudents().add(student);
                }
            } while (resultSet.next());
        }
        return course;
    };

    private final ResultSetExtractor<ArrayList<Course>> courseExtractor1 = resultSet -> {
        Set<Integer> processedCourses = new HashSet<>();
        Course currentCourse = null;
        ArrayList<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            if (!processedCourses.contains(resultSet.getInt("id"))) {
                currentCourse = courseRowMapper.mapRow(resultSet, resultSet.getRow());
                courses.add(currentCourse);
            }
            Integer studentId = resultSet.getObject("student_id", Integer.class);
            if (studentId != null) {
                Student student = studentRowMapper.mapRow(resultSet, resultSet.getRow());
                currentCourse.getStudents().add(student);
            }
            processedCourses.add(currentCourse.getId());
        }
        return courses;
    };


    @Override
    public void save(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});

            statement.setString(1, course.getName());
            statement.setString(2, course.getStartDate());
            statement.setString(3, course.getEndDate());
            statement.setInt(4, course.getTeacher().getId());

            return statement;
        }, keyHolder);

        course.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(SQL_UPDATE, course.getName(), course.getStartDate()
                , course.getEndDate(), course.getTeacher().getId(), course.getId());
    }

    @Override
    public Optional<Course> findById(Integer id) {
        try {
            return Optional.of(jdbcTemplate.query(SQL_FIND_BY_ID, courseExtractor2, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Course> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_BY_NAME, courseExtractor1, name);
    }

}
