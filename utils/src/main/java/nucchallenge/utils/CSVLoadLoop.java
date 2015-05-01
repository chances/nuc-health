package nucchallenge.utils;

import java.io.File;
import java.util.List;

/**
 * Created by nova on 4/24/15.
 */
public class CSVLoadLoop extends Bash implements Runnable {
    private String csvFile;
    private File f;
    private int pid;
    private static final String URL = "jdbc:postgresql://localhost/testdb";
    private static final String USER = "nova";
    private static final String PASSWD = "123";

    public CSVLoadLoop(String csvFile, int pid) {
        this.csvFile = csvFile;
        this.f = null;
        this.pid = pid;
    }

    @Override
    public void run() {
            loadLoop();
    }

    public void loadLoop() {
        while (true) {
            f = new File(csvFile);

            if (f.exists() && !f.isDirectory()) {
                CSV patientCSV = new CSV();
                patientCSV.setReader(csvFile);
                patientCSV.insertIntoDatabase(URL,USER,PASSWD, Integer.toString(pid));
                patientCSV.close();

                File fn = new File("patient" + pid +".csv");

                f.renameTo(fn);
                f = null;
            } else {
                f = null;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
