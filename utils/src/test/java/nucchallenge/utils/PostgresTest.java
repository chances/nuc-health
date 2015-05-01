package nucchallenge.utils;

import org.junit.Before;
import org.junit.Ignore;
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
            ResultSet rs = db.selectQuery("SELECT * FROM patients");

            if (rs.next()) {
                System.out.print(rs.getString(1));
                System.out.print(": ");
                System.out.print(rs.getString(2));
                System.out.print(": ");
                System.out.println(rs.getString(3));
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Test
    @Ignore
    public void CopyCSVTest() {
        db.copyFromCSV("/home/nova/NUCchallenge/nuc-health/utils/test.csv", "eeg");
    }
}
