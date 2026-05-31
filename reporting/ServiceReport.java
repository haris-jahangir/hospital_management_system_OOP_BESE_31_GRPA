package reporting;

import Services.Service;
import Services.ServiceRecord;
import java.util.Map;


public class ServiceReport implements Reportable {

    private ServiceRecord serviceRecord;

    public ServiceReport(ServiceRecord serviceRecord) {
        this.serviceRecord = serviceRecord;
    }

    @Override
    public String getReportTitle() {
        return "SERVICE REPORT - " + serviceRecord.getPatientName();
    }

    @Override
    public void generateReport() {
        System.out.println("============================================");
        System.out.println("   " + getReportTitle());
        System.out.println("============================================");
        System.out.println("Patient ID       : " + serviceRecord.getPatientId());
        System.out.println("Total Services   : " + serviceRecord.getTotalServiceCount());
        System.out.println("Completed        : " + serviceRecord.getCompletedCount());

        System.out.println("--------------------------------------------");
        System.out.println("Service Breakdown by Type:");
        Map<String, Integer> counts = serviceRecord.getServiceCountByType();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.println("  " + entry.getKey() + " : " + entry.getValue() + " service(s)");
        }

        System.out.println("--------------------------------------------");
        System.out.println("Cost Breakdown (Completed Services Only):");
        Map<String, Double> costs = serviceRecord.getCostBreakdown();
        for (Map.Entry<String, Double> entry : costs.entrySet()) {
            System.out.println("  " + entry.getKey() + " : Rs. " + entry.getValue());
        }

        System.out.println("--------------------------------------------");
        System.out.println("All Services:");
        for (Service s : serviceRecord.getAllServices()) {
            System.out.println("  [" + s.getStatus() + "] " +
                               s.getServiceType() + " (ID: " + s.getServiceID() + ")" +
                               " - Rs. " + s.getCost());
        }

        System.out.println("--------------------------------------------");
        System.out.println("TOTAL BILLABLE : Rs. " + serviceRecord.calculateTotalCost());
        System.out.println("============================================");
    }
}