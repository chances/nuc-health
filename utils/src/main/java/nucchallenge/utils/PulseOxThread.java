package nucchallenge.utils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import gnu.io.SerialPort;

public class PulseOxThread implements Runnable {
    private AtomicInteger pid;
    private Postgres psql;
    private PulseOx pulseOx;
    private String ttyUSB;

    /* SQL stuff */
    private static final String PARSE_TABLE_NAME = "\\$\\{table\\}";
    private static final String PARSE_FIELD_VALUE = "\\$\\{values\\}";
    private static final String SQL_INSERT_QUERY = "INSERT INTO ${table} VALUES(${values})";


    public PulseOxThread(AtomicInteger pid, ConfigManager cm) {
        String dbHost = cm.getPsqlHost();
        String dbUser = cm.getPsqlUser();
        String dbPasswd = cm.getPsqlPasswd();

        this.ttyUSB = cm.getPulseOxSerialTTY();
        this.psql = new Postgres(dbHost, dbUser, dbPasswd);
        this.psql.connectToDatabase();
        this.pulseOx = new PulseOx();
        this.pulseOx.setParams(19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_ODD);
        this.pulseOx.connect(ttyUSB);
        this.pid = pid;
    }

    @Override
    public void run() {
        loadLoop();
    }

    private void loadLoop() {
        String insertQuery = SQL_INSERT_QUERY.replaceFirst(PARSE_TABLE_NAME, "pulseox");
        while (true) {
            if(psql.isConnected()) {
                if (pulseOx.isConnected()) {
                    try {
                        int[] data = pulseOx.read();

                        if (data != null) {

                            /* 0 is pulse, 1 is ox */
                            if ((data[0] != 0 && data[1] != 0) && (data[0] < 100)){
                                String insert = insertQuery.replaceFirst(PARSE_FIELD_VALUE, pid.toString() + ',' + data[1] + ',' + data[0]);
                                psql.insert(insert);
                                System.err.println(pid.get() + " " + data[0] + " " + data[1]);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    pulseOx.connect(ttyUSB);
                }
            }
        }
    }
}

