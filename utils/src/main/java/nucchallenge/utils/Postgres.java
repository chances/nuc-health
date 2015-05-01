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

    private static final String PARSE_TABLE_NAME = "\\$\\{table\\}";
    private static final String PARSE_FIELD_VALUE = "\\$\\{values\\}";
    private static final String PARSE_FIELD_FILENAME = "\\$\\{filename\\}";
    private static final String SQL_COPY_QUERY = "COPY ${table} FROM \'${filename}\' WITH DELIMITER ',' CSV HEADER";


    public Postgres(String url) {
        this.connection = null;
        this.resultSet = null;
        this.user = null;
        this.passwd = null;
        this.url = url;
    }

    public Postgres(String url, String user, String passwd) {
        this.url = url;
        this.user = user;
        this.passwd = passwd;
        this.connection = null;
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
                if(this.statement.execute(st)) {
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

    public void copyFromCSV(String filename, String table) {
        try {
            String copyQuery = SQL_COPY_QUERY.replaceFirst(PARSE_TABLE_NAME, table);
            copyQuery = copyQuery.replaceFirst(PARSE_FIELD_FILENAME, filename);

            if(connection != null) {
                this.statement = connection.createStatement();
                if(this.statement.execute(copyQuery))
                    System.err.println("copy failed");


            }
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.WARNING, e.getMessage(), e);
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

    public boolean isConnected() {
        return connection != null;
    }
}
