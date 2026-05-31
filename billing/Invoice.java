package billing;

import java.util.ArrayList;

public abstract class Invoice {

    protected String invoiceID;
    protected String patientID;
    protected String patientName;
    protected String status;
    protected String invoiceType;
    protected ArrayList<BillableService> services;

    public Invoice(String invoiceID, String patientID, String patientName, String invoiceType) {
        this.invoiceID   = invoiceID;
        this.patientID   = patientID;
        this.patientName = patientName;
        this.invoiceType = invoiceType;
        this.status      = "UNPAID";
        this.services    = new ArrayList<BillableService>();
    }

    // Abstract: each subclass defines its own discount
    public abstract double applyDiscount(double total);
    public abstract String getInvoiceTypeLabel();

    public void addService(BillableService bs) {
        services.add(bs);
    }

    public double getSubTotal() {
        double total = 0;
        for (BillableService bs : services) {
            total += bs.calculateCost();
        }
        return total;
    }

    public double getFinalTotal() {
        return applyDiscount(getSubTotal());
    }

    public void markAsPaid() {
        this.status = "PAID";
    }

    public void printInvoice() {
        System.out.println("============================================");
        System.out.println("   HOSPITAL SYSTEM - " + getInvoiceTypeLabel());
        System.out.println("============================================");
        System.out.println("Invoice ID   : " + invoiceID);
        System.out.println("Patient ID   : " + patientID);
        System.out.println("Patient Name : " + patientName);
        System.out.println("Status       : " + status);
        System.out.println("--------------------------------------------");
        System.out.println("Services:");

        for (BillableService bs : services) {
            System.out.println("  - " + bs.getServiceName() +
                               " | " + bs.getServiceCategory() +
                               " | Rs. " + bs.calculateCost());
        }

        System.out.println("--------------------------------------------");
        System.out.println("Sub Total  : Rs. " + getSubTotal());
        System.out.println("Discount   : Rs. " + (getSubTotal() - getFinalTotal()));
        System.out.println("TOTAL DUE  : Rs. " + getFinalTotal());
        System.out.println("============================================");
    }

    // Getters
    public String getInvoiceID()   { return invoiceID; }
    public String getPatientID()   { return patientID; }
    public String getPatientName() { return patientName; }
    public String getStatus()      { return status; }
    public String getInvoiceType() { return invoiceType; }
    public ArrayList<BillableService> getServices() { return services; }
}