# SecureAudRT — Secure Audit & Risk Tracking

A Java-based Security Governance, Risk Assessment & Compliance (GRC) toolkit for Kali Linux. It wraps **Nmap**, **Lynis**, **Nikto**, and **tcpdump** behind one color-coded terminal menu, logs every scan into a local SQLite database, and drives a dynamic risk matrix and compliance checker aligned to ISO 27001 and NIST SP 800-53.

This repository contains the complete, ready-to-run source code. No file needs to be created or renamed — clone this repo on Kali Linux and compile directly.

## Requirements

- Kali Linux (or any Linux distro) with `nmap`, `lynis`, `nikto`, and `tcpdump` installed
- Java JDK 11 or newer (`javac -version` to check)

## How to Run

Clone or download this repository, then from inside the project folder:

```bash
# Compile
javac -cp .:sqlite-jdbc-3.45.2.0.jar *.java

# Run
java -cp .:sqlite-jdbc-3.45.2.0.jar:slf4j-api-2.0.9.jar:slf4j-simple-2.0.9.jar SecureAudRT
```

That's it — the color menu will launch immediately.

## Project Files

| File | Purpose |
|---|---|
| `Colors.java` | ANSI color codes for the terminal UI |
| `DatabaseHelper.java` | SQLite connection, table setup, live stats |
| `ScannerEngine.java` | Runs Nmap / Lynis / Nikto / tcpdump live and logs results |
| `RiskAssessmentEngine.java` | 3x3 likelihood-vs-impact risk matrix |
| `ComplianceChecker.java` | Checks live `/etc/login.defs` against ISO 27001 / NIST SP 800-53 |
| `ReportGenerator.java` | Console ledger + HTML dashboard export |
| `SecureAudRT.java` | Main menu and entry point |
| `sqlite-jdbc-3.45.2.0.jar` | SQLite JDBC driver (bundled, no download needed) |
| `slf4j-api-2.0.9.jar`, `slf4j-simple-2.0.9.jar` | Logging libraries required by the SQLite driver (bundled) |

## Menu Overview

```
[1] Nmap Ports Discovery       [5] 3x3 Dynamic Risk Matrix
[2] Native Lynis Auditor       [6] Real-time OS Compliance
[3] Nikto Web Auditor          [7] Write HTML Dashboard
[4] Tcpdump Traffic Sniff      [8] Display SQLite Ledger
                                [9] Framework Specs
[0] Graceful Shutdown
```

Every scan is saved into `secure_audrt.db` (created automatically on first run), and option 7 exports the full audit history to `dashboard_report.html`, viewable in any browser.

## Notes

- Options 1–4 launch real system tools (`nmap`, `lynis`, `nikto`, `tcpdump`), so those must be installed and on your `PATH`.
- Option 4 (tcpdump) requires `sudo` privileges to capture packets.
- `secure_audrt.db` and `dashboard_report.html` are generated at runtime and are not part of this repository — they will appear after you run the program.

## Author

**Md Iftiur Hossen Riyad**
Department of Cyber Security Engineering, University of Frontier Technology, Bangladesh (UFTB)
