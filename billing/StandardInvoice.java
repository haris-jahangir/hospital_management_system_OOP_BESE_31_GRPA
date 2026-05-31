package billing;

public class StandardInvoice extends Invoice {

    public StandardInvoice(String invoiceID, String patientID, String patientName) {
        super(invoiceID, patientID, patientName, "STANDARD");
    }

    @Override
    public double applyDiscount(double total) {
        // 10% discount if bill is more than Rs. 500
        if (total > 500) {
            return total - (total * 0.10);
        }
        return total;
    }

    @Override
    public String getInvoiceTypeLabel() {
        return "STANDARD INVOICE";
    }
}