package Main;

import Actors.*;
import Appointments.*;
import Managers.*;
import Services.*;
import billing.*;
import reporting.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        System.out.println("\n============================================");
        System.out.println("   HOSPITAL MANAGEMENT SYSTEM - OOP PBL   ");
        System.out.println("============================================\n");

        // ══════════════════════════════════════════
        // 1. SETUP - Create Managers
        // ══════════════════════════════════════════
        ActorsManager actorsManager    = new ActorsManager();
        AppointmentManager apptManager = new AppointmentManager(actorsManager);
        ServiceRecordManager svcRecMgr = ServiceRecordManager.getInstance();
        BillingManager billingManager  = new BillingManager();

        // ══════════════════════════════════════════
        // 2. USER REGISTRATION
        // ══════════════════════════════════════════
        System.out.println("============================================");
        System.out.println("         STEP 1: USER REGISTRATION         ");
        System.out.println("============================================");

        Patient p1 = new Patient("Ali Khan",   "P001", "ali@email.com",  "REC-001", "B+");
        Patient p2 = new Patient("Sara Ahmed", "P002", "sara@email.com", "REC-002", "A+");

        Doctor d1 = new Doctor("Dr. Salman",  "D001", "salman@email.com", "Cardiology");
        Doctor d2 = new Doctor("Dr. Ayesha",  "D002", "ayesha@email.com", "Orthopedics");

        Admin a1 = new Admin("Usman Ali", "A001", "usman@email.com", "Administration");

        actorsManager.addUser(p1);
        actorsManager.addUser(p2);
        actorsManager.addUser(d1);
        actorsManager.addUser(d2);
        actorsManager.addUser(a1);

        p1.displayProfile();
        d1.displayProfile();
        a1.displayProfile();

        // ══════════════════════════════════════════
        // 3. APPOINTMENT SCHEDULING
        // ══════════════════════════════════════════
        System.out.println("============================================");
        System.out.println("      STEP 2: APPOINTMENT SCHEDULING       ");
        System.out.println("============================================");

        apptManager.scheduleAppointment("APT001", "P001", "D001", "02-06-2026", "09:00 AM");
        apptManager.scheduleAppointment("APT002", "P002", "D002", "02-06-2026", "10:00 AM");
        apptManager.scheduleEmergency("APT003", "P001", "D001", "02-06-2026", "08:00 AM");
        apptManager.rescheduleAppointment("APT002", "03-06-2026", "11:00 AM");
        apptManager.cancelAppointment("APT002");
        apptManager.completeAppointment("APT001");
        apptManager.completeAppointment("APT003");
        apptManager.displayAllAppointments();

        // ══════════════════════════════════════════
        // 4. SERVICES EXECUTION
        // ══════════════════════════════════════════
        System.out.println("============================================");
        System.out.println("        STEP 3: SERVICES EXECUTION         ");
        System.out.println("============================================");

        // Patient 1 - Consultation
        ConsultationService cons1 = new ConsultationService("SVC001", p1, d1, false);
        cons1.setSymptoms("Chest pain and shortness of breath");
        cons1.setDiagnosis("Possible heart condition");
        cons1.setPrescription("ECG and blood tests recommended");
        cons1.execute();
        svcRecMgr.addServiceToPatient(p1, cons1);

        // Patient 1 - Lab Test
        LabTestService lab1 = new LabTestService("SVC002", p1, d1, "ECG");
        lab1.setTechnician("Tech. Bilal");
        lab1.setResult("Mild arrhythmia detected", true);
        lab1.execute();
        svcRecMgr.addServiceToPatient(p1, lab1);

        // Patient 1 - Blood Test
        LabTestService lab2 = new LabTestService("SVC003", p1, d1, "Blood Test");
        lab2.setTechnician("Tech. Bilal");
        lab2.setResult("Normal blood count", false);
        lab2.execute();
        svcRecMgr.addServiceToPatient(p1, lab2);

        // Patient 1 - Treatment
        TreatmentService treat1 = new TreatmentService("SVC004", p1, d1, "Medication", 3);
        treat1.execute();
        treat1.completeSession();
        treat1.completeSession();
        treat1.setOutcome("Patient responding well to medication");
        svcRecMgr.addServiceToPatient(p1, treat1);

        // Patient 2 - Consultation
        ConsultationService cons2 = new ConsultationService("SVC005", p2, d2, false);
        cons2.setSymptoms("Knee pain after fall");
        cons2.setDiagnosis("Fracture in left knee");
        cons2.setPrescription("X-Ray and physiotherapy required");
        cons2.execute();
        svcRecMgr.addServiceToPatient(p2, cons2);

        // Patient 2 - X-Ray
        LabTestService lab3 = new LabTestService("SVC006", p2, d2, "X-Ray");
        lab3.setTechnician("Tech. Zara");
        lab3.setResult("Hairline fracture confirmed", true);
        lab3.execute();
        svcRecMgr.addServiceToPatient(p2, lab3);

        // Patient 2 - Physiotherapy
        TreatmentService treat2 = new TreatmentService("SVC007", p2, d2, "Physiotherapy", 3);
        treat2.execute();
        treat2.completeSession();
        treat2.completeSession();
        treat2.setOutcome("Physiotherapy ongoing");
        svcRecMgr.addServiceToPatient(p2, treat2);

        // Display service records
        svcRecMgr.displayPatientRecord(p1);
        svcRecMgr.displayPatientRecord(p2);

        // ══════════════════════════════════════════
        // 5. DIAGNOSTICS ENGINE
        // ══════════════════════════════════════════
        System.out.println("============================================");
        System.out.println("        STEP 4: DIAGNOSTICS ENGINE         ");
        System.out.println("============================================");

        DiagnosticsEngine.runDiagnosticWorkflow(cons1, null, "SVC");
        DiagnosticsEngine.runDiagnosticWorkflow(cons2, null, "SVC");

        // ══════════════════════════════════════════
        // 6. BILLING
        // ══════════════════════════════════════════
        System.out.println("============================================");
        System.out.println("          STEP 5: BILLING MODULE           ");
        System.out.println("============================================");

        ServiceRecord rec1 = svcRecMgr.getServiceRecord(p1);
        ServiceRecord rec2 = svcRecMgr.getServiceRecord(p2);

        // Standard invoice for P001 - gets 10% discount if total > 500
        StandardInvoice inv1 = billingManager.createStandardInvoice(p1, rec1);
        inv1.printInvoice();

        // Emergency invoice for P001 - no discount
        EmergencyInvoice inv2 = billingManager.createEmergencyInvoice(p1, rec1);
        inv2.printInvoice();

        // Standard invoice for P002
        StandardInvoice inv3 = billingManager.createStandardInvoice(p2, rec2);
        inv3.printInvoice();

        // Pay invoice
        billingManager.payInvoice(inv1.getInvoiceID());

        // ══════════════════════════════════════════
        // 7. ACCESS CONTROL
        // ══════════════════════════════════════════
        System.out.println("============================================");
        System.out.println("        STEP 6: ACCESS CONTROL             ");
        System.out.println("============================================");

        System.out.println("Is P001 a PATIENT? " + p1.isAuthorized("PATIENT"));
        System.out.println("Is D001 a DOCTOR?  " + d1.isAuthorized("DOCTOR"));
        System.out.println("Is A001 an ADMIN?  " + a1.isAuthorized("ADMIN"));
        System.out.println("Is P001 a DOCTOR?  " + p1.isAuthorized("DOCTOR"));

        actorsManager.checkAccess("P001", "PATIENT");
        actorsManager.checkAccess("D001", "ADMIN");

        // ══════════════════════════════════════════
        // 8. REPORTS
        // ══════════════════════════════════════════
        System.out.println("============================================");
        System.out.println("         STEP 7: REPORTS MODULE            ");
        System.out.println("============================================");

        // Patient Report P001
        ArrayList<Invoice> p1Invoices = billingManager.getInvoicesByPatient("P001");
        PatientReport patReport1 = new PatientReport(p1, rec1, p1Invoices);
        patReport1.generateReport();

        // Patient Report P002
        ArrayList<Invoice> p2Invoices = billingManager.getInvoicesByPatient("P002");
        PatientReport patReport2 = new PatientReport(p2, rec2, p2Invoices);
        patReport2.generateReport();

        // Billing Report P001
        BillingReport billReport = new BillingReport("P001", p1.getName(), billingManager);
        billReport.generateReport();

        // Service Report P001
        ServiceReport svcReport1 = new ServiceReport(rec1);
        svcReport1.generateReport();

        // Service Report P002
        ServiceReport svcReport2 = new ServiceReport(rec2);
        svcReport2.generateReport();

        System.out.println("\n============================================");
        System.out.println("          SYSTEM DEMO COMPLETE             ");
        System.out.println("============================================\n");
    }
}