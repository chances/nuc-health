package nucchallenge.utils;

import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nova on 4/30/15.
 */
public class PostgresTest {
    private Postgres db;

    @Before
    public void setUp() {
        db = new Postgres("jdbc:postgresql://localhost/testdb");
        db.setUser("nova"); db.setPasswd("123");
        db.connectToDatabase();
    }

    @Test
    public void SelectQueryTest() {
        try {
            ResultSet rs = db.selectQuery("SELECT VERSION();");

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}
