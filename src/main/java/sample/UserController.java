package sample;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;

public class UserController {
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button save;
    private static final String DB_NAME = "bmil";
    private static final String COLLECTION_NAME = "users";
    private double[] vector;


    public void initialize(double[] vector) {
        this.vector = vector;
    }

    public void saveUser(ActionEvent actionEvent) {
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection collection = database.getCollection(COLLECTION_NAME);
            Document user = new Document();
            user.put("name", name.getText());
            user.put("email",email.getText());
            user.put("password", BCrypt.hashpw(password.getText(),BCrypt.gensalt()));
            user.put("vector",Arrays.toString(vector));
            collection.insertOne(user);

        }
        ((Stage)save.getScene().getWindow()).close();
    }
}
