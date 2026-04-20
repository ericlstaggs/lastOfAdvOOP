import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaDbTest {
	private static final String DRIVER = "org.mariadb.jdbc.Driver";
	private static final String URL = "jdbc:mariadb://localhost:3306/university";
	private static final String USER = "root";
	private static final String PWD = "";

	public static void main(String[] args) {

		try {
			Class.forName(DRIVER);
			Connection connection = DriverManager.getConnection(URL, USER, PWD);
			Statement stmt = connection.createStatement();

			String query = "select * from classes";

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString(3));
			}

		} catch (ClassNotFoundException cnfe) {
			System.err.println("Configuration error: " + cnfe.getMessage());
		} catch (SQLException se) {
			System.err.println("SQL Exception: " + se);
		}
	}
}
