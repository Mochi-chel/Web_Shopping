import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:mydatabase.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Anslutning till SQLite-databasen lyckades!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*<plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.11.0</version> <!-- eller en nyare version -->
        <configuration>
          <source>20</source>  <!-- Använd Java 20 som språkversion -->
          <target>20</target>  <!-- Kompilera till Java 20 -->
        </configuration>
      </plugin>
    </plugins>
     */
}
