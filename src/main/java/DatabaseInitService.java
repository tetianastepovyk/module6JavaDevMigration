
import org.flywaydb.core.Flyway;

public class DatabaseInitService {
    public static void main(String[] args) {

        Flyway.configure()
                .dataSource(Database.getDatabaseUrl(), null, null)
                .load()
                .migrate();

        ClientService clientService = new ClientService();
        System.out.println(clientService.create("New Name Client"));

    }
}
