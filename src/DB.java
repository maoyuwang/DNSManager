import java.sql.*;

public class DB {
    private Connection c = null;

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
