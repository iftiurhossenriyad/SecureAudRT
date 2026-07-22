import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ScannerEngine {

    public static void runInteractiveProcess(String command, String module, String target, String risk) {
        System.out.println(Colors.CYAN + "[*] Launching: " + command + Colors.RESET);
        StringBuilder logBuilder = new StringBuilder();
        try {
            ProcessBuilder builder = new ProcessBuilder(command.split(" "));
            builder.redirectErrorStream(true);
            Process process = builder.start();

            InputStream in = process.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                String chunk = new String(buffer, 0, bytesRead);
                System.out.print(chunk);
                logBuilder.append(chunk);
            }

            process.waitFor();
            saveLogToDB(module, target, risk, logBuilder.toString());
            System.out.println(Colors.GREEN + "\n[+] Operation Finished Successfully." + Colors.RESET);
        } catch (Exception e) {
            System.out.println(Colors.RED + "[-] Process execution failed: " + e.getMessage() + Colors.RESET);
        }
    }

    public static void saveLogToDB(String module, String target, String risk, String details) {
        String sql = "INSERT INTO audit_logs (module, target, risk_level, details) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (conn != null) {
                pstmt.setString(1, module);
                pstmt.setString(2, target);
                pstmt.setString(3, risk);
                pstmt.setString(4, details.length() > 1000 ? details.substring(0, 1000) : details);
                pstmt.executeUpdate();
                System.out.println(Colors.GREEN + "[+] Event persistent log entry stored in SQLite DB." + Colors.RESET);
            }
        } catch (Exception e) {
            System.out.println(Colors.RED + "[-] DB Error: " + e.getMessage() + Colors.RESET);
        }
    }
}
