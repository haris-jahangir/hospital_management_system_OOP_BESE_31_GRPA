package Actors;

public class Admin extends User {
    private String department;

    public Admin(String name, String id, String email, String department) {
        super(name, id, email, "ADMIN");
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public void displayProfile() {
        System.out.println("___ ADMINISTRATIVE PROFILE ___");
        System.out.println("User ID: " + getID());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Assigned Department: " + department);
        System.out.println("____________________________________");
    }
}