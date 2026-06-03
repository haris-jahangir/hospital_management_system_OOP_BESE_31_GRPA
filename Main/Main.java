package Main;

import Actors.*;
import Appointments.*;
import Managers.*;
import Services.*;
import billing.*;
import reporting.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 1. Core Manager Initialization
        ActorsManager actorsManager = new ActorsManager();
        AppointmentManager apptManager = new AppointmentManager(actorsManager);
        ServiceRecordManager svcRecMgr = ServiceRecordManager.getInstance();
        BillingManager billingManager = new BillingManager();

        // 2. Clear, Pre-loaded Test Objects (Directly accessible, zero-stress viva setup)
        Patient p1 = new Patient("Ali Khan", "P001", "ali@email.com", "REC-001", "B+");
        Patient p2 = new Patient("Sara Ahmed", "P002", "sara@email.com", "REC-002", "A+");
        Doctor d1 = new Doctor("Dr. Salman", "D001", "salman@email.com", "Cardiology");
        Doctor d2 = new Doctor("Dr. Ayesha", "D002", "ayesha@email.com", "Orthopedics");

        actorsManager.addUser(p1);
        actorsManager.addUser(p2);
        actorsManager.addUser(d1);
        actorsManager.addUser(d2);

        while (true) {
            System.out.println("\n============================================");
            System.out.println("     HOSPITAL MANAGEMENT SYSTEM (VIVA UTILITY) ");
            System.out.println("============================================");
            System.out.println(" Pre-loaded IDs: Patient [P001, P002] | Doctor [D001, D002]");
            System.out.println("--------------------------------------------");
            System.out.println("1. Register New Patient or Doctor");
            System.out.println("2. Process Appointment Scheduling Flow");
            System.out.println("3. Execute Medical Clinical Services");
            System.out.println("4. Generate Invoices & Process Settlement");
            System.out.println("5. Compile Analytics & Generated Reports");
            System.out.println("0. Terminate Application System");
            System.out.println("============================================");
            System.out.print("Select operational module: ");

            String choice = scanner.nextLine();
            System.out.println();

            // Direct object assignment based on live input selection to eliminate lookup errors
            Patient selectedPatient = choice.contains("2") || choice.contains("3") || choice.contains("4") || choice.contains("5") ? 
                (confirmPatientSelection() == 2 ? p2 : p1) : p1;

            switch (choice) {
                case "1":
                    System.out.println("--- REGISTRATION MODULE ---");
                    System.out.print("1. Patient\n2. Doctor\nChoice: ");
                    String opt = scanner.nextLine();
                    System.out.print("Enter Name: "); String name = scanner.nextLine();
                    System.out.print("Enter Custom ID: "); String id = scanner.nextLine();
                    System.out.print("Enter Email: "); String email = scanner.nextLine();

                    if (opt.equals("1")) {
                        Patient pNew = new Patient(name, id, email, "REC-NEW", "O+");
                        actorsManager.addUser(pNew);
                        System.out.println("[SUCCESS] New Patient tracked in manager registry arrays.");
                    } else {
                        Doctor dNew = new Doctor(name, id, email, "General");
                        actorsManager.addUser(dNew);
                        System.out.println("[SUCCESS] New Doctor tracked in manager registry arrays.");
                    }
                    break;

                case "2":
                    System.out.println("--- APPOINTMENT FLOW ENGINE ---");
                    System.out.println("1. Standard Schedule\n2. Emergency Override\n3. Reschedule\n4. View All");
                    System.out.print("Select Operational Path: ");
                    String apptOpt = scanner.nextLine();

                    if (apptOpt.equals("4")) {
                        apptManager.displayAllAppointments();
                        break;
                    }

                    System.out.print("Enter Appointment Sequence ID: ");
                    String aptId = scanner.nextLine();

                    if (apptOpt.equals("3")) {
                        System.out.print("New Date: "); String d = scanner.nextLine();
                        System.out.print("New Time: "); String t = scanner.nextLine();
                        apptManager.rescheduleAppointment(aptId, d, t);
                    } else {
                        String docId = (selectedPatient == p1) ? "D001" : "D002";
                        if (apptOpt.equals("2")) {
                            apptManager.scheduleEmergency(aptId, selectedPatient.toString(), docId, "02-06-2026", "08:00 AM");
                        } else {
                            apptManager.scheduleAppointment(aptId, selectedPatient.toString(), docId, "02-06-2026", "09:00 AM");
                        }
                        System.out.println("[SUCCESS] Managed data state committed to appointment registry.");
                    }
                    break;

                case "3":
                    System.out.println("--- CLINICAL EXECUTION ENGINE ---");
                    Doctor activeDoc = (selectedPatient == p1) ? d1 : d2;
                    ConsultationService serviceInstance = new ConsultationService("SVC" + System.currentTimeMillis() % 1000, selectedPatient, activeDoc, false);
                    
                    System.out.print("Enter Presenting Clinical Symptoms: "); 
                    serviceInstance.setSymptoms(scanner.nextLine());
                    System.out.print("Enter Differential Diagnosis: "); 
                    serviceInstance.setDiagnosis(scanner.nextLine());
                    System.out.print("Enter Prescriptions / Actions: "); 
                    serviceInstance.setPrescription(scanner.nextLine());
                    
                    serviceInstance.execute();
                    svcRecMgr.addServiceToPatient(selectedPatient, serviceInstance);
                    System.out.println("\n[SUCCESS] Singleton state populated.");
                    svcRecMgr.displayPatientRecord(selectedPatient);
                    break;

                case "4":
                    System.out.println("--- BILLING MODULE ---");
                    ServiceRecord recordBlock = svcRecMgr.getServiceRecord(selectedPatient);
                    if (recordBlock == null) {
                        System.out.println("[ERROR] No medical services found! Execute clinical actions via option 3 first.");
                        break;
                    }
                    System.out.print("1. Standard Billing Ledger\n2. Emergency Billing Ledger\nChoice: ");
                    Invoice bill = scanner.nextLine().equals("2") ? 
                        billingManager.createEmergencyInvoice(selectedPatient, recordBlock) : 
                        billingManager.createStandardInvoice(selectedPatient, recordBlock);
                    
                    bill.printInvoice();
                    System.out.print("\nSettle balance immediately? (yes/no): ");
                    if (scanner.nextLine().equalsIgnoreCase("yes")) {
                        billingManager.payInvoice(bill.getInvoiceID());
                        System.out.println("[SUCCESS] Financial record flagged: PAID.");
                    }
                    break;

                case "5":
                    System.out.println("--- COMPILING AGGREGATE SYSTEM ANALYTICS ---");
                    ServiceRecord sRec = svcRecMgr.getServiceRecord(selectedPatient);
                    ArrayList<Invoice> invoiceHistory = billingManager.getInvoicesByPatient(selectedPatient.toString());

                    if (sRec != null) {
                        new PatientReport(selectedPatient, sRec, invoiceHistory).generateReport();
                        new ServiceReport(sRec).generateReport();
                    }
                    new BillingReport(selectedPatient.toString(), selectedPatient.getName(), billingManager).generateReport();
                    break;

                case "0":
                    System.out.println("Shutting down core interactive environment. Good luck with your Viva!");
                    return;

                default:
                    System.out.println("[WARNING] Selection out of execution bounds.");
            }
            System.out.println("\nOperation completed. Returning to Dashboard configuration...");
        }
    }

    private static int confirmPatientSelection() {
        System.out.print("Select Active Profile Context ID (1 for P001, 2 for P002): ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return 1;
        }
    }
}