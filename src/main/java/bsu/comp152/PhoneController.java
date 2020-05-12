package bsu.comp152;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class PhoneController implements Initializable {
    @FXML
    private TextArea DataDisplay;
    @FXML
    private ListView DataList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData() {
        var loc = "http://country.io/phone.json";
        var requestBuilder = HttpRequest.newBuilder();
        var dataGrabber = HttpClient.newHttpClient();
        var dataRequest = requestBuilder.uri(URI.create(loc)).build();
        HttpResponse<String> response = null;
        try {
            response = dataGrabber.send(dataRequest, HttpResponse.BodyHandlers.ofString());
        }catch(IOException e){
            System.out.println("Error connecting to network or site");
        }
        catch (InterruptedException e){
            System.out.println("Connection to site broken");
        }
        if (response == null ){
            System.out.println("Something went terribly wrong, ending program");
            System.exit(-1);
        }
        var usefulData = response.body();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        var gson = new Gson();
        Map<String, String> mymap = gson.fromJson(usefulData, type);
        var dataAsList = new ArrayList<String>(mymap.keySet());
        ObservableList<String> stateNames = FXCollections.observableList(dataAsList);
        DataList.setItems(stateNames);
    }
}
