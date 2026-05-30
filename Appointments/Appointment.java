package Appointments;

public class Appointment {

    private String appointmentID;
    private String patientID;
    private String doctorID;
    private String date;
    private String timeSlot;
    private String status;      // "SCHEDULED", "CANCELLED", "COMPLETED", "RESCHEDULED"
    private String priority;    // "NORMAL", "EMERGENCY"
    private String notes;

    public Appointment(String appointmentID, String patientID, String doctorID,
                       String date, String timeSlot, String priority) {
        this.appointmentID = appointmentID;
        this.patientID     = patientID;
        this.doctorID      = doctorID;
        this.date          = date;
        this.timeSlot      = timeSlot;
        this.priority      = priority;
        this.status        = "SCHEDULED";
        this.notes         = "";
    }

    // Getters
    public String getAppointmentID() { return appointmentID; }
    public String getPatientID()     { return patientID; }
    public String getDoctorID()      { return doctorID; }
    public String getDate()          { return date; }
    public String getTimeSlot()      { return timeSlot; }
    public String getStatus()        { return status; }
    public String getPriority()      { return priority; }
    public String getNotes()         { return notes; }

    // Setters
    public void setDate(String date)         { this.date = date; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public void setStatus(String status)     { this.status = status; }
    public void setPriority(String priority) { this.priority = priority; }
    public void setNotes(String notes)       { this.notes = notes; }

    public void displayAppointment() {
        System.out.println("___ APPOINTMENT DETAILS ___");
        System.out.println("Appointment ID : " + appointmentID);
        System.out.println("Patient ID     : " + patientID);
        System.out.println("Doctor ID      : " + doctorID);
        System.out.println("Date           : " + date);
        System.out.println("Time Slot      : " + timeSlot);
        System.out.println("Priority       : " + priority);
        System.out.println("Status         : " + status);
        if (!notes.isEmpty()) {
            System.out.println("Notes          : " + notes);
        }
        System.out.println("___________________________");
    }
}