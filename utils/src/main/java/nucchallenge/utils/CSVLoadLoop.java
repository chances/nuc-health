package nucchallenge.utils;

import java.io.File;

/**
 * Created by nova on 4/24/15.
 */
public class CSVLoadLoop extends Bash implements Runnable {
    private String csvfile;
    private File f;
    private int currentpid;
    private Sqlite database;
    private String db;

    public CSVLoadLoop(String db, String csvfile, int patient_id) {
        this.csvfile = csvfile;
        this.f = null;
        this.currentpid = patient_id;
        this.db = db;
        this.database = new Sqlite();
    }

    @Override
    public void run() {
        try {
            database.connectToDatabase(db);
        } catch (ClassNotFoundException e) {

        }

       loadLoop();
    }

    public void loadLoop() {
        while (true) {
            f = new File(csvfile);
            File fn = new File("./patient" + currentpid + ".csv");
            if (f.exists() && !f.isDirectory()) {
                removeFirstLineCSV(csvfile);
                addPatientIDToCSV(csvfile, currentpid);

                if(database != null) {
                    database.importCSV(csvfile, "eeg");
                    f.renameTo(fn);
                }
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
