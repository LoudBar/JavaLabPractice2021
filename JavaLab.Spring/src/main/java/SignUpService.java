import javax.sql.DataSource;

public class SignUpService {

    BlackListJdbcTemplateImpl blackList;

    public SignUpService(BlackListJdbcTemplateImpl blackList) {
        this.blackList = blackList;
    }

    void signUp(String password){
        System.out.println(blackList.Contains(password));
    }
}