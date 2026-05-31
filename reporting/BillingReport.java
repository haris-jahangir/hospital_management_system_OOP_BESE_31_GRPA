package reporting;

import billing.BillingManager;
import billing.Invoice;
import billing.BillableService;
import java.util.ArrayList;


public class BillingReport implements Reportable {

    private String patientID;
    private String patientName;
    private ArrayList<Invoice> invoices;

    public BillingReport(String patientID, String patientName, BillingManager billingManager) {
        this.patientID   = patientID;
        this.patientName = patientName;
        this.invoices    = billingManager.getInvoicesByPatient(patientID);
    }

    @Override
    public String getReportTitle() {
        return "BILLING REPORT - " + patientName;
    }

    @Override
    public void generateReport() {
        System.out.println("============================================");
        System.out.println("   " + getReportTitle());
        System.out.println("============================================");
        System.out.println("Patient ID : " + patientID);

        if (invoices.isEmpty()) {
            System.out.println("No invoices found.");
            System.out.println("============================================");
            return;
        }

        double grandTotal = 0;

        for (Invoice inv : invoices) {
            System.out.println("--------------------------------------------");
            System.out.println("Invoice ID : " + inv.getInvoiceID());
            System.out.println("Type       : " + inv.getInvoiceType());
            System.out.println("Status     : " + inv.getStatus());
            System.out.println("Services:");
            for (BillableService bs : inv.getServices()) {
                System.out.println("  - " + bs.getServiceName() + " : Rs. " + bs.calculateCost());
            }
            System.out.println("Sub Total  : Rs. " + inv.getSubTotal());
            System.out.println("Total Due  : Rs. " + inv.getFinalTotal());
            grandTotal += inv.getFinalTotal();
        }

        System.out.println("--------------------------------------------");
        System.out.println("GRAND TOTAL : Rs. " + grandTotal);
        System.out.println("============================================");
    }
}