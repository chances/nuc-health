package nucchallenge.utils;
import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by nova on 4/20/15.
 */
public class Sqlite {
    private Connection connection;

    public void connectToDatabase(String dbfile) throws ClassNotFoundException
    {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        connection = null;
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
}
