package reporting;

import Actors.Patient;
import Services.ServiceRecord;
import billing.Invoice;
import java.util.ArrayList;


public class PatientReport implements Reportable {

    private Patient patient;
    private ServiceRecord serviceRecord;
    private ArrayList<Invoice> invoices;

    public PatientReport(Patient patient, ServiceRecord serviceRecord, ArrayList<Invoice> invoices) {
        this.patient       = patient;
        this.serviceRecord = serviceRecord;
        this.invoices      = invoices;
    }

    @Override
    public String getReportTitle() {
        return "PATIENT REPORT - " + patient.getName();
    }

    @Override
    public void generateReport() {
        System.out.println("============================================");
        System.out.println("   " + getReportTitle());
        System.out.println("============================================");
        System.out.println("Patient ID    : " + patient.getID());
        System.out.println("Name          : " + patient.getName());
        System.out.println("Email         : " + patient.getEmail());
        System.out.println("Blood Group   : " + patient.getBloodGroup());
        System.out.println("Record ID     : " + patient.getPatientRecordID());

        // Medical History
        System.out.println("--------------------------------------------");
        System.out.println("Medical History:");
        if (patient.getMedicalHistory().isEmpty()) {
            System.out.println("  No history recorded.");
        } else {
            for (String entry : patient.getMedicalHistory()) {
                System.out.println("  - " + entry);
            }
        }

        // Services
        System.out.println("--------------------------------------------");
        System.out.println("Services Received: " + serviceRecord.getTotalServiceCount());
        System.out.println("Completed        : " + serviceRecord.getCompletedCount());
        System.out.println("Total Cost       : Rs. " + serviceRecord.calculateTotalCost());

        // Invoices
        System.out.println("--------------------------------------------");
        System.out.println("Invoices:");
        if (invoices.isEmpty()) {
            System.out.println("  No invoices generated.");
        } else {
            for (Invoice inv : invoices) {
                System.out.println("  - " + inv.getInvoiceID() +
                                   " | " + inv.getInvoiceType() +
                                   " | Rs. " + inv.getFinalTotal() +
                                   " | " + inv.getStatus());
            }
        }
        System.out.println("============================================");
    }
}