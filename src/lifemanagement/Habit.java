package lifemanagement;

import org.bson.Document;

import java.time.LocalDate;

public class Habit {
    private String habitId;
    private String userId;
    private String name;
    private String description;
    private String category; // Vježbanje, Čitanje, Meditacija, Voda, Zdravo jelo, itd.
    private LocalDate createdDate;
    private int consecutiveDays;
    private int totalDays;
    private boolean completedToday;

    public Habit(String userId, String name, String description, String category) {
        this.habitId = null;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.createdDate = LocalDate.now();
        this.consecutiveDays = 0;
        this.totalDays = 0;
        this.completedToday = false;
    }

    public Habit(String habitId, String userId, String name, String description, String category,
                 LocalDate createdDate, int consecutiveDays, int totalDays, boolean completedToday) {
        this.habitId = habitId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.createdDate = createdDate;
        this.consecutiveDays = consecutiveDays;
        this.totalDays = totalDays;
        this.completedToday = completedToday;
    }

    public Document toDocument() {
        return new Document()
                .append("userId", userId)
                .append("name", name)
                .append("description", description)
                .append("category", category)
                .append("createdDate", createdDate.toString())
                .append("consecutiveDays", consecutiveDays)
                .append("totalDays", totalDays)
                .append("completedToday", completedToday);
    }

    // Getters and Setters
    public String getHabitId() { return habitId; }
    public void setHabitId(String habitId) { this.habitId = habitId; }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getCreatedDate() { return createdDate; }
    public int getConsecutiveDays() { return consecutiveDays; }
    public void setConsecutiveDays(int consecutiveDays) { this.consecutiveDays = consecutiveDays; }

    public int getTotalDays() { return totalDays; }
    public void setTotalDays(int totalDays) { this.totalDays = totalDays; }

    public boolean isCompletedToday() { return completedToday; }
    public void setCompletedToday(boolean completedToday) { this.completedToday = completedToday; }

    public double getCompletionPercentage() {
        if (totalDays == 0) return 0;
        return (double) consecutiveDays / totalDays * 100;
    }
}