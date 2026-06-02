package Appointments;

import Actors.Doctor;
import Actors.User;
import Managers.ActorsManager;
import java.util.ArrayList;

public class AppointmentManager {

    private ArrayList<Appointment> appointmentList;
    private ActorsManager actorsManager;

    public AppointmentManager(ActorsManager actorsManager) {
        this.appointmentList = new ArrayList<Appointment>();
        this.actorsManager   = actorsManager;
    }

    // ── Schedule a normal appointment ─────────────────────────────────────────
    public boolean scheduleAppointment(String appointmentID, String patientID,
                                       String doctorID, String date, String timeSlot) {

        // Check patient exists
        User patient = actorsManager.findUserByID(patientID);
        if (patient == null) {
            System.out.println("[ERROR] Patient ID not found: " + patientID);
            return false;
        }

        // Check patient role
        if (!patient.getRole().equalsIgnoreCase("PATIENT")) {
            System.out.println("[ERROR] ID does not belong to a patient: " + patientID);
            return false;
        }

        // Check doctor exists
        User doctorUser = actorsManager.findUserByID(doctorID);
        if (doctorUser == null) {
            System.out.println("[ERROR] Doctor ID not found: " + doctorID);
            return false;
        }

        // Check doctor role
        if (!doctorUser.getRole().equalsIgnoreCase("DOCTOR")) {
            System.out.println("[ERROR] ID does not belong to a doctor: " + doctorID);
            return false;
        }

        // Check doctor availability
        Doctor doc = (Doctor) doctorUser;
        if (!doc.getIsAvailable()) {
            System.out.println("[ERROR] Doctor is not available: " + doctorID);
            return false;
        }

        // Check duplicate appointment ID
        if (findAppointmentByID(appointmentID) != null) {
            System.out.println("[ERROR] Appointment ID already exists: " + appointmentID);
            return false;
        }

        Appointment appt = new Appointment(appointmentID, patientID, doctorID,
                                           date, timeSlot, "NORMAL");
        appointmentList.add(appt);
        System.out.println("[SUCCESS] Appointment scheduled successfully: " + appointmentID);
        return true;
    }


   // ── Find appointment by ID ────────────────────────────────────────────────
    public Appointment findAppointmentByID(String appointmentID) {
        if (appointmentID == null) return null;

        for (int i = 0; i < appointmentList.size(); i++) {
            if (appointmentList.get(i).getAppointmentID().equalsIgnoreCase(appointmentID)) {
                return appointmentList.get(i);
            }
        }
        return null;
    }


    // ── Schedule an EMERGENCY appointment (bypasses availability check) ────────
    public boolean scheduleEmergency(String appointmentID, String patientID,
                                     String doctorID, String date, String timeSlot) {

        // Check patient exists
        User patient = actorsManager.findUserByID(patientID);
        if (patient == null) {
            System.out.println("[ERROR] Patient ID not found: " + patientID);
            return false;
        }

        // Check doctor exists
        User doctorUser = actorsManager.findUserByID(doctorID);
        if (doctorUser == null) {
            System.out.println("[ERROR] Doctor ID not found: " + doctorID);
            return false;
        }
        
        // Check patient role
        if (!patient.getRole().equalsIgnoreCase("PATIENT")) {
            System.out.println("[ERROR] ID does not belong to a patient: " + patientID);
            return false;
        }

        // Check doctor role
        if (!doctorUser.getRole().equalsIgnoreCase("DOCTOR")) {
            System.out.println("[ERROR] ID does not belong to a doctor: " + doctorID);
            return false;
        }

        // Check duplicate appointment ID
        if (findAppointmentByID(appointmentID) != null) {
            System.out.println("[ERROR] Appointment ID already exists: " + appointmentID);
            return false;
        }

        Appointment appt = new Appointment(appointmentID, patientID, doctorID,
                                           date, timeSlot, "EMERGENCY");
        appt.setNotes("EMERGENCY CASE - Bypassed standard scheduling workflow.");
        appointmentList.add(appt);

        System.out.println("[EMERGENCY] Emergency appointment booked immediately: " + appointmentID);
        return true;
    }

    // ── Cancel an appointment ─────────────────────────────────────────────────
    public boolean cancelAppointment(String appointmentID) {
        Appointment appt = findAppointmentByID(appointmentID);

        if (appt == null) {
            System.out.println("[ERROR] Appointment not found: " + appointmentID);
            return false;
        }

        if (appt.getStatus().equalsIgnoreCase("CANCELLED")) {
            System.out.println("[ERROR] Appointment is already cancelled: " + appointmentID);
            return false;
        }

        if (appt.getStatus().equalsIgnoreCase("COMPLETED")) {
            System.out.println("[ERROR] Cannot cancel a completed appointment: " + appointmentID);
            return false;
        }

        appt.setStatus("CANCELLED");
        System.out.println("[SUCCESS] Appointment cancelled: " + appointmentID);
        return true;
    }

