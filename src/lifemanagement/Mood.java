package lifemanagement;

import org.bson.Document;

import java.time.LocalDate;

public class Mood {

    private String moodId;
    private String userId;
    private LocalDate date;
    private String mood;
    private String note;

    public Mood(String userId, LocalDate date,
                String mood, String note) {

        this.moodId = null;
        this.userId = userId;
        this.date = date;
        this.mood = mood;
        this.note = note;
    }

    public Mood(String moodId, String userId,
                LocalDate date, String mood, String note) {

        this.moodId = moodId;
        this.userId = userId;
        this.date = date;
        this.mood = mood;
        this.note = note;
    }

    public Document toDocument() {

        return new Document()
                .append("userId", userId)
                .append("date", date.toString())
                .append("mood", mood)
                .append("note", note);
    }

    public String getMoodId() {
        return moodId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMood() {
        return mood;
    }

    public String getNote() {
        return note;
    }
}