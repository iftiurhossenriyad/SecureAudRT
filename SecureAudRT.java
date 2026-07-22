import java.util.Scanner;

public class SecureAudRT {

    public static void renderMenuInterface() {
        System.out.println(Colors.CYAN + "================================================================" + Colors.RESET);
        System.out.println(Colors.PURPLE + "[SYSTEM BASES] Host: " + System.getProperty("user.name") + " | Target OS: Kali Linux | GRC Database: Connected" + Colors.RESET);
        System.out.println(Colors.CYAN + "================================================================" + Colors.RESET);

        System.out.println(Colors.BLUE + "███████╗███████╗ ██████╗██╗   ██╗██████╗ ███████╗ █████╗ ██╗   ██╗██████╗ ██████╗ ████████╗");
        System.out.println("██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██╔════╝██╔══██╗██║   ██║██╔══██╗██╔══██╗╚══██╔══╝");
        System.out.println("███████╗█████╗  ██║     ██║   ██║██████╔╝█████╗  ███████║██║   ██║██║  ██║██████╔╝   ██║   ");
        System.out.println("╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██╔══╝  ██╔══██║██║   ██║██║  ██║██╔══██╗   ██║   ");
        System.out.println("███████║███████╗╚██████╗╚██████╔╝██║  ██║███████╗██║  ██║╚██████╔╝██████╔╝██║  ██║   ██║   ");
        System.out.println("╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝   ╚═╝   " + Colors.RESET);

        System.out.println(Colors.CYAN + " ┌────────────────────────────────────────────────────────────┐");
        System.out.println(" │ " + Colors.BOLD + "GRC Ultimate Control Center (v5.0 Enterprise-Ready)" + Colors.RESET + Colors.CYAN + "     │");
        System.out.println(" ├────────────────────────────────────────────────────────────┤");
        System.out.println(" │  " + Colors.BOLD + "🔴 SCANNING ENGINES" + Colors.RESET + Colors.CYAN + "               │  " + Colors.BOLD + "🟡 DECISION & GOVERNANCE" + Colors.RESET + Colors.CYAN + "          │");
        System.out.println(" │  [1] Nmap Ports Discovery   │  [5] 3x3 Dynamic Risk Matrix │");
        System.out.println(" │  [2] Native Lynis Auditor   │  [6] Real-time OS Compliance │");
        System.out.println(" │  [3] Nikto Web Auditor      │  [7] Write HTML Dashboard    │");
        System.out.println(" │  [4] Tcpdump Traffic Sniff  │  [8] Display SQLite Ledger   │");
        System.out.println(" │                             │  [9] Framework Specs         │");
        System.out.println(" ├─────────────────────────────┴──────────────────────────────┤");
        System.out.println(" │  " + Colors.RED + "[0] Graceful Shutdown Terminal Engine" + Colors.RESET + Colors.CYAN + "                     │");
        System.out.println(" └────────────────────────────────────────────────────────────┘" + Colors.RESET);

        System.out.println(" 📊 [LIVE SECURITY STATUS] " + DatabaseHelper.getLiveStatsSummary());
        System.out.print("\nEnter your Security Objective: ");
    }

    public static void main(String[] args) {
        DatabaseHelper.initializeDatabase();
        Scanner sc = new Scanner(System.in);

        while (true) {
            renderMenuInterface();
            String rawIn = sc.nextLine();

            switch (rawIn) {
                case "1":
                    System.out.println(Colors.YELLOW + "\n[ISO 27001 Controls] Target scanning checks against open entryways." + Colors.RESET);
                    System.out.print("Target IP/Host to scan: ");
                    String nTarget = sc.nextLine();
                    ScannerEngine.runInteractiveProcess("nmap -F " + nTarget, "Nmap Scan", nTarget, "MEDIUM");
                    break;
                case "2":
                    System.out.println(Colors.YELLOW + "\n[Interactive Security Audit] Initiating full system-wide compliance sweep..." + Colors.RESET);
                    ScannerEngine.runInteractiveProcess("lynis audit system --quick", "Lynis Audit", "Localhost Core OS", "LOW");
                    break;
                case "3":
                    System.out.println(Colors.YELLOW + "\n[OWASP Web Audit] Scanning the web app targeting dangerous endpoints..." + Colors.RESET);
                    System.out.print("Target Web Domain/IP: ");
                    String webTarget = sc.nextLine();
                    ScannerEngine.runInteractiveProcess("nikto -h " + webTarget, "Nikto Web Scan", webTarget, "HIGH");
                    break;
                case "4":
                    System.out.print("Network Sniffing Interface (e.g., eth0, lo): ");
                    String iface = sc.nextLine();
                    ScannerEngine.runInteractiveProcess("sudo tcpdump -c 5 -i " + iface, "Traffic Monitor", iface, "LOW");
                    break;
                case "5":
                    RiskAssessmentEngine.executeRiskAssessment();
                    break;
                case "6":
                    ComplianceChecker.verifySystemCompliance();
                    break;
                case "7":
                    ReportGenerator.generateHTMLDashboard();
                    break;
                case "8":
                    ReportGenerator.displayLogs();
                    break;
                case "9":
                    System.out.println("\nSecureAudRT v5.0 Core: Operational Resilience Suite.");
                    break;
                case "0":
                    System.out.println(Colors.GREEN + "[+] Engine stopped successfully." + Colors.RESET);
                    System.exit(0);
                default:
                    System.out.println(Colors.RED + "[!] Vector Error: Invalid Input Option." + Colors.RESET);
            }

            System.out.println("\nPress [ENTER] to return back to main control dashboard...");
            sc.nextLine();
        }
    }
}
