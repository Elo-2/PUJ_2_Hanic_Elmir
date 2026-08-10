package lifemanagement;

import org.bson.Document;

import java.time.LocalDate;

public class Sleep {
    private String sleepId;
    private String userId;
    private LocalDate date;
    private double hours; // Sati spavanja
    private String quality; // Odličan, Dobar, Prosječan, Loš
    private String notes;

    public Sleep(String userId, LocalDate date, double hours, String quality, String notes) {
        this.sleepId = null;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
        this.quality = quality;
        this.notes = notes;
    }

    public Sleep(String sleepId, String userId, LocalDate date, double hours, String quality, String notes) {
        this.sleepId = sleepId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
        this.quality = quality;
        this.notes = notes;
    }

    public Document toDocument() {
        return new Document()
                .append("userId", userId)
                .append("date", date.toString())
                .append("hours", hours)
                .append("quality", quality)
                .append("notes", notes);
    }

    // Getters and Setters
    public String getSleepId() { return sleepId; }
    public void setSleepId(String sleepId) { this.sleepId = sleepId; }

    public String getUserId() { return userId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public double getHours() { return hours; }
    public void setHours(double hours) { this.hours = hours; }

    public String getQuality() { return quality; }
    public void setQuality(String quality) { this.quality = quality; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}