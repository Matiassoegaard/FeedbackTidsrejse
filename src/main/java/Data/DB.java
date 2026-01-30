package Data;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    private static final String URL =
            "jdbc:mysql://localhost:3306/Tidsrejseagenturetdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "Kage!";

    /**
     * Opretter en forbindelse til databasen.
     * try-with-resources sker i kaldende kode (service), så vi lukker ikke her.
     */
    public Connection get() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }


}
