package Actors;

public class Doctor extends User {
    private String specialization;
    private boolean isAvailable;

    public Doctor(String name, String id, String email, String specialization) {
        super(name, id, email, "DOCTOR");
        this.specialization = specialization;
        this.isAvailable = true; // Doctor is active and available by default
    }

    public String getSpecialization() { return specialization; }
    public boolean getIsAvailable() { return isAvailable; }

    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setIsAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

    @Override
    public void displayProfile() {
        System.out.println("___ DOCTOR PROFILE ___");
        System.out.println("User ID: " + getID());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Specialization: " + specialization);
        System.out.println("Availability Status: " + (isAvailable ? "Available" : "On Duty / Busy"));
        System.out.println("______________________");
    }
}