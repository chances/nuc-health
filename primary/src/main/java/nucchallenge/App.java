package nucchallenge;

import nucchallenge.ui.Credentials;
import nucchallenge.ui.DialogResult;
import nucchallenge.ui.LockDialog;

/**
 * Main entry point into the nuc-health application.
 */
public class App
{
    public static void main(String[] args)
    {
        final Credentials credentials = new Credentials("nuc", "nuc");

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
        });
    }
}
