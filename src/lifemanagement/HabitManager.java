package lifemanagement;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;

public class HabitManager {

    private final MongoCollection<Document> collection;

    public HabitManager() {
        MongoDatabase db = lifemanagement.MongoDBConnection.getDatabase();
        collection = db.getCollection("habits");
    }

    // ADD
    public void addHabit(Habit habit) {
        collection.insertOne(habit.toDocument());
    }

    // GET ALL BY USER
    public ArrayList<lifemanagement.Habit> getHabitsByUser(String userId) {
        ArrayList<lifemanagement.Habit> list = new ArrayList<>();

        MongoCursor<Document> cursor = collection.find(Filters.eq("userId", userId)).iterator();

        while (cursor.hasNext()) {
            Document d = cursor.next();
            lifemanagement.Habit h = new lifemanagement.Habit(
                    d.getObjectId("_id").toHexString(),
                    d.getString("userId"),
                    d.getString("name"),
                    d.getString("description"),
                    d.getString("category"),
                    LocalDate.parse(d.getString("createdDate")),
                    d.getInteger("consecutiveDays"),
                    d.getInteger("totalDays"),
                    d.getBoolean("completedToday")
            );
            list.add(h);
        }

        return list;
    }

    // MARK AS COMPLETED
    public void markHabitAsCompleted(String habitId) {
        lifemanagement.Habit habit = getHabitById(habitId);
        if (habit != null) {
            int newTotal = habit.getTotalDays() + 1;
            int newConsecutive = habit.getConsecutiveDays() + 1;

            collection.updateOne(
                    Filters.eq("_id", new ObjectId(habitId)),
                    Updates.combine(
                            Updates.set("totalDays", newTotal),
                            Updates.set("consecutiveDays", newConsecutive),
                            Updates.set("completedToday", true)
                    )
            );
        }
    }

    // RESET CONSECUTIVE DAYS
    public void resetConsecutiveDays(String habitId) {
        collection.updateOne(
                Filters.eq("_id", new ObjectId(habitId)),
                Updates.set("consecutiveDays", 0)
        );
    }

    // UPDATE
    public void updateHabit(String habitId, lifemanagement.Habit habit) {
        collection.updateOne(
                Filters.eq("_id", new ObjectId(habitId)),
                Updates.combine(
                        Updates.set("name", habit.getName()),
                        Updates.set("description", habit.getDescription()),
                        Updates.set("category", habit.getCategory())
                )
        );
    }

    // DELETE
    public void deleteHabit(String habitId) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(habitId)));
    }

    // GET BY ID
    public lifemanagement.Habit getHabitById(String habitId) {
        try {
            Document d = collection.find(Filters.eq("_id", new ObjectId(habitId))).first();
            if (d != null) {
                return new lifemanagement.Habit(
                        d.getObjectId("_id").toHexString(),
                        d.getString("userId"),
                        d.getString("name"),
                        d.getString("description"),
                        d.getString("category"),
                        LocalDate.parse(d.getString("createdDate")),
                        d.getInteger("consecutiveDays"),
                        d.getInteger("totalDays"),
                        d.getBoolean("completedToday")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ANALYTICS - Ukupan broj navika
    public int getTotalHabits(String userId) {
        return (int) collection.countDocuments(Filters.eq("userId", userId));
    }

    // ANALYTICS - Prosječni procenat završetka
    public double getAverageCompletionPercentage(String userId) {
        ArrayList<lifemanagement.Habit> habits = getHabitsByUser(userId);
        if (habits.isEmpty()) return 0;

        double total = 0;
        for (lifemanagement.Habit h : habits) {
            total += h.getCompletionPercentage();
        }
        return total / habits.size();
    }

    // ANALYTICS - Najbolja naviка
    public lifemanagement.Habit getBestHabit(String userId) {
        ArrayList<lifemanagement.Habit> habits = getHabitsByUser(userId);
        if (habits.isEmpty()) return null;

        lifemanagement.Habit best = habits.get(0);
        for (lifemanagement.Habit h : habits) {
            if (h.getConsecutiveDays() > best.getConsecutiveDays()) {
                best = h;
            }
        }
        return best;
    }

    // ANALYTICS - Navike dovršene hoje
    public int getCompletedTodayCount(String userId) {
        int count = 0;
        for (lifemanagement.Habit h : getHabitsByUser(userId)) {
            if (h.isCompletedToday()) {
                count++;
            }
        }
        return count;
    }
}