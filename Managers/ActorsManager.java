package Managers;

import Actors.User;
import Actors.Patient;
import Actors.Doctor;
import Actors.Admin;
import Actors.ADS;
import java.util.ArrayList;

public class ActorsManager {
    

    private ArrayList<User> userDatabase;
    private ArrayList<ADS> adsDatabase;

 
    public ActorsManager() {
        this.userDatabase = new ArrayList<User>();
        this.adsDatabase = new ArrayList<ADS>();
    }

    
    public void addUser(User user) {
        if (user != null) {
            userDatabase.add(user);
        }
    }

    
    public void addADS(ADS service) {
        if (service != null) {
            adsDatabase.add(service);
        }
    }

    
    public User findUserByID(String id) {
        if (id == null) {
            return null;
        }
        
       
        for (int i = 0; i < userDatabase.size(); i++) {
            User currentUser = userDatabase.get(i);
            
           
            if (currentUser.getID().equalsIgnoreCase(id)) {
                return currentUser;
            }
        }
        return null; 
    }

    
    public ADS findADSByID(String id) {
        if (id == null) {
            return null;
        }
        
        for (int i = 0; i < adsDatabase.size(); i++) {
            ADS currentMachine = adsDatabase.get(i);
            
            if (currentMachine.getServiceID().equalsIgnoreCase(id)) {
                return currentMachine;
            }
        }
        return null;
    }

    
    public ArrayList<Doctor> getAvailableDoctorsBySpecialty(String specialty) {
        ArrayList<Doctor> matchingDoctors = new ArrayList<Doctor>();
        
        if (specialty == null) {
            return matchingDoctors;
        }

        for (int i = 0; i < userDatabase.size(); i++) {
            User currentUser = userDatabase.get(i);
            
    
            if (currentUser.getRole().equalsIgnoreCase("DOCTOR")) {
                
                
                Doctor doc = (Doctor) currentUser; 
                
                
                if (doc.getSpecialization().equalsIgnoreCase(specialty) && doc.getIsAvailable()) {
                    matchingDoctors.add(doc);
                }
            }
        }
        return matchingDoctors;
    }

    
    public boolean checkAccess(String requesterID, String requiredRole) {
        if (requesterID == null || requiredRole == null) {
            return false;
        }

       
        User user = findUserByID(requesterID);
        if (user != null) {
            
            if (user.getRole().equalsIgnoreCase(requiredRole)) {
                return true;
            } else {
                System.out.println("[ACCESS DENIED] Wrong role level.");
                return false;
            }
        }

       
        ADS service = findADSByID(requesterID);
        if (service != null) {
            if (service.getSystemRole().equalsIgnoreCase(requiredRole)) {
                return true; 
            } else {
                System.out.println("[ACCESS DENIED] Machine not permitted.");
                return false;
            }
        }

        System.out.println("[ACCESS DENIED] ID not found in system.");
        return false;
    }
}