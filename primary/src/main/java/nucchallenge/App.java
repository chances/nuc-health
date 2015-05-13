package nucchallenge;

import nucchallenge.ui.Credentials;
import nucchallenge.ui.DialogResult;
import nucchallenge.ui.LockDialog;
import nucchallenge.ui.PatientDialog;
import nucchallenge.utils.BloodPressureCuff;
import nucchallenge.utils.ConfigManager;
import nucchallenge.utils.PulseOx;
import nucchallenge.utils.PulseOxThread;

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

        PulseOxThread pulseOxThread = new PulseOxThread(pid,configs);
        ExecutorService es = Executors.newSingleThreadExecutor();

        es.submit(pulseOxThread);
        es.execute(pulseOxThread);

        es.shutdownNow();
        while (!es.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                es.shutdownNow();
                e.printStackTrace();
            }
        }
    }
}
