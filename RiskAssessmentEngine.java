import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RiskAssessmentEngine {

    public static void executeRiskAssessment() {
        System.out.println("\n" + Colors.YELLOW + "[*] Computing DB Records for GRC Risk Matrix Mapping..." + Colors.RESET);

        String sql = "SELECT risk_level FROM audit_logs";
        int lowCount = 0, medCount = 0, highCount = 0;

        try (Connection conn = DatabaseHelper.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String lvl = rs.getString("risk_level").toUpperCase();
                if (lvl.equals("HIGH")) highCount++;
                else if (lvl.equals("MEDIUM")) medCount++;
                else lowCount++;
            }

            String targetLikelihood = "LOW";
            String targetImpact = "LOW";

            if (highCount > 0) {
                targetLikelihood = "HIGH";
                targetImpact = "HIGH";
            } else if (medCount > 0) {
                targetLikelihood = "MED";
                targetImpact = "MED";
            }

            System.out.println(Colors.CYAN + "\n┌───────────────────────────────────────────────┐");
            System.out.println("│     DYNAMIC LIKELIHOOD VS SYSTEMIC IMPACT     │");
            System.out.println("└───────────────────────────────────────────────┘" + Colors.RESET);

            renderRow("HIGH", targetLikelihood.equals("HIGH"), Colors.BG_RED);
            renderRow("MED ", targetLikelihood.equals("MED"), Colors.BG_YELLOW);
            renderRow("LOW ", targetLikelihood.equals("LOW"), Colors.BG_GREEN);

            System.out.println(Colors.CYAN + "      └────────────┴────────────┴────────────┘");
            System.out.println("          LOW          MED          HIGH    " + Colors.RESET);

            System.out.println("\n" + Colors.BOLD + "[📊 Risk Analysis Summary]" + Colors.RESET);
            System.out.println(" » Threat Level Status: " + (targetLikelihood.equals("HIGH") ? Colors.RED : targetLikelihood.equals("MED") ? Colors.YELLOW : Colors.GREEN) + targetLikelihood + Colors.RESET);
            System.out.println(" » Target System Impact: " + (targetImpact.equals("HIGH") ? Colors.RED : targetImpact.equals("MED") ? Colors.YELLOW : Colors.GREEN) + targetImpact + Colors.RESET);
        } catch (Exception e) {
            System.out.println(Colors.RED + "[-] Assessment Failed: " + e.getMessage() + Colors.RESET);
        }
    }

    private static void renderRow(String label, boolean isMatch, String color) {
        System.out.print(" " + label + " │");
        if (isMatch) {
            System.out.println(color + Colors.BOLD + "  [ ACTIVE ]  " + Colors.RESET + Colors.CYAN + "│            │            │");
        } else {
            System.out.println("            │            │            │");
        }
    }
}
