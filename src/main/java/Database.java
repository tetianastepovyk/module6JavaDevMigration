import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static String connectionUrl = "jdbc:h2:./module6Migration";
    private static final Database DATABASE = new Database();
    private Connection connection;


    public static String getDatabaseUrl(){
        return connectionUrl;
    }
    private Database(){
        try {
            connection = DriverManager.getConnection(connectionUrl);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static Database getInstance(){
        return DATABASE;
    }

    public Connection getConnection(){
        return connection;
    }

    public  void close(){
        try {
            connection.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
