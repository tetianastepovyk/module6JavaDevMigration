
import entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientService {
   private static final String CREATE_CLIENT = "INSERT INTO client (name) VALUES (?)";
    private static final String CLIENT_BY_ID = "SELECT name FROM client WHERE id = ?";
    private static final String UPDATE_CLIENTS_NAME = "UPDATE client SET name = ? WHERE id = ?";

    private static final String DELETE_CLIENT = "DELETE FROM client WHERE id = ?";

    private static final String DELETE_PROJECT = "DELETE FROM project WHERE ID = ?";

    private static final String DELETE_PROJECT_WORKER = "DELETE FROM project_worker WHERE PROJECT_ID = ?";

    private static final String SELECT_ALL_CLIENTS = "SELECT * FROM client";

    private static final String PROJECT_ID_BY_CLIENT_ID = "SELECT ID FROM project WHERE CLIENT_ID = ?";

    private final Connection connection = Database.getInstance().getConnection();

//додає нового клієнта з іменем name. Повертає ідентифікатор щойно створеного клієнта.
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
    ////// видаляє клієнта з ідентифікатором id
    public void deleteById(long id) {

        try (PreparedStatement getByIdSt = connection.prepareStatement(PROJECT_ID_BY_CLIENT_ID)) {
            getByIdSt.setLong(1, id);
            ResultSet resultSet = getByIdSt.executeQuery();
            while (resultSet.next()) {
                long idLong = resultSet.getLong("ID");
                deleteProjectWorkerById(idLong );
                deleteProjectById(idLong );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        deleteClientById(id);
    }
//повертає назву клієнта з ідентифікатором id
    public String getById(long id) {

        String name = null;
        try (PreparedStatement getByIdSt = connection.prepareStatement(CLIENT_BY_ID)) {
            getByIdSt.setLong(1, id);
            ResultSet resultSet = getByIdSt.executeQuery();
            resultSet.next();
            name = resultSet.getString("NAME");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
    ///встановлює нове ім'я name для клієнта з ідентифікатором id
    public void setName(long id, String name) {
        try (PreparedStatement setNameSt = connection.prepareStatement(UPDATE_CLIENTS_NAME)) {
            setNameSt.setString(1, name);
            setNameSt.setLong(2, id);
            setNameSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //// видаляє строки з ідентифікатором PROJECT_ID
    public void deleteProjectWorkerById(long projectId) {
        try (PreparedStatement deleteByIdSt = connection.prepareStatement(DELETE_PROJECT_WORKER)) {
            deleteByIdSt.setLong(1, projectId);
            deleteByIdSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //// видаляє Project за ідентифікатором id
    public void deleteProjectById(long id) {
        try (PreparedStatement deleteByIdSt = connection.prepareStatement(DELETE_PROJECT)) {
            deleteByIdSt.setLong(1, id);
            deleteByIdSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //// видаляє клієнта з ідентифікатором id
    public void deleteClientById(long id) {
        try (PreparedStatement deleteByIdSt = connection.prepareStatement(DELETE_CLIENT)) {
            deleteByIdSt.setLong(1, id);
            deleteByIdSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//повертає всіх клієнтів з БД у вигляді колекції об'єктів типу Client (цей клас створи сам, у ньому має бути 2 поля - id та name)
    public List<Client> listAll() {
        List<Client> clientList = new ArrayList<>();
        try (PreparedStatement listAllSt = connection.prepareStatement(SELECT_ALL_CLIENTS)) {
            ResultSet resultSet = listAllSt.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong("ID"));
                client.setName(resultSet.getString("NAME"));
                clientList.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }

}
