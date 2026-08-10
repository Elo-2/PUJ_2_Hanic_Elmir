package lifemanagement;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

public class UserManager {

    private final MongoCollection<Document> collection;

    public UserManager() {
        MongoDatabase db = lifemanagement.MongoDBConnection.getDatabase();
        collection = db.getCollection("users");
    }

    // REGISTER - Dodaj novog korisnika
    public boolean registerUser(lifemanagement.User user) {
        try {
            // Provjeri da li korisnik već postoji
            if (getUserByUsername(user.getUsername()) != null) {
                return false; // Korisnik postoji
            }
            collection.insertOne(user.toDocument());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // LOGIN - Provjeri kredencijale
    public lifemanagement.User loginUser(String username, String password) {
        try {
            Document doc = collection.find(
                    Filters.and(
                            Filters.eq("username", username),
                            Filters.eq("password", password)
                    )
            ).first();

            if (doc != null) {
                return new lifemanagement.User(
                        doc.getObjectId("_id").toHexString(),
                        doc.getString("username"),
                        doc.getString("password"),
                        doc.getString("email"),
                        doc.getString("firstName"),
                        doc.getString("lastName"),
                        doc.getString("theme"),
                        doc.getString("color")
                );
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Pronađi korisnika po username-u
    public lifemanagement.User getUserByUsername(String username) {
        try {
            Document doc = collection.find(Filters.eq("username", username)).first();
            if (doc != null) {
                return new lifemanagement.User(
                        doc.getObjectId("_id").toHexString(),
                        doc.getString("username"),
                        doc.getString("password"),
                        doc.getString("email"),
                        doc.getString("firstName"),
                        doc.getString("lastName"),
                        doc.getString("theme"),
                        doc.getString("color")
                );
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Pronađi korisnika po ID-u
    public lifemanagement.User getUserById(String userId) {
        try {
            Document doc = collection.find(Filters.eq("_id", new ObjectId(userId))).first();
            if (doc != null) {
                return new lifemanagement.User(
                        doc.getObjectId("_id").toHexString(),
                        doc.getString("username"),
                        doc.getString("password"),
                        doc.getString("email"),
                        doc.getString("firstName"),
                        doc.getString("lastName"),
                        doc.getString("theme"),
                        doc.getString("color")
                );
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // UPDATE - Ažuriraj korisničke podatke
    public boolean updateUserProfile(String userId, String firstName, String lastName, String email) {
        try {
            collection.updateOne(
                    Filters.eq("_id", new ObjectId(userId)),
                    Updates.combine(
                            Updates.set("firstName", firstName),
                            Updates.set("lastName", lastName),
                            Updates.set("email", email)
                    )
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ažuriraj temu i boju
    public boolean updateTheme(String userId, String theme, String color) {
        try {
            collection.updateOne(
                    Filters.eq("_id", new ObjectId(userId)),
                    Updates.combine(
                            Updates.set("theme", theme),
                            Updates.set("color", color)
                    )
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Obriši korisnika
    public boolean deleteUser(String userId) {
        try {
            collection.deleteOne(Filters.eq("_id", new ObjectId(userId)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Promijeni lozinku
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        try {
            lifemanagement.User user = getUserById(userId);
            if (user != null && user.getPassword().equals(oldPassword)) {
                collection.updateOne(
                        Filters.eq("_id", new ObjectId(userId)),
                        Updates.set("password", newPassword)
                );
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}