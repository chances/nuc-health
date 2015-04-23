package nucchallenge.utils;
import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by nova on 4/20/15.
 */
public class Sqlite {
    private Connection connection;
    private String dbfile;

    public Sqlite() {
        this.connection = null;
        this.dbfile = null;
    }

    public Sqlite(Connection connection, String dbfile) {
        this.connection = connection;
        this.dbfile = dbfile;
    }

    public void connectToDatabase(String dbfile) throws ClassNotFoundException
    {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        this.dbfile = dbfile;

        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbfile);
            //Statement statement = connection.createStatement();
            //statement.setQueryTimeout(30);  // set timeout to 30 sec.
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        /*finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }*/
        System.out.println("Connected to DB successfully");
    }

    public void createTable(String table_query) {
        try {
            if(connection != null) {
                Statement statement = null;
                statement = connection.createStatement();
                statement.executeUpdate(table_query);
                statement.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        System.out.println("Table created successfully");
    }

    public void dropTable(String table_name) {
        try {
            if(connection != null) {
                Statement statement = null;
                statement = connection.createStatement();
                statement.executeUpdate("DROP TABLE " + table_name + ";");
                System.out.println("Dropped table successfully");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void insert(String insert) {
        try {
            if (connection != null) {
                Statement statement = null;
                statement = connection.createStatement();
                statement.executeUpdate(insert);
                statement.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public ResultSet selectQuery(String select) {
        try {
            if(connection != null) {
                Statement statement = null;
                statement = connection.createStatement();
                return statement.executeQuery(select);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    //must have the .separator set to ","
    public void importCSV(String csvfile, String table) {
        Process process;
        try {
            String[] cmd = {"/bin/bash", "-c", "sqlite3 " + dbfile + " \'.import " + csvfile + " " + table + "\' 2>/dev/null"};
            process = Runtime.getRuntime().exec(cmd);
            /*BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while(br.ready())
                System.out.println(br.readLine());
            */
            process.waitFor();

        } catch (IOException e) {
            System.err.println(e);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
