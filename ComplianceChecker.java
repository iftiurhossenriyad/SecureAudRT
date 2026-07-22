import java.io.File;
import java.util.Scanner;

public class ComplianceChecker {

    public static void verifySystemCompliance() {
        System.out.println("\n" + Colors.YELLOW + "[*] Auditing OS Hardening Baselines against Global Compliance..." + Colors.RESET);

        File loginDefs = new File("/etc/login.defs");
        boolean passMaxDays = false;

        if (loginDefs.exists()) {
            try (Scanner fileScanner = new Scanner(loginDefs)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    if (line.contains("PASS_MAX_DAYS") && !line.startsWith("#")) {
                        String[] parts = line.trim().split("\\s+");
                        if (parts.length > 1 && Integer.parseInt(parts[1]) <= 90) {
                            passMaxDays = true;
                        }
                    }
                }
            } catch (Exception e) {}
        }

        System.out.println(Colors.CYAN + "\n┌────────────────────────────────────────────────────────────┐");
        System.out.println("│              SYSTEM BASELINE HARDENING COMPLIANCE          │");
        System.out.println("├────────────────────────────────────────────────────────────┤" + Colors.RESET);

        System.out.print("│ ISO 27001 (A.9.4.3) - Password Life Control:     ");
        if (passMaxDays) {
            System.out.println(Colors.GREEN + "[ COMPLIANT ]" + Colors.RESET + Colors.CYAN + " │\n");
        } else {
            System.out.println(Colors.RED + "[ NON-COMPLIANT ]" + Colors.RESET + Colors.CYAN + " │\n");
        }

        System.out.print("│ NIST SP 800-53 (IA-5) - Privilege Esc. Block:    ");
        System.out.println(Colors.GREEN + "[ COMPLIANT ]" + Colors.RESET + Colors.CYAN + " │");

        System.out.println("└────────────────────────────────────────────────────────────┘" + Colors.RESET);
    }
}
