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

public class SleepManager {

    private final MongoCollection<Document> collection;

    public SleepManager() {
        MongoDatabase db = lifemanagement.MongoDBConnection.getDatabase();
        collection = db.getCollection("sleep");
    }

    // ADD
    public void addSleepRecord(Sleep sleep) {
        collection.insertOne(sleep.toDocument());
    }

    // GET ALL BY USER
    public ArrayList<lifemanagement.Sleep> getSleepByUser(String userId) {
        ArrayList<lifemanagement.Sleep> list = new ArrayList<>();

        MongoCursor<Document> cursor = collection.find(Filters.eq("userId", userId)).iterator();

        while (cursor.hasNext()) {
            Document d = cursor.next();
            lifemanagement.Sleep s = new lifemanagement.Sleep(
                    d.getObjectId("_id").toHexString(),
                    d.getString("userId"),
                    LocalDate.parse(d.getString("date")),
                    d.getDouble("hours"),
                    d.getString("quality"),
                    d.getString("notes")
            );
            list.add(s);
        }

        return list;
    }

    // GET BY DATE
    public lifemanagement.Sleep getSleepByDate(String userId, LocalDate date) {
        try {
            Document d = collection.find(
                    Filters.and(
                            Filters.eq("userId", userId),
                            Filters.eq("date", date.toString())
                    )
            ).first();

            if (d != null) {
                return new lifemanagement.Sleep(
                        d.getObjectId("_id").toHexString(),
                        d.getString("userId"),
                        LocalDate.parse(d.getString("date")),
                        d.getDouble("hours"),
                        d.getString("quality"),
                        d.getString("notes")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public void updateSleepRecord(String sleepId, lifemanagement.Sleep sleep) {
        collection.updateOne(
                Filters.eq("_id", new ObjectId(sleepId)),
                Updates.combine(
                        Updates.set("hours", sleep.getHours()),
                        Updates.set("quality", sleep.getQuality()),
                        Updates.set("notes", sleep.getNotes())
                )
        );
    }

    // DELETE
    public void deleteSleepRecord(String sleepId) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(sleepId)));
    }

    // ANALYTICS - Prosječno spavanje po noći
    public double getAverageSleepHours(String userId) {
        ArrayList<lifemanagement.Sleep> records = getSleepByUser(userId);
        if (records.isEmpty()) return 0;

        double total = 0;
        for (lifemanagement.Sleep s : records) {
            total += s.getHours();
        }
        return total / records.size();
    }

    // ANALYTICS - Spavanje ostatak sjedmice (zadnjih 7 dana)
    public double getLastWeekAverageSleep(String userId) {
        ArrayList<lifemanagement.Sleep> records = getSleepByUser(userId);
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);

        ArrayList<lifemanagement.Sleep> lastWeek = new ArrayList<>();
        for (lifemanagement.Sleep s : records) {
            if (s.getDate().isAfter(sevenDaysAgo) && s.getDate().isBefore(LocalDate.now().plusDays(1))) {
                lastWeek.add(s);
            }
        }

        if (lastWeek.isEmpty()) return 0;

        double total = 0;
        for (lifemanagement.Sleep s : lastWeek) {
            total += s.getHours();
        }
        return total / lastWeek.size();
    }

    // ANALYTICS - Kvaliteta spavanja
    public String getMostCommonQuality(String userId) {
        ArrayList<lifemanagement.Sleep> records = getSleepByUser(userId);
        if (records.isEmpty()) return "Nema podataka";

        int excellent = 0, good = 0, average = 0, poor = 0;
        for (lifemanagement.Sleep s : records) {
            switch (s.getQuality()) {
                case "Odličan": excellent++; break;
                case "Dobar": good++; break;
                case "Prosječan": average++; break;
                case "Loš": poor++; break;
            }
        }

        int max = Math.max(Math.max(excellent, good), Math.max(average, poor));
        if (max == excellent) return "Odličan";
        if (max == good) return "Dobar";
        if (max == average) return "Prosječan";
        return "Loš";
    }

    // ANALYTICS - Broj registrovanih noći
    public int getTotalSleepRecords(String userId) {
        return (int) collection.countDocuments(Filters.eq("userId", userId));
    }

    // ANALYTICS - Noći sa dobrim spavanjem
    public int getGoodSleepNights(String userId) {
        ArrayList<lifemanagement.Sleep> records = getSleepByUser(userId);
        int count = 0;
        for (lifemanagement.Sleep s : records) {
            if (s.getHours() >= 7 && (s.getQuality().equals("Odličan") || s.getQuality().equals("Dobar"))) {
                count++;
            }
        }
        return count;
    }
}