import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.*;

public class BlackListJdbcTemplateImpl implements BlackListRepository{

    //language=SQL
    private final static String SQL_FIND_ALL= "select password from password_black_list";

    private JdbcTemplate jdbcTemplate;

    public BlackListJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<String> passwordRowMapper = (row, rowNumber) -> {
        String password = row.getString("password");
        return password;
    };


    @Override
    public String Contains(String password) {

        List<String> passwords = new ArrayList<>();
        passwords.addAll(jdbcTemplate.query(SQL_FIND_ALL, passwordRowMapper));

        if (passwords.contains(password))
            return "This password is not allowed, try again";
        else return "Success";
    }
}
