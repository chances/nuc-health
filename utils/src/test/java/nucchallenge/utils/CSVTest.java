package nucchallenge.utils;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by nova on 4/30/15.
 */
public class CSVTest {
    private CSV file;

    @Before
    public void setUp() {
        file = new CSV();
        file.setReader("test.csv");
    }

    @Test
    public void PrintListTest() {
        //file.printList();
    }
}
