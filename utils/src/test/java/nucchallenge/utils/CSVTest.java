package nucchallenge.utils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by nova on 4/30/15.
 */
public class CSVTest {
    private CSV file;

    @Before
    @Ignore
    public void setUp() {
        file = new CSV();
        file.setReader("test.csv");
    }

    @Test
    @Ignore
    public void PrintListTest() {
        String url = "jdbc:postgresql://localhost/testdb";
        String user = "nova"; String passwd = "123";
        file.insertIntoDatabase(url,user,passwd, "0");
    }
}
