import config.DBConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Westeros {

    private static final Logger logger = LoggerFactory.getLogger(Westeros.class);

    public static void main(String[] args) {
        try (Connection connection = DBConfig.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate("""
                DROP TABLE IF EXISTS HOUSES;
                """);

                statement.executeUpdate("""
                CREATE TABLE IF NOT EXISTS HOUSES(
                id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                name TEXT NOT NULL,
                words TEXT NOT NULL);
                """);

                int i = statement.executeUpdate("""
                INSERT INTO HOUSES (name, words) VALUES
                ('Targaryen of King''s Landing', 'Fire and Blood'),
                ('Stark of Winterfell', 'Summer is Coming'),
                ('Lannister of Casterly Rock', 'Hear Me Roar!');
                """);

                logger.info("Statement executed {} times", i);

                int u = statement.executeUpdate("""
                UPDATE HOUSES
                SET words = 'Winter is coming'
                WHERE id = 2;
                """);

                logger.info("Statement executed {} times", u);

                try (ResultSet greatHouses = statement.executeQuery("SELECT * FROM HOUSES")) {
                    while (greatHouses.next()) {
                        // Retrieve column values
                        int id = greatHouses.getInt("id");
                        String name = greatHouses.getString("name");
                        String words = greatHouses.getString("words");

                        System.out.printf("""
                        House %d
                        \tName: %s
                        \tWords: %s%n
                        """, id, name, words);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
