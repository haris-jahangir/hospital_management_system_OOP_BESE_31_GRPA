package Main;

import Actors.Patient;
import Actors.Doctor;
import Actors.Admin;
import Managers.ActorsManager;
import Services.*;
import Appointments.AppointmentManager;

public class Main {
    public static void main(String[] args) {

        ActorsManager actorsManager = new ActorsManager();

        Patient p1 = new Patient("Ali Khan", "P001", "ali@email.com", "REC001", "B+");
        Patient p2 = new Patient("Sara Malik", "P002", "sara@email.com", "REC002", "O+");
        Doctor d1 = new Doctor("Dr. Ahmed", "D001", "ahmed@email.com", "Cardiology");
        Doctor d2 = new Doctor("Dr. Ayesha", "D002", "ayesha@email.com", "Neurology");

        actorsManager.addUser(p1);
        actorsManager.addUser(p2);
        actorsManager.addUser(d1);
        actorsManager.addUser(d2);

        AppointmentManager apptManager = new AppointmentManager(actorsManager);

        apptManager.scheduleAppointment("APT001", "P001", "D001", "2025-06-10", "10:00 AM");
        apptManager.scheduleAppointment("APT002", "P002", "D002", "2025-06-11", "02:00 PM");
        apptManager.scheduleEmergency("APT003", "P001", "D002", "2025-06-10", "IMMEDIATE");
        apptManager.rescheduleAppointment("APT001", "2025-06-15", "11:00 AM");
        apptManager.cancelAppointment("APT002");
        apptManager.completeAppointment("APT003");
        apptManager.displayAllAppointments();
        // ========== PERSON 3: SERVICES & DIAGNOSTICS DEMO ==========
        System.out.println("\n========== SERVICES & DIAGNOSTICS MODULE ==========\n");

        // Get the actual Patient and Doctor objects
        Patient ali = (Patient) actorsManager.findUserByID("P001");
        Patient sara = (Patient) actorsManager.findUserByID("P002");
        Doctor ahmed = (Doctor) actorsManager.findUserByID("D001");
        Doctor ayesha = (Doctor) actorsManager.findUserByID("D002");

        // Get the service record manager
        ServiceRecordManager recordManager = ServiceRecordManager.getInstance();

        System.out.println("--- Creating Consultation Services ---");
        // Consultation for Ali with Dr. Ahmed
        ConsultationService cs1 = new ConsultationService("S001", ali, ahmed, false);
        cs1.setSymptoms("Chest pain, shortness of breath, fatigue");
        cs1.setDiagnosis("Hypertension with possible cardiac involvement");
        cs1.setPrescription("Lisinopril 10mg daily, follow up in 2 weeks");
        cs1.execute();
        recordManager.addServiceToPatient(ali, cs1);

        // Consultation for Sara with Dr. Ayesha
        ConsultationService cs2 = new ConsultationService("S002", sara, ayesha, false);
        cs2.setSymptoms("Severe headache, blurred vision");
        cs2.setDiagnosis("Migraine with aura");
        cs2.setPrescription("Sumatriptan 50mg as needed, avoid triggers");
        cs2.execute();
        recordManager.addServiceToPatient(sara, cs2);

        System.out.println("\n--- Creating Lab Tests ---");
        // Lab test for Ali
        LabTestService lab1 = new LabTestService("L001", ali, ahmed, "Blood Test");
        lab1.setTechnician("Tech. Rahman");
        lab1.setResult("BP: 145/95, Cholesterol: 220, HDL: 35", true);
        lab1.execute();
        recordManager.addServiceToPatient(ali, lab1);

        // Lab test for Sara
        LabTestService lab2 = new LabTestService("L002", sara, ayesha, "MRI");
        lab2.setTechnician("Tech. Fatima");
        lab2.setResult("Normal brain scan, no abnormalities detected", false);
        lab2.execute();
        recordManager.addServiceToPatient(sara, lab2);

        System.out.println("\n--- Creating Treatments ---");
        // Treatment for Ali
        TreatmentService tx1 = new TreatmentService("T001", ali, ahmed, "Medication");
        tx1.execute();
        tx1.completeSession();
        tx1.setOutcome("Patient responding well to medication");
        recordManager.addServiceToPatient(ali, tx1);

        // Follow-up consultation for Ali (discounted rate)
        ConsultationService cs3 = new ConsultationService("S003", ali, ahmed, true);
        cs3.setSymptoms("Feeling better, occasional chest discomfort");
        cs3.setDiagnosis("Improving, continue medication");
        cs3.setPrescription("Continue Lisinopril, add baby aspirin");
        cs3.execute();
        recordManager.addServiceToPatient(ali, cs3);

        System.out.println("\n--- Running Diagnostic Engine ---");
        // Use diagnostic engine to recommend tests/treatments
        DiagnosticsEngine.runDiagnosticWorkflow(cs1, recordManager.getServiceRecord(ali), "REC");

        System.out.println("\n--- Displaying Service Records ---");
        // Display complete service record for Ali
        recordManager.displayPatientRecord(ali);

        // Display simple record for Sara
        System.out.println("\n--- Sara's Service Summary ---");
        recordManager.getServiceRecord(sara).displaySimple();

        System.out.println("\n--- FOR PERSON 4 (BILLING) ---");
        System.out.println("This data is ready for invoice generation:\n");

        ServiceRecord aliRecord = recordManager.getServiceRecord(ali);
        System.out.println("Patient: " + ali.getName() + " (" + ali.getID() + ")");
        System.out.println("Completed Billable Services:");
        for (Service s : aliRecord.getCompletedServices()) {
            System.out.println("  • " + s.getServiceType() + " (ID: " + s.getServiceID() + "): $" + s.getCost());
        }
        System.out.println("  ---------------------------------");
        System.out.println("  TOTAL AMOUNT DUE: $" + aliRecord.calculateTotalCost());

        System.out.println("\n========== END OF SERVICES DEMO ==========");
    }
}