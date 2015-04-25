package nucchallenge.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

/**
 * Created by nova on 4/23/15.
 */
public class BashTest extends Bash {

    @Ignore
    @Test
    public void csvTest() {
        System.out.println("removing first line");
        Random rand = new Random(System.currentTimeMillis());
        int id = Math.abs(rand.nextInt() % 100);
        removeFirstLineCSV("test.csv");
        addPatientIDToCSV("test.csv", id);
        System.out.println("added patient ID:" + id);
    }

    @Ignore
    @Before
    public void setUp() {
        try {
            String[] cmd = {"/bin/bash", "-c", "cp ~/NUCchallenge/nova-1-19.04.2015.21.53.46.csv test.csv"};
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        }catch (Exception e) {
        }
    }
}
