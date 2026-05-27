package Actors;

public abstract class User {
    
    private String name;
    private String id;
    private String email;   
    private String role;

    public User(String name, String id, String email, String role) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.role = role;
    }
    
    public String getName() { return name; }
    public String getID() { return id; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    public void setID(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }    
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }

    public abstract void displayProfile();
}
