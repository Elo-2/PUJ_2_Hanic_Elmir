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

public class TaskManager {

    private final MongoCollection<Document> collection;

    public TaskManager() {
        MongoDatabase db = lifemanagement.MongoDBConnection.getDatabase();
        collection = db.getCollection("tasks");
    }

    // ADD
    public void addTask(Task task) {
        collection.insertOne(task.toDocument());
    }

    // GET ALL BY USER
    public ArrayList<lifemanagement.Task> getTasksByUser(String userId) {
        ArrayList<lifemanagement.Task> list = new ArrayList<>();

        MongoCursor<Document> cursor = collection.find(Filters.eq("userId", userId)).iterator();

        while (cursor.hasNext()) {
            Document d = cursor.next();
            lifemanagement.Task t = new lifemanagement.Task(
                    d.getObjectId("_id").toHexString(),
                    d.getString("userId"),
                    d.getString("title"),
                    d.getString("description"),
                    d.getString("priority"),
                    d.getString("status"),
                    LocalDate.parse(d.getString("dueDate")),
                    LocalDate.parse(d.getString("createdDate")),
                    d.getString("category")
            );
            list.add(t);
        }

        return list;
    }

    // GET BY STATUS
    public ArrayList<lifemanagement.Task> getTasksByStatus(String userId, String status) {
        ArrayList<lifemanagement.Task> allTasks = getTasksByUser(userId);
        ArrayList<lifemanagement.Task> filtered = new ArrayList<>();

        for (lifemanagement.Task t : allTasks) {
            if (t.getStatus().equals(status)) {
                filtered.add(t);
            }
        }

        return filtered;
    }

    // UPDATE
    public void updateTask(String taskId, lifemanagement.Task task) {
        collection.updateOne(
                Filters.eq("_id", new ObjectId(taskId)),
                Updates.combine(
                        Updates.set("title", task.getTitle()),
                        Updates.set("description", task.getDescription()),
                        Updates.set("priority", task.getPriority()),
                        Updates.set("status", task.getStatus()),
                        Updates.set("dueDate", task.getDueDate().toString()),
                        Updates.set("category", task.getCategory())
                )
        );
    }

    // UPDATE STATUS
    public void updateTaskStatus(String taskId, String status) {
        collection.updateOne(
                Filters.eq("_id", new ObjectId(taskId)),
                Updates.set("status", status)
        );
    }

    // DELETE
    public void deleteTask(String taskId) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(taskId)));
    }

    // ANALYTICS - Ukupan broj zadataka
    public int getTotalTasks(String userId) {
        return (int) collection.countDocuments(Filters.eq("userId", userId));
    }

    // ANALYTICS - Završeni zadaci
    public int getCompletedTasks(String userId) {
        return (int) collection.countDocuments(
                Filters.and(
                        Filters.eq("userId", userId),
                        Filters.eq("status", "Završeno")
                )
        );
    }

    // ANALYTICS - Procenat završenosti
    public double getCompletionPercentage(String userId) {
        int total = getTotalTasks(userId);
        if (total == 0) return 0;
        int completed = getCompletedTasks(userId);
        return (double) completed / total * 100;
    }

    // ANALYTICS - Zadaci u toku
    public int getInProgressTasks(String userId) {
        return (int) collection.countDocuments(
                Filters.and(
                        Filters.eq("userId", userId),
                        Filters.eq("status", "U toku")
                )
        );
    }

    // ANALYTICS - Prekoračeni zadaci
    public int getOverdueTasks(String userId) {
        ArrayList<lifemanagement.Task> tasks = getTasksByUser(userId);
        int count = 0;
        for (lifemanagement.Task t : tasks) {
            if (t.isOverdue()) {
                count++;
            }
        }
        return count;
    }

    // ANALYTICS - Prioritet raspodela
    public int getHighPriorityTasks(String userId) {
        return (int) collection.countDocuments(
                Filters.and(
                        Filters.eq("userId", userId),
                        Filters.eq("priority", "Visoka")
                )
        );
    }

    public int getMediumPriorityTasks(String userId) {
        return (int) collection.countDocuments(
                Filters.and(
                        Filters.eq("userId", userId),
                        Filters.eq("priority", "Srednja")
                )
        );
    }

    public int getLowPriorityTasks(String userId) {
        return (int) collection.countDocuments(
                Filters.and(
                        Filters.eq("userId", userId),
                        Filters.eq("priority", "Niska")
                )
        );
    }

    // ANALYTICS - Zadaci po kategoriji
    public ArrayList<String> getTasksByCategory(String userId) {
        ArrayList<lifemanagement.Task> tasks = getTasksByUser(userId);
        ArrayList<String> categories = new ArrayList<>();

        for (lifemanagement.Task t : tasks) {
            if (!categories.contains(t.getCategory())) {
                categories.add(t.getCategory());
            }
        }

        return categories;
    }
}