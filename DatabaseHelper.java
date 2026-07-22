import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:secure_audrt.db";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            System.out.println(Colors.RED + "[-] Connection Failed: " + e.getMessage() + Colors.RESET);
        }
        return conn;
    }

    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS audit_logs (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                     "module TEXT, " +
                     "target TEXT, " +
                     "risk_level TEXT, " +
                     "details TEXT);";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            if (conn != null) {
                stmt.execute(sql);
            }
        } catch (Exception e) {
            System.out.println(Colors.RED + "[-] DB Initialization Error: " + e.getMessage() + Colors.RESET);
        }
    }

    public static String getLiveStatsSummary() {
        int total = 0, high = 0, med = 0, low = 0;
        String sql = "SELECT risk_level FROM audit_logs";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                total++;
                String lvl = rs.getString("risk_level").toUpperCase();
                if (lvl.equals("HIGH")) high++;
                else if (lvl.equals("MEDIUM")) med++;
                else low++;
            }
        } catch (Exception e) {
            return "[ Counter Offline ]";
        }
        return String.format("%sTotal Scans:%s %d | %sHigh Risks:%s %d | %sMedium:%s %d | %sLow:%s %d",
                Colors.BOLD, Colors.RESET, total,
                Colors.RED, Colors.RESET, high,
                Colors.YELLOW, Colors.RESET, med,
                Colors.GREEN, Colors.RESET, low);
    }
}
