package nucchallenge.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.*;

/**
 * Created by nova on 4/20/15.
 */
public class SqliteTest {
    private Sqlite dbtest;

    @Before
    public void setUp() throws ClassNotFoundException {
        dbtest = new Sqlite();
        dbtest.connectToDatabase("test.db");
    }

    @Test
    public void CreateInsertSelect() {
        dbtest.createTable("CREATE TABLE PEOPLE(" +
                "ID INT PRIMARY KEY NOT NULL, " +
                "NAME TEXT NOT NULL," +
                "AGE INT NOT NULL);");

        dbtest.insert("INSERT INTO PEOPLE (ID, NAME, AGE)" +
                "VALUES (1,'Lisa', 21);");

        dbtest.insert("INSERT INTO PEOPLE (ID, NAME, AGE)" +
                "VALUES (2,'Chance', 21);");

        dbtest.insert("INSERT INTO PEOPLE (ID, NAME, AGE)" +
                "VALUES (3,'Nova', 21);");

        ResultSet results  = dbtest.selectQuery("SELECT * FROM PEOPLE;");
        try {
            while (results.next()) {
                int id = results.getInt("ID");
                String name = results.getString("NAME");
                int age = results.getInt("AGE");

                System.out.println("ID=" + id);
                System.out.println("Name=" + name);
                System.out.println("Age=" + age);
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @After
    public void takeDown() {
        dbtest.dropTable("PEOPLE");
    }
}
