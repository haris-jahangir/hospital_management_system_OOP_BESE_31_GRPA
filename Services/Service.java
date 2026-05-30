package Services;

import Actors.Patient;
import Actors.Doctor;

public abstract class Service {
    protected String serviceID;
    protected String serviceType;
    protected Patient patient;
    protected Doctor doctor;
    protected String status; // "PENDING", "IN_PROGRESS", "COMPLETED", "CANCELLED"
    protected double cost;
    protected String notes;

    public Service(String serviceID, Patient patient, Doctor doctor, String serviceType) {
        this.serviceID = serviceID;
        this.patient = patient;
        this.doctor = doctor;
        this.serviceType = serviceType;
        this.status = "PENDING";
        this.cost = 0.0;
        this.notes = "";
    }

    public void start() {
        if (status.equals("PENDING")) {
            this.status = "IN_PROGRESS";
            System.out.println("[SERVICE] " + serviceID + " started.");
        }
    }

    public void complete() {
        if (status.equals("IN_PROGRESS") || status.equals("PENDING")) {
            this.status = "COMPLETED";
            System.out.println("[SERVICE] " + serviceID + " completed. Cost: $" + cost);
            // Add to patient's medical history
            patient.addHistoryEntry(serviceType + " completed on " + java.time.LocalDate.now());
        }
    }

    public void cancel() {
        if (!status.equals("COMPLETED")) {
            this.status = "CANCELLED";
            System.out.println("[SERVICE] " + serviceID + " cancelled.");
        }
    }

    // Getters
    public String getServiceID() {
        return serviceID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getStatus() {
        return status;
    }

    public double getCost() {
        return cost;
    }

    public String getNotes() {
        return notes;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public abstract void execute();

    public abstract void displayDetails();
}