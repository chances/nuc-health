package nucchallenge.utils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import gnu.io.SerialPort;

public class PulseOxThread implements Runnable {
    private AtomicInteger pid;
    private Postgres psql;
    private PulseOx pulseOx;
    private String ttyUSB;
    private String dbHost;
    private String dbUser;
    private String dbPasswd;

    public PulseOxThread(AtomicInteger pid, ConfigManager cm) {
        this.dbHost = cm.getPsqlHost();
        this.dbUser = cm.getPsqlUser();
        this.dbPasswd = cm.getPsqlPasswd();
        this.ttyUSB = cm.getPulseOxSerialTTY();
        this.psql = new Postgres(dbHost,dbUser,dbPasswd);
        this.psql.connectToDatabase();
        this.pulseOx = new PulseOx();
        this.pulseOx.setParams(19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_ODD);
        this.pulseOx.connect(ttyUSB);
    }

    @Override
    public void run() {
        loadLoop();
    }

    public void loadLoop() {
        while (true) {
            if(psql.isConnected()) {
                if (pulseOx.isConnected()) {
                    try {

                        int[] data = pulseOx.read();

                        if (data != null) {

                            /* 0 is pulse, 1 is ox */
                            if (data[0] != 0 && data[1] != 0) {
                                //psql.insert("INSERT THINGS");
                                System.out.println(data[0] + " " + data[1]);
                            } else {
                                //System.err.println("Null Data");
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    pulseOx.connect(ttyUSB);
                }
            }
            /*try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}

