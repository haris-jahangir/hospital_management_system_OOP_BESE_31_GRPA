// Automated Diagnosis System

package Actors;

public class ADS {
    private String serviceID;
    private String serviceName;
    private String systemRole; // Kept as "AUTOMATED_SERVICE" for security authorization checks
    private boolean isOperational;

    public ADS(String serviceID, String serviceName) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.systemRole = "AUTOMATED_SERVICE";
        this.isOperational = true;
    }

    public String getServiceID() { return serviceID; }
    public String getServiceName() { return serviceName; }
    public String getSystemRole() { return systemRole; }
    public boolean getIsOperational() { return isOperational; }

    public void setServiceID(String serviceID) { this.serviceID = serviceID; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public void setIsOperational(boolean isOperational) { this.isOperational = isOperational; }

    public boolean isAuthorized(String requiredRole) {
        if (requiredRole == null) return false;
        return this.systemRole.equalsIgnoreCase(requiredRole);
    }

    public void displayServiceStatus() {
        System.out.println("___ AUTOMATED DIAGNOSTIC SERVICE STATUS ___");
        System.out.println("Service ID: " + serviceID);
        System.out.println("Service Name: " + serviceName);
        System.out.println("Operational Status: " + (isOperational ? "ONLINE" : "OFFLINE / MAINTENANCE"));
        System.out.println("___________________________________________");
    }
}