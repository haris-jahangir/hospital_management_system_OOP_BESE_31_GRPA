package Services;

import Actors.Patient;
import Actors.Doctor;

public class ConsultationService extends Service {
    private String symptoms;
    private String diagnosis;
    private String prescription;
    private boolean isFollowUp;

    public ConsultationService(String serviceID, Patient patient, Doctor doctor, boolean isFollowUp) {
        super(serviceID, patient, doctor, "CONSULTATION");
        this.isFollowUp = isFollowUp;
        this.cost = isFollowUp ? 60.0 : 100.0; // Follow-up costs 60% of base
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    @Override
    public void execute() {
        if (!status.equals("PENDING") && !status.equals("IN_PROGRESS")) {
            System.out.println("[ERROR] Cannot execute consultation in status: " + status);
            return;
        }

        start();
        System.out
                .println("[CONSULTATION] Doctor " + doctor.getName() + " consulting with patient " + patient.getName());

        if (symptoms != null && !symptoms.isEmpty()) {
            System.out.println("  Symptoms: " + symptoms);
        }
        if (diagnosis != null && !diagnosis.isEmpty()) {
            System.out.println("  Diagnosis: " + diagnosis);
            patient.addHistoryEntry("Diagnosis: " + diagnosis);
        }
        if (prescription != null && !prescription.isEmpty()) {
            System.out.println("  Prescription: " + prescription);
            patient.addHistoryEntry("Prescribed: " + prescription);
        }

        complete();
    }

    @Override
    public void displayDetails() {
        System.out.println("___ CONSULTATION DETAILS ___");
        System.out.println("Service ID: " + serviceID);
        System.out.println("Patient: " + patient.getName() + " (" + patient.getID() + ")");
        System.out.println("Doctor: " + doctor.getName() + " (" + doctor.getID() + ")");
        System.out.println("Type: " + (isFollowUp ? "Follow-up" : "Initial"));
        System.out.println("Symptoms: " + (symptoms != null ? symptoms : "Not recorded"));
        System.out.println("Diagnosis: " + (diagnosis != null ? diagnosis : "Pending"));
        System.out.println("Prescription: " + (prescription != null ? prescription : "None"));
        System.out.println("Status: " + status);
        System.out.println("Cost: $" + cost);
        System.out.println("_____________________________");
    }
}