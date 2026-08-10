package lifemanagement;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;

public class MoodManager {

    private final MongoCollection<Document> collection;

    public MoodManager() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        collection = db.getCollection("moods");
    }

    public void addMood(Mood mood) {
        collection.insertOne(mood.toDocument());
    }

    public void deleteMood(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    public ArrayList<Mood> getMoodsByUser(String userId) {

        ArrayList<Mood> list = new ArrayList<>();

        MongoCursor<Document> cursor =
                collection.find(Filters.eq("userId", userId)).iterator();

        while (cursor.hasNext()) {

            Document d = cursor.next();

            list.add(new Mood(
                    d.getObjectId("_id").toHexString(),
                    d.getString("userId"),
                    LocalDate.parse(d.getString("date")),
                    d.getString("mood"),
                    d.getString("note")
            ));
        }

        return list;
    }

    public String getAverageMood(String userId) {

        ArrayList<Mood> list = getMoodsByUser(userId);

        if (list.isEmpty())
            return "Nema podataka";

        int sum = 0;

        for (Mood m : list) {

            switch (m.getMood()) {

                case "😀 Odlično":
                    sum += 5;
                    break;

                case "😊 Dobro":
                    sum += 4;
                    break;

                case "😐 Normalno":
                    sum += 3;
                    break;

                case "😔 Loše":
                    sum += 2;
                    break;

                default:
                    sum += 1;
            }
        }

        double avg = (double) sum / list.size();

        if (avg >= 4.5) return "😀 Odlično";
        if (avg >= 3.5) return "😊 Dobro";
        if (avg >= 2.5) return "😐 Normalno";
        if (avg >= 1.5) return "😔 Loše";

        return "😡 Veoma loše";
    }
}