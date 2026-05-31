package billing;

// OOP: Inheritance (extends Invoice), Polymorphism (overrides applyDiscount)
public class EmergencyInvoice extends Invoice {

    public EmergencyInvoice(String invoiceID, String patientID, String patientName) {
        super(invoiceID, patientID, patientName, "EMERGENCY");
    }

    @Override
    public double applyDiscount(double total) {
        // No discount on emergency invoices
        return total;
    }

    @Override
    public String getInvoiceTypeLabel() {
        return "EMERGENCY INVOICE";
    }
}