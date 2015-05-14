package nucchallenge;

import nucchallenge.utils.*;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main entry point into the nuc-health application.
 */
public class App
{
    public static void main(String[] args)
    {
       /* final Credentials credentials = new Credentials("nuc", "nuc");

        System.out.println("Hello Nerds!");

        System.out.println("\nValid credentials:");
        System.out.println("\tUsername: " + credentials.getUsername());
        System.out.println("\tPassword: " + credentials.getPassword());
        System.out.println();

        // Schedule a job for the event-dispatching thread:
        //   Creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() -> {
            DialogResult result = LockDialog.showDialog(credentials);

            System.out.println(result.toString());

            if (result == DialogResult.OK) {
                result = PatientDialog.showDialog(null);

                System.out.println();
                System.out.println(result.toString());
            }
        });*/
        /* Get Configurations */
        ConfigManager configs = new ConfigManager("config.json");
        String dbHost = configs.getPsqlHost();
        String dbUser = configs.getPsqlUser();
        String dbPasswd = configs.getPsqlPasswd();
        String pulseOxTTY = configs.getPulseOxSerialTTY();
        String patientCSVDir = configs.getPatientCSVDir();

        AtomicInteger pid = new AtomicInteger(1);


        /* print config info */
        System.out.println(dbHost);
        System.out.println(dbUser);
        System.out.println(dbPasswd);
        System.out.println(pulseOxTTY);
        System.out.println(patientCSVDir);

        /* test bpc */
        /*BloodPressureCuff bloodPressureCuff = new BloodPressureCuff();
        int [] bpc = bloodPressureCuff.read();
        for (int aBpc : bpc) {
            System.out.println(aBpc);
        }*/


        /* Set Up Devices*/
        BloodPressureCuffTask bloodPressureCuffTask = new BloodPressureCuffTask(pid,configs);
        CSVLoadTask csvLoadTask = new CSVLoadTask("patient.csv", pid, configs);
        PulseOxThread pulseOxThread = new PulseOxThread(pid,configs);
        BloodPressureCuffThread bloodPressureCuffThread = new BloodPressureCuffThread(bloodPressureCuffTask);
        CSVLoadThread csvLoadThread = new CSVLoadThread(csvLoadTask);

        ExecutorService es = Executors.newSingleThreadExecutor();
        ExecutorService es2 = Executors.newSingleThreadScheduledExecutor();
        ExecutorService es3 = Executors.newSingleThreadScheduledExecutor();

        es.submit(pulseOxThread);
        es2.submit(bloodPressureCuffThread);
        es3.submit(csvLoadThread);

        es.execute(pulseOxThread);
        es2.execute(bloodPressureCuffThread);
        es3.execute(csvLoadThread);

        es.shutdown();
        es2.shutdown();
        es3.shutdown();
        while (!es3.isTerminated() || !es.isTerminated() || !es2.isTerminated() ) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                es.shutdownNow();
                es2.shutdownNow();
                es3.shutdownNow();
            }
        }
    }
}
