package billing;

import Actors.Patient;
import Services.Service;
import Services.ServiceRecord;
import java.util.ArrayList;
import java.util.HashMap;


public class BillingManager {

    private HashMap<String, Invoice> invoices;  // invoiceID -> Invoice
    private int invoiceCounter;

    public BillingManager() {
        this.invoices       = new HashMap<String, Invoice>();
        this.invoiceCounter = 1;
    }

    // Generate a unique invoice ID
    private String generateInvoiceID() {
        return "INV-" + invoiceCounter++;
    }

    // Create a standard invoice for a patient using their ServiceRecord
    public StandardInvoice createStandardInvoice(Patient patient, ServiceRecord record) {
        String id = generateInvoiceID();
        StandardInvoice invoice = new StandardInvoice(id, patient.getID(), patient.getName());

        for (Service s : record.getCompletedServices()) {
            invoice.addService(new BillableService(s));
        }

        invoices.put(id, invoice);
        System.out.println("[BILLING] Standard invoice created: " + id + " for " + patient.getName());
        return invoice;
    }

    // Create an emergency invoice for a patient
    public EmergencyInvoice createEmergencyInvoice(Patient patient, ServiceRecord record) {
        String id = generateInvoiceID();
        EmergencyInvoice invoice = new EmergencyInvoice(id, patient.getID(), patient.getName());

        for (Service s : record.getCompletedServices()) {
            invoice.addService(new BillableService(s));
        }

        invoices.put(id, invoice);
        System.out.println("[BILLING] Emergency invoice created: " + id + " for " + patient.getName());
        return invoice;
    }

    // Find invoice by ID
    public Invoice findInvoiceByID(String invoiceID) {
        return invoices.get(invoiceID);
    }

    // Get all invoices for a specific patient
    public ArrayList<Invoice> getInvoicesByPatient(String patientID) {
        ArrayList<Invoice> result = new ArrayList<Invoice>();
        for (Invoice inv : invoices.values()) {
            if (inv.getPatientID().equalsIgnoreCase(patientID)) {
                result.add(inv);
            }
        }
        return result;
    }

    // Mark an invoice as paid
    public void payInvoice(String invoiceID) {
        Invoice inv = invoices.get(invoiceID);
        if (inv == null) {
            System.out.println("[ERROR] Invoice not found: " + invoiceID);
            return;
        }
        inv.markAsPaid();
        System.out.println("[BILLING] Invoice " + invoiceID + " marked as PAID.");
    }

    // Print all invoices
    public void printAllInvoices() {
        if (invoices.isEmpty()) {
            System.out.println("[BILLING] No invoices in the system.");
            return;
        }
        for (Invoice inv : invoices.values()) {
            inv.printInvoice();
        }
    }
}