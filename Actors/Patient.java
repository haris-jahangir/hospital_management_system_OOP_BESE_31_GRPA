package Actors;

import java.util.ArrayList;

public class Patient extends User {

    private String patientRecordID;
    private String bloodGroup;
    private ArrayList<String> medicalHistory;

    public Patient(String id, String name, String email, String patientRecordID, String bloodGroup) 
    {
        super(id, name, email, "PATIENT");
        this.patientRecordID = patientRecordID;
        this.bloodGroup = bloodGroup;
        this.medicalHistory = new ArrayList<>();
    }

    public String getPatientRecordID() { return patientRecordID; }
    public String getBloodGroup() { return bloodGroup; }
    public ArrayList<String> getMedicalHistory() { return medicalHistory; }

    public void setPatientRecordID(String patientRecordID) { this.patientRecordID = patientRecordID; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    
    public void addHistoryEntry(String entry) 
    {
        if (entry != null && !entry.trim().isEmpty()) 
        {
            this.medicalHistory.add(entry);
        }
    }

    

    @Override
    public void displayProfile() 
    {
        System.out.println("___ PATIENT PROFILE ___");
        System.out.println("User ID: " + getUserID());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Patient Record ID: " + patientRecordID);
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Medical History Log:");

        if (medicalHistory.isEmpty())     
        {
            System.out.println(" - No history recorded yet.");
        } 
        else 
        {
            for (String record : medicalHistory) 
            {
                System.out.println(" - " + record);
            }
        }
        System.out.println("_______________________");
    }
}
