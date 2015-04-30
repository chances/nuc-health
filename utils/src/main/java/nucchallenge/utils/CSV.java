package nucchallenge.utils;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by nova on 4/30/15.
 */
public class CSV {
    private CSVReader reader;
    private List<String[]> entries;

    public CSV() {
        this.reader = null;
    }

    public void setReader(String file) {
        try {
            if (reader == null) {
                reader = new CSVReader(new FileReader(file), ',');
                entries = reader.readAll();
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void printList() {
        if(entries != null) {
            for (String[] entry : entries) {
                for (int i = 0; i < 15; i++) {
                    System.out.println(entry[i]);
                }
            }
        }
    }
}
