package Services;

import Actors.Patient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceRecord {
    private String patientId;
    private String patientName;
    private ArrayList<Service> serviceHistory;

    public ServiceRecord(Patient patient) {
        this.patientId = patient.getID();
        this.patientName = patient.getName();
        this.serviceHistory = new ArrayList<>();
    }

    public void addService(Service service) {
        if (service != null) {
            serviceHistory.add(service);
            System.out.println("[RECORD] Service " + service.getServiceID() + " added to patient record.");
        }
    }

    public ArrayList<Service> getAllServices() {
        return serviceHistory;
    }

    public ArrayList<Service> getCompletedServices() {
        ArrayList<Service> completed = new ArrayList<>();
        for (Service s : serviceHistory) {
            if (s.getStatus().equals("COMPLETED")) {
                completed.add(s);
            }
        }
        return completed;
    }

    public ArrayList<Service> getServicesByType(String type) {
        ArrayList<Service> result = new ArrayList<>();
        for (Service s : serviceHistory) {
            if (s.getServiceType().equalsIgnoreCase(type)) {
                result.add(s);
            }
        }
        return result;
    }

    public ArrayList<Service> getServicesByStatus(String status) {
        ArrayList<Service> result = new ArrayList<>();
        for (Service s : serviceHistory) {
            if (s.getStatus().equalsIgnoreCase(status)) {
                result.add(s);
            }
        }
        return result;
    }

    public double calculateTotalCost() {
        double total = 0;
        for (Service s : serviceHistory) {
            if (s.getStatus().equals("COMPLETED")) {
                total += s.getCost();
            }
        }
        return total;
    }

    public double calculateTotalCostAll() {
        double total = 0;
        for (Service s : serviceHistory) {
            total += s.getCost();
        }
        return total;
    }

    public Map<String, Double> getCostBreakdown() {
        Map<String, Double> breakdown = new HashMap<>();
        for (Service s : getCompletedServices()) {
            String type = s.getServiceType();
            breakdown.put(type, breakdown.getOrDefault(type, 0.0) + s.getCost());
        }
        return breakdown;
    }

    public Map<String, Integer> getServiceCountByType() {
        Map<String, Integer> counts = new HashMap<>();
        for (Service s : serviceHistory) {
            String type = s.getServiceType();
            counts.put(type, counts.getOrDefault(type, 0) + 1);
        }
        return counts;
    }

    public Service findServiceByID(String serviceID) {
        for (Service s : serviceHistory) {
            if (s.getServiceID().equalsIgnoreCase(serviceID)) {
                return s;
            }
        }
        return null;
    }

    public void displaySummary() {
        System.out.println("\n========== SERVICE RECORD FOR: " + patientName + " ==========");
        System.out.println("Patient ID: " + patientId);
        System.out.println("Total Services: " + serviceHistory.size());
        System.out.println("Completed Services: " + getCompletedServices().size());
        System.out.println("Pending/In Progress Services: " + (serviceHistory.size() - getCompletedServices().size()));

        System.out.println("\n--- Service Breakdown ---");
        if (serviceHistory.isEmpty()) {
            System.out.println("  No services recorded yet.");
        } else {
            for (Service s : serviceHistory) {
                String statusIcon = "";
                switch (s.getStatus()) {
                    case "COMPLETED":
                        statusIcon = "✓";
                        break;
                    case "IN_PROGRESS":
                        statusIcon = "▶";
                        break;
                    case "PENDING":
                        statusIcon = "⏳";
                        break;
                    case "CANCELLED":
                        statusIcon = "✗";
                        break;
                    default:
                        statusIcon = "?";
                }
                System.out.println("  [" + statusIcon + "] " + s.getServiceType() +
                        " (ID: " + s.getServiceID() + ") - $" + s.getCost() +
                        " | Status: " + s.getStatus());
            }
        }

        System.out.println("\n--- Cost Summary ---");
        Map<String, Double> breakdown = getCostBreakdown();
        if (breakdown.isEmpty()) {
            System.out.println("  No completed services to bill.");
        } else {
            for (Map.Entry<String, Double> entry : breakdown.entrySet()) {
                System.out.println("  " + entry.getKey() + ": $" + entry.getValue());
            }
            System.out.println("  ---------------------------------");
            System.out.println("  TOTAL BILLABLE: $" + calculateTotalCost());
        }
        System.out.println("============================================================\n");
    }

    public void displaySimple() {
        System.out.println("Patient: " + patientName + " (" + patientId + ")");
        System.out.println("Services: " + serviceHistory.size() + " total, " +
                getCompletedServices().size() + " completed");
        System.out.println("Total Cost: $" + calculateTotalCost());
    }

    // Getters
    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public int getTotalServiceCount() {
        return serviceHistory.size();
    }

    public int getCompletedCount() {
        return getCompletedServices().size();
    }
}