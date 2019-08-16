import java.sql.*;

/**
 * A DB class is a client used to connect SQLite Database.
 */
public class DB {
    private Connection c = null;

    /**
     * Construct a Database Client.
     */
    DB()
    {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:config.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Successfully Load SQLite Driver.");
    }

    /**
     * Execute a SQL SELECT Query.
     * @param sqlString The SQL command to query.
     * @return  The result set after executing the SQL query.
     */
    public ResultSet executeQuery(String sqlString){
        String result = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = c.createStatement();
            rs = stmt.executeQuery(sqlString);
            stmt.close();
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }

        return rs;
    }

    /**
     * Execute a SQL UPDATE/DELETE query.
     * @param sqlString The SQL command to run.
     * @return  The result set after running the SQL query.
     */
    public int executeUpdate(String sqlString){
        Statement stmt = null;
        int rs = 0;
        try{
            stmt = c.createStatement();
            rs = stmt.executeUpdate(sqlString);
            stmt.close();
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }

        return rs;
    }
}
