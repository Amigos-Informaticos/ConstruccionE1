package models;

public class ProjectResponsible {
    private String email;
    private String names;
    private String lastNames;
    
    public ProjectResponsible() {
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNames() {
        return names;
    }
    
    public void setNames(String names) {
        this.names = names;
    }
    
    public String getLastNames() {
        return lastNames;
    }
    
    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }
    
    public boolean isComplete() {
        return this.email != null &&
            this.names != null &&
            this.lastNames != null;
    }
}
