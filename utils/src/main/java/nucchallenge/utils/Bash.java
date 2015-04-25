package nucchallenge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nova on 4/21/15.
 */
public class Bash {

    public static void removeFirstLineCSV(String filename) {
        Process process;
        try {

            String[] cmd = {"/bin/bash", "-c", "sed -i -e \"1d\" " + filename};
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();

        } catch (IOException e) {
            System.err.println(e);
        } catch (InterruptedException e) {
            System.err.println(e);
        }

    }

    public static void addPatientIDToCSV(String filename, int patientid) {
        Process process;
        try {
            String[] cmd = {"/bin/bash", "-c", "sed -i -e \'s_.*_" + patientid + ',' + "&_' " + filename};
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (IOException e) {
            System.err.println(e);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    public static void syncDatabases(String loadfile) {
        Process process;
        try {
            String[] cmd = {"/home/nova/NUCchallenge/nuc-health/utils/pgloader-3.2.0/build/bin/pgloader", loadfile };
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

    }
}


