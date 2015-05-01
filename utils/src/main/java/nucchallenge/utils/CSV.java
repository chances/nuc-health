package nucchallenge.utils;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by nova on 4/30/15.
 */
public class CSV {
    private static final String PARSE_TABLE_NAME = "\\$\\{table\\}";
    private static final String PARSE_FIELD_VALUE = "\\$\\{values\\}";
    private static final String SQL_INSERT_QUERY = "INSERT INTO ${table} VALUES(${values})";
    private CSVReader reader;
    private Postgres database;
    private List<String[]> entries;

    public CSV() {
        this.reader = null;
    }

    public List<String[]> getEntries() {
        return entries;
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

    public void insertIntoDatabase(String url, String user, String passwd, String pid) {
        if(database == null) {
            database = new Postgres(url,user,passwd);
            database.connectToDatabase();
        }

        if(entries != null) {
            //TODO:insert into database
            //set table name for query
            String insertQuery = SQL_INSERT_QUERY.replaceFirst(PARSE_TABLE_NAME, "eeg");

            ListIterator<String[]> csvRowIterator = entries.listIterator();

            //go past the header row
            if (csvRowIterator.hasNext()) {
                csvRowIterator.next();
            }

            while (csvRowIterator.hasNext()) {
                String[] row = csvRowIterator.next();
                String[] goodRowdata = Arrays.copyOfRange(row,0,13);
                String insert = insertQuery.replaceFirst(PARSE_FIELD_VALUE, pid + ','+ StringUtils.join(goodRowdata,','));
                database.insert(insert);
            }
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

    public void close() {
        try {
            if(reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
