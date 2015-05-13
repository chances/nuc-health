package nucchallenge.utils;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nova on 4/24/15.
 */
public class CSVLoadThread extends Bash implements Runnable {
    private String csvFile;
    private String csvDir;
    private File f;
    private AtomicInteger pid;
    private String dbHost;
    private String dbUser;
    private String dbPasswd;

    public CSVLoadThread(String csvFile, AtomicInteger pid, ConfigManager cm) {
        this.csvFile = csvFile;
        this.f = null;
        this.pid = pid;
        this.dbHost = cm.getPsqlHost();
        this.dbUser = cm.getPsqlUser();
        this.dbPasswd = cm.getPsqlPasswd();
        this.csvDir = cm.getPatientCSVDir();
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
                patientCSV.insertIntoDatabase(dbHost, dbUser, dbPasswd, Integer.toString(pid.get()));
                patientCSV.close();

                File fn = new File(csvDir + (csvFile.replace(".csv", pid +".csv")));

                f.renameTo(fn);
                f = null;
            } else {
                f = null;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
