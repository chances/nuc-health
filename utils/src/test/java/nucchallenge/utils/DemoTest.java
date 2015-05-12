package nucchallenge.utils;

import org.junit.Before;
import org.junit.Ignore;
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
    private Patient patient;
    private Postgres database;
    private static final String URL = "jdbc:postgresql://localhost/testdb";
    private static final String USER = "nova";
    private static final String PASSWD = "123";

    @Before
    @Ignore
    public void setUp() throws ClassNotFoundException {
        database = new Postgres(URL,USER,PASSWD);
        database.connectToDatabase();
    }
    @Test
    @Ignore
    public void DemoTest() {
      /*  Random rand = new Random(System.currentTimeMillis());
        String codes[] = new String[] { "D85", "L682" };
        boolean first_pass = true;
    while (true) {
        patient = new Patient("\'Lisa Gray\'", Math.abs(rand.nextInt() % 100), codes);
        codes = patient.getIllnesscodes();
        int id = patient.getPatientId();
        String name = patient.getName();

        database.insert("INSERT INTO patients VALUES(" + id + ',' + name + ",\'" + codes[0] + "\');");


            CSVLoadLoop csvLoadLoop = new CSVLoadLoop("patient.csv", patient.getPatientid());

            ExecutorService es = Executors.newFixedThreadPool(4);

            es.submit(csvLoadLoop);
            es.execute(csvLoadLoop);

            es.shutdown();
        /*while (!es.isTerminated()) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {

            }
        } */

            System.out.println("New Patient");
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {

            }
        }
}
