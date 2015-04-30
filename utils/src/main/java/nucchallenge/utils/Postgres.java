package nucchallenge.utils;

import sun.misc.Version;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by nova on 4/30/15.
 */
public class Postgres {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String url;
    private String user;
    private String passwd;

    public Postgres(String url) {
        this.connection = null;
        this.resultSet = null;
        this.user = null;
        this.passwd = null;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void insert(String st) {
        try {
            if(connection != null) {
                this.statement = connection.createStatement();
                if(!this.statement.execute(st)) {
                    System.err.println("insert failed");
                }
            }
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                Logger lgr = Logger.getLogger(Version.class.getName());
                lgr.log(Level.WARNING, e.getMessage(), e);
            }
        }
    }

    public ResultSet selectQuery(String st) {
        try {
            if(connection != null) {
                this.statement = connection.createStatement();
                return this.statement.executeQuery(st);
            }
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);

        }
        return null;
    }


    public void connectToDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,user,passwd);

        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
