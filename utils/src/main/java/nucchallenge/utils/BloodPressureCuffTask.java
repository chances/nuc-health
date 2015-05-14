package nucchallenge.utils;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class BloodPressureCuffTask extends TimerTask {
    private AtomicInteger pid;
    private Postgres psql;
    private BloodPressureCuff bloodPressureCuff;
    private String dbHost;
    private String dbUser;
    private String dbPasswd;
    private int oldSys;
    private int oldDia;
    private int oldPulse;

    /* SQL stuff */
    private static final String PARSE_TABLE_NAME = "\\$\\{table\\}";
    private static final String PARSE_FIELD_VALUE = "\\$\\{values\\}";
    private static final String SQL_INSERT_QUERY = "INSERT INTO ${table} VALUES(${values})";
    private static String insertQuery = SQL_INSERT_QUERY.replaceFirst(PARSE_TABLE_NAME, "bloodpressure");

    public BloodPressureCuffTask(AtomicInteger pid, ConfigManager cm) {
        this.pid = pid;
        this.dbHost = cm.getPsqlHost();
        this.dbUser = cm.getPsqlUser();
        this.dbPasswd = cm.getPsqlPasswd();
        this.psql = new Postgres(dbHost,dbUser,dbPasswd);
        this.psql.connectToDatabase();
        this.bloodPressureCuff = null;
    }

    @Override
    public void run() {
        loadTask();
    }

    private void loadTask() {
            bloodPressureCuff = new BloodPressureCuff();
            if (psql.isConnected()) {
                int[] bpc = null;
                synchronized (bloodPressureCuff) {
                        bpc = bloodPressureCuff.read();
                }
                if (bpc != null) {
                    bloodPressureCuff = null;
                    if (bpc[0] != 0) {
                        if (oldSys != bpc[0] && oldDia != bpc[1] && oldPulse != bpc[2]) {
                            String insert = insertQuery.replaceFirst(PARSE_FIELD_VALUE, pid.get() + "," + bpc[0] + "," + bpc[1] + "," + bpc[2]);
                            System.out.println("Sys: " + bpc[0] + " Dia: " + bpc[1] + "Pulse: " + bpc[2]);
                            psql.insert(insert);
                            oldSys = bpc[0];
                            oldDia = bpc[1];
                            oldPulse = bpc[2];
                        }
                    }
                }

            } else {
                    psql.connectToDatabase();
            }
    }
}
