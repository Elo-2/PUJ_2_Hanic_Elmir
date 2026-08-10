package lifemanagement;

import org.bson.Document;

public class User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String theme; // zelena, plava, roza, narandžasta, tamna, cyberpunk
    private String color;
    private long createdAt;

    public User(String username, String password, String email, String firstName, String lastName) {
        this.userId = null;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.theme = "tamna"; // default tema
        this.color = "#FFFFFF"; // default boja
        this.createdAt = System.currentTimeMillis();
    }

    public User(String userId, String username, String password, String email,
                String firstName, String lastName, String theme, String color) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.theme = theme;
        this.color = color;
        this.createdAt = System.currentTimeMillis();
    }

    public Document toDocument() {
        return new Document()
                .append("username", username)
                .append("password", password)
                .append("email", email)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("theme", theme)
                .append("color", color)
                .append("createdAt", createdAt);
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public long getCreatedAt() { return createdAt; }
}