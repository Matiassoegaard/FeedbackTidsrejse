package Logic;

import Data.DB;
import Model.Kunder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Kundeadministration {
private final DB db;

public  Kundeadministration(DB db) {
    this.db = db;
}

    public int opretKunde (Kunder kunder) throws Exception {

        String sql = "INSERT INTO movies (navn,email,id) VALUES (?, ?, ?)";
        try (Connection c = db.get();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, kunder.getNavn());
            ps.setString(2, kunder.getEmail());
            ps.setInt(3, kunder.getId());

            int rows = ps.executeUpdate();

            return rows;
        }
    }






}
