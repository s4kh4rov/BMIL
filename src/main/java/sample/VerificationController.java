package sample;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;
import static sample.DBConstants.COLLECTION_NAME;
import static sample.DBConstants.DB_NAME;

public class VerificationController {
    @FXML
    public PasswordField password;
    @FXML
    public TextField id;
    @FXML
    public Label result;
    private double[] vector;
    private String passwd;
    private static final String SUCCESSFULLY = "УСПЕШНО";
    private static final String UNSUCCESSFULLY = "НЕУДАЧА";

    public void initialize(double[] vector, String password) {
        this.vector = vector;
        this.passwd = password;
    }

    public void verification(ActionEvent actionEvent) {
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection collection = database.getCollection(COLLECTION_NAME);
            Document document = (Document) collection.find(eq("_id", new ObjectId(id.getText()))).first();
            if (document == null) {
                showResult(UNSUCCESSFULLY);
                return;
            }
            JSONArray dbVector = new JSONArray(document.get("vector").toString());
            if (document.get("_id").toString().equals(id.getText()) &&
                    BCrypt.checkpw(passwd, document.get("password").toString()) &&
                    PasswordService.compareVector(dbVector, vector)) {
                showResult(SUCCESSFULLY);
            } else {
                showResult(UNSUCCESSFULLY);
            }

        }
    }

    private void showResult(String s) {
        switch (s) {
            case SUCCESSFULLY:
                result.setTextFill(Color.GREEN);
                break;
            default:
                result.setTextFill(Color.RED);
                break;
        }
        result.setText(s);
    }
}
