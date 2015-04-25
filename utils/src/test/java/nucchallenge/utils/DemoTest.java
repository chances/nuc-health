package nucchallenge.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by nova on 4/24/15.
 */
public class DemoTest {
    private Sqlite sqlite;
    private Patient patient;

    @Before
    public void setUp() throws ClassNotFoundException {
        sqlite = new Sqlite();
        sqlite.connectToDatabase("test.db");
    }
    @Test
    public void DemoTest() {
        Random rand = new Random(System.currentTimeMillis());
        String codes[] = new String[] { "D85", "L682" };
        boolean first_pass = true;
    while (true) {
        patient = new Patient("\'Lisa Gray\'", Math.abs(rand.nextInt() % 100), codes);
        codes = patient.getIllnesscodes();

        sqlite.insert("INSERT INTO patients (ID, NAME, CODES)" +
                "VALUES (" + patient.getPatientid() + ", " + patient.getName() + ",\'D85\');");

        if(first_pass) {

            DBUpdateLoop dbUpdateLoop = new DBUpdateLoop(60000 / 2);
            CSVLoadLoop csvLoadLoop = new CSVLoadLoop("test.db", "patient.csv", patient.getPatientid());

            ExecutorService es = Executors.newFixedThreadPool(4);

            // es.submit(dbUpdateLoop);
            // es.submit(csvLoadLoop);
            es.execute(dbUpdateLoop);
            es.execute(csvLoadLoop);

            es.shutdown();
        /*while (!es.isTerminated()) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {

            }
        } */
            first_pass = false;
        } else {

            System.out.println("New Patient");
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {

            }
        }
    }
    }
}