    // ── Reschedule an appointment ─────────────────────────────────────────────
    public boolean rescheduleAppointment(String appointmentID, String newDate, String newTimeSlot) {
        Appointment appt = findAppointmentByID(appointmentID);

        if (appt == null) {
            System.out.println("[ERROR] Appointment not found: " + appointmentID);
            return false;
        }

        if (appt.getStatus().equalsIgnoreCase("CANCELLED")) {
            System.out.println("[ERROR] Cannot reschedule a cancelled appointment: " + appointmentID);
            return false;
        }

        if (appt.getStatus().equalsIgnoreCase("COMPLETED")) {
            System.out.println("[ERROR] Cannot reschedule a completed appointment: " + appointmentID);
            return false;
        }

        appt.setDate(newDate);
        appt.setTimeSlot(newTimeSlot);
        appt.setStatus("RESCHEDULED");
        System.out.println("[SUCCESS] Appointment rescheduled: " + appointmentID
                           + " -> New Date: " + newDate + " | New Slot: " + newTimeSlot);
        return true;
    }

    // ── Mark appointment as completed ─────────────────────────────────────────
    public boolean completeAppointment(String appointmentID) {
        Appointment appt = findAppointmentByID(appointmentID);

        if (appt == null) {
            System.out.println("[ERROR] Appointment not found: " + appointmentID);
            return false;
        }

        if (appt.getStatus().equalsIgnoreCase("CANCELLED")) {
            System.out.println("[ERROR] Cannot complete a cancelled appointment: " + appointmentID);
            return false;
        }

        if (appt.getStatus().equalsIgnoreCase("COMPLETED")) {
            System.out.println("[ERROR] Appointment is already completed: " + appointmentID);
            return false;
        }

        appt.setStatus("COMPLETED");
        System.out.println("[SUCCESS] Appointment marked as completed: " + appointmentID);
        return true;
    }


    // ── Get all appointments for a specific patient ───────────────────────────
    public ArrayList<Appointment> getAppointmentsByPatient(String patientID) {
        ArrayList<Appointment> result = new ArrayList<Appointment>();

        for (int i = 0; i < appointmentList.size(); i++) {
            if (appointmentList.get(i).getPatientID().equalsIgnoreCase(patientID)) {
                result.add(appointmentList.get(i));
            }
        }
        return result;
    }

    // ── Get all appointments for a specific doctor ────────────────────────────
    public ArrayList<Appointment> getAppointmentsByDoctor(String doctorID) {
        ArrayList<Appointment> result = new ArrayList<Appointment>();

        for (int i = 0; i < appointmentList.size(); i++) {
            if (appointmentList.get(i).getDoctorID().equalsIgnoreCase(doctorID)) {
                result.add(appointmentList.get(i));
            }
        }
        return result;
    }

    // ── Display all appointments ──────────────────────────────────────────────
    public void displayAllAppointments() {
        if (appointmentList.isEmpty()) {
            System.out.println("[INFO] No appointments in the system.");
            return;
        }

        System.out.println("========== ALL APPOINTMENTS ==========");
        for (int i = 0; i < appointmentList.size(); i++) {
            appointmentList.get(i).displayAppointment();
        }
    }

    // ── Display appointments for a specific patient ───────────────────────────
    public void displayPatientAppointments(String patientID) {
        ArrayList<Appointment> list = getAppointmentsByPatient(patientID);

        if (list.isEmpty()) {
            System.out.println("[INFO] No appointments found for Patient: " + patientID);
            return;
        }

        System.out.println("===== APPOINTMENTS FOR PATIENT: " + patientID + " =====");
        for (int i = 0; i < list.size(); i++) {
            list.get(i).displayAppointment();
        }
    }

    // ── Display appointments for a specific doctor ────────────────────────────
    public void displayDoctorAppointments(String doctorID) {
        ArrayList<Appointment> list = getAppointmentsByDoctor(doctorID);

        if (list.isEmpty()) {
            System.out.println("[INFO] No appointments found for Doctor: " + doctorID);
            return;
        }

        System.out.println("===== APPOINTMENTS FOR DOCTOR: " + doctorID + " =====");
        for (int i = 0; i < list.size(); i++) {
            list.get(i).displayAppointment();
        }
    }
}