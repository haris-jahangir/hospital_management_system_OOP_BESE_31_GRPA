package Services;

import Actors.Patient;
import Actors.Doctor;
import java.util.HashMap;
import java.util.Map;

public class TreatmentService extends Service {
    private String treatmentType;
    private int totalSessions;
    private int completedSessions;
    private String outcome;

    // Cost mapping for different treatment types
    private static final Map<String, Double> TREATMENT_COSTS = new HashMap<>();
    static {
        TREATMENT_COSTS.put("Medication", 30.0);
        TREATMENT_COSTS.put("Surgery", 2500.0);
        TREATMENT_COSTS.put("Physiotherapy", 120.0);
        TREATMENT_COSTS.put("Vaccination", 75.0);
        TREATMENT_COSTS.put("Wound Care", 90.0);
    }

    public TreatmentService(String serviceID, Patient patient, Doctor doctor, String treatmentType) {
        super(serviceID, patient, doctor, "TREATMENT");
        this.treatmentType = treatmentType;
        this.cost = TREATMENT_COSTS.getOrDefault(treatmentType, 100.0);
        this.totalSessions = 1;
        this.completedSessions = 0;
    }

    public TreatmentService(String serviceID, Patient patient, Doctor doctor,
            String treatmentType, int totalSessions) {
        this(serviceID, patient, doctor, treatmentType);
        this.totalSessions = totalSessions;
        this.cost = this.cost * totalSessions; // Multiply cost for multiple sessions
    }

    public void completeSession() {
        if (status.equals("IN_PROGRESS") && completedSessions < totalSessions) {
            completedSessions++;
            System.out.println("[TREATMENT] Session " + completedSessions + " of " + totalSessions + " completed");

            if (completedSessions == totalSessions) {
                complete();
            }
        }
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
        if (status.equals("COMPLETED")) {
            patient.addHistoryEntry(treatmentType + " completed. Outcome: " + outcome);
        }
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public int getCompletedSessions() {
        return completedSessions;
    }

    public int getTotalSessions() {
        return totalSessions;
    }

    @Override
    public void execute() {
        if (!status.equals("PENDING") && !status.equals("IN_PROGRESS")) {
            System.out.println("[ERROR] Cannot execute treatment in status: " + status);
            return;
        }

        start();
        System.out.println("[TREATMENT] " + treatmentType + " prescribed by Dr. " + doctor.getName() +
                " for patient " + patient.getName());
        System.out.println("  Total sessions: " + totalSessions);

        if (totalSessions == 1) {
            completeSession();
        }
    }

    @Override
    public void displayDetails() {
        System.out.println("___ TREATMENT DETAILS ___");
        System.out.println("Service ID: " + serviceID);
        System.out.println("Patient: " + patient.getName() + " (" + patient.getID() + ")");
        System.out.println("Doctor: " + doctor.getName() + " (" + doctor.getID() + ")");
        System.out.println("Treatment Type: " + treatmentType);
        System.out.println("Sessions: " + completedSessions + " / " + totalSessions);
        System.out.println("Outcome: " + (outcome != null ? outcome : "Pending"));
        System.out.println("Status: " + status);
        System.out.println("Cost: $" + cost);
        System.out.println("_________________________");
    }
}