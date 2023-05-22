
import java.sql.*;


public class ClientService {

   private static final String CREATE_CLIENT = "INSERT INTO client (name) VALUES (?)";

    private final Connection connection = Database.getInstance().getConnection();

    public long create(String name) {

        long id = 0;
        try (PreparedStatement createSt = connection.prepareStatement(CREATE_CLIENT, Statement.RETURN_GENERATED_KEYS)) {
            createSt.setString(1, name);
            createSt.executeUpdate();
            ResultSet generatedKeys = createSt.getGeneratedKeys();
            generatedKeys.next();
            id = generatedKeys.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
