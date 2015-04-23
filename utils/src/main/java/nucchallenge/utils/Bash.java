package nucchallenge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nova on 4/21/15.
 */
public class Bash {
    //TODO: call sed on csv file to add patientID before each line of data
    //TODO: format csv file

    public static void removeFirstLineCSV(String filename) {
        Process process;
        try{

            String [] cmd = {"/bin/bash","-c", "sed -i -e \"1d\" " + filename};
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
            String [] cmd = {"/bin/bash","-c", "sed -i -e \'s_.*_"+ patientid + ',' +"&_' " + filename};
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (IOException e) {
            System.err.println(e);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
