
import org.flywaydb.core.Flyway;

public class DatabaseInitService {
    public static void main(String[] args) {

        Flyway.configure()
                .dataSource(Database.getDatabaseUrl(), null, null)
                .load()
                .migrate();

       ClientService clientService = new ClientService();
        System.out.println(clientService.create("New Name Client"));
        System.out.println("Before update Name:  "+ clientService.getById(3));
        clientService.setName(3, "Client New Name");
        System.out.println("After update Name:  "+ clientService.getById(3));
        clientService.deleteById(4);
        System.out.println(clientService.listAll());
    }
}


