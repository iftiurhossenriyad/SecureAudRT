import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReportGenerator {

    public static void displayLogs() {
        String sql = "SELECT * FROM audit_logs ORDER BY timestamp DESC";
        try (Connection conn = DatabaseHelper.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n" + Colors.BOLD + "================= SECUREAUDRT ENHANCED DB LEDGER =================" + Colors.RESET);
            while (rs.next()) {
                String risk = rs.getString("risk_level").toUpperCase();
                String color = Colors.GREEN;
                if (risk.equals("HIGH")) color = Colors.RED + Colors.BOLD;
                else if (risk.equals("MEDIUM")) color = Colors.YELLOW;

                System.out.printf("[%s] Block: %-15s | Address: %-20s | Level: %s%s%s\n",
                        rs.getString("timestamp"), rs.getString("module"),
                        rs.getString("target"), color, risk, Colors.RESET);
            }
        } catch (Exception e) {
            System.out.println(Colors.RED + "[-] Ledger Display Failed: " + e.getMessage() + Colors.RESET);
        }
    }

    public static void generateHTMLDashboard() {
        String filename = "dashboard_report.html";
        String sql = "SELECT * FROM audit_logs";
        try (Connection conn = DatabaseHelper.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); FileWriter fw = new FileWriter(filename)) {

            fw.write("<!DOCTYPE html><html><head>");
            fw.write("<meta charset='UTF-8'><title>SecureAudRT v5.0 Dashboard</title>");
            fw.write("<style>");
            fw.write("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #0f172a; color: #f8fafc; padding: 40px; }");
            fw.write("h1 { color: #38bdf8; text-align: center; font-size: 2.5em; margin-bottom: 30px; }");
            fw.write(".card { background: #1e293b; padding: 25px; border-radius: 12px; box-shadow: 0 10px 15px -3px rgba(0,0,0,0.3); }");
            fw.write("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            fw.write("th, td { padding: 15px; border-bottom: 1px solid #334155; text-align: left; }");
            fw.write("th { background: #0284c7; color: white; border-radius: 4px; }");
            fw.write(".HIGH { background: #7f1d1d; color: #fecaca; padding: 4px 8px; border-radius: 4px; font-weight: bold; }");
            fw.write(".MEDIUM { background: #78350f; color: #fef3c7; padding: 4px 8px; border-radius: 4px; font-weight: bold; }");
            fw.write(".LOW { background: #14532d; color: #dcfce7; padding: 4px 8px; border-radius: 4px; font-weight: bold; }");
            fw.write("</style></head><body>");
            fw.write("<h1>🛡️ SecureAudRT v5.0 - Ultimate Executive Dashboard</h1>");
            fw.write("<div class='card'><h2>📋 Comprehensive Penetration & Compliance Logs</h2>");
            fw.write("<table><tr><th>Timestamp</th><th>Audited Module</th><th>Target Host</th><th>Risk Metrics</th></tr>");

            while (rs.next()) {
                fw.write(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td><span class='%s'>%s</span></td></tr>",
                        rs.getString("timestamp"), rs.getString("module"),
                        rs.getString("target"), rs.getString("risk_level"), rs.getString("risk_level")));
            }
            fw.write("</table></div></body></html>");
            System.out.println(Colors.GREEN + "[+] Executive HTML Report written to local storage: " + filename + Colors.RESET);
        } catch (Exception e) {
            System.out.println(Colors.RED + "[-] Report Writing Error: " + e.getMessage() + Colors.RESET);
        }
    }
}
