package lifemanagement;

import org.bson.Document;

import java.time.LocalDate;

public class Task {
    private String taskId;
    private String userId;
    private String title;
    private String description;
    private String priority; // Niska, Srednja, Visoka
    private String status; // Nepaćeno, U toku, Završeno
    private LocalDate dueDate;
    private LocalDate createdDate;
    private String category; // Posao, Osobno, Zdravlje, Učenje, itd.

    public Task(String userId, String title, String description, String priority, String category, LocalDate dueDate) {
        this.taskId = null;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = "Nepočeto";
        this.dueDate = dueDate;
        this.createdDate = LocalDate.now();
        this.category = category;
    }

    public Task(String taskId, String userId, String title, String description, String priority,
                String status, LocalDate dueDate, LocalDate createdDate, String category) {
        this.taskId = taskId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.createdDate = createdDate;
        this.category = category;
    }

    public Document toDocument() {
        return new Document()
                .append("userId", userId)
                .append("title", title)
                .append("description", description)
                .append("priority", priority)
                .append("status", status)
                .append("dueDate", dueDate.toString())
                .append("createdDate", createdDate.toString())
                .append("category", category);
    }

    // Getters and Setters
    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getCreatedDate() { return createdDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate) && !status.equals("Završeno");
    }
}