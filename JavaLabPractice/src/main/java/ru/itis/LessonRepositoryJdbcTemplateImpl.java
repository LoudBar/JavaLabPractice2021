package ru.itis;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.Course;
import ru.itis.models.Lesson;
import ru.itis.models.Teacher;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.*;

public class LessonRepositoryJdbcTemplateImpl implements LessonRepository {

    //language=SQL
    private static final String SQL_INSERT = "insert into lesson(name, time, course) values (?, ?, ?)";

    //language=SQL
    private static final String SQL_UPDATE = "update lesson set name = ?, time = ?, course = ? where id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_NAME = "select *, c.id as course_id , c.name as course_name " +
            "from lesson l left join course c on c.id = l.id where l.name = ?";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select *, c.id as course_id , c.name as course_name " +
            "from lesson l left join course c on l.course = c.id where l.id = ?";


    private JdbcTemplate jdbcTemplate;

    public LessonRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final ResultSetExtractor<ArrayList<Lesson>> lessonExtractor1 = resultSet -> {
        Set<Integer> processedLessons = new LinkedHashSet<>();
        Lesson currentLesson = null;
        ArrayList<Lesson> lessons = new ArrayList<>();
        while (resultSet.next()) {
            if (!processedLessons.contains(resultSet.getInt("id"))) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String time = resultSet.getString("time");

                currentLesson = new Lesson(id, name, time);
                lessons.add(currentLesson);
            }
            Integer courseId = resultSet.getObject("course", Integer.class);
            if (courseId != null) {
                Integer id = resultSet.getInt("course_id");
                String name = resultSet.getString("course_name");
                String startDate = resultSet.getString("start_date");
                String endDate = resultSet.getString("end_date");

                Course course = new Course(id, name, startDate, endDate);
                currentLesson.setCourse(course);
            }
            processedLessons.add(currentLesson.getId());
        }
        return lessons;
    };

    private final ResultSetExtractor<Lesson> lessonExtractor2 = resultSet -> {

        Lesson lesson = null;
        if (resultSet.next()) {

            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String time = resultSet.getString("time");

            lesson = new Lesson(id, name, time);

            do {
                Integer courseId = resultSet.getObject("course", Integer.class);
                if (courseId != null) {
                    Integer cId = resultSet.getInt("course_id");
                    String cName = resultSet.getString("course_name");
                    String startDate = resultSet.getString("start_date");
                    String endDate = resultSet.getString("end_date");


                    Course course = new Course(cId, cName, startDate, endDate);
                    lesson.setCourse(course);
                }
            } while (resultSet.next());
        }
        return lesson;
    };

    @Override
    public List<Lesson> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_BY_NAME, lessonExtractor1, name);
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.query(SQL_FIND_BY_ID, lessonExtractor2, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Lesson lesson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});

            statement.setString(1, lesson.getName());
            statement.setString(2, lesson.getTime());
            statement.setInt(3, lesson.getCourse().getId());

            return statement;
        }, keyHolder);

        lesson.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Lesson lesson) {
        jdbcTemplate.update(SQL_UPDATE, lesson.getName(), lesson.getTime()
                , lesson.getCourse().getId(), lesson.getId());
    }
}
