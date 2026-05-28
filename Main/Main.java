package Main;

import Actors.Patient;
import Actors.Doctor;
import Actors.Admin;
import Managers.ActorsManager;
import Appointments.AppointmentManager;

public class Main {
    public static void main(String[] args) {

        
        ActorsManager actorsManager = new ActorsManager();

        Patient p1 = new Patient("Ali Khan",   "P001", "ali@email.com",    "REC001", "B+");
        Patient p2 = new Patient("Sara Malik", "P002", "sara@email.com",   "REC002", "O+");
        Doctor  d1 = new Doctor("Dr. Ahmed",   "D001", "ahmed@email.com",  "Cardiology");
        Doctor  d2 = new Doctor("Dr. Ayesha",  "D002", "ayesha@email.com", "Neurology");

        actorsManager.addUser(p1);
        actorsManager.addUser(p2);
        actorsManager.addUser(d1);
        actorsManager.addUser(d2);

       
        AppointmentManager apptManager = new AppointmentManager(actorsManager);

        apptManager.scheduleAppointment("APT001", "P001", "D001", "2025-06-10", "10:00 AM");
        apptManager.scheduleAppointment("APT002", "P002", "D002", "2025-06-11", "02:00 PM");
        apptManager.scheduleEmergency  ("APT003", "P001", "D002", "2025-06-10", "IMMEDIATE");
        apptManager.rescheduleAppointment("APT001", "2025-06-15", "11:00 AM");
        apptManager.cancelAppointment("APT002");
        apptManager.completeAppointment("APT003");
        apptManager.displayAllAppointments();
    }
}