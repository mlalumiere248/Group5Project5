
/*Megan Lalumiere
* This project portion did not complete most of the requirements. The main window does not open anything when a button
* is pressed. No data is displayed and there is not way for the user to give input.*/
package bsu.comp152;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class PhoneController implements Initializable {
    @FXML
    private TextArea DataDisplay;
    @FXML
    private ListView DataList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPhoneData();
        loadPhoneCodeData();
    }

    private void loadPhoneData() {
        var loc1= " http://country.io/names.json";
        var requestBuilder = HttpRequest.newBuilder();
        var dataGrabber = HttpClient.newHttpClient();
        var dataRequest1 = requestBuilder.uri(URI.create(loc1)).build();
        HttpResponse<String> response1 = null;
        try {
            response1 = dataGrabber.send(dataRequest1, HttpResponse.BodyHandlers.ofString());
        }catch(IOException e){
            System.out.println("Error connecting to network or site");
        }
        catch (InterruptedException e){
            System.out.println("Connection to site broken");
        }
        if (response1 == null ){
            System.out.println("Something went terribly wrong, ending program");
            System.exit(-1);
        }
        var usefulData1 = response1.body();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        var gson = new Gson();
        Map<String, String> mymap1 = gson.fromJson(usefulData1, type);
        var dataAsList1 = new ArrayList<String>(mymap1.keySet());
        ObservableList<String> stateNames = FXCollections.observableList(dataAsList1);
        DataList.setItems(stateNames);

    }

    private void loadPhoneCodeData() {
        var loc2= "http://country.io/phone.json ";
        var requestBuilder = HttpRequest.newBuilder();
        var dataGrabber = HttpClient.newHttpClient();
        var dataRequest2 = requestBuilder.uri(URI.create(loc2)).build();
        HttpResponse<String> response2= null;
        try {
            response2 = dataGrabber.send(dataRequest2, HttpResponse.BodyHandlers.ofString());
        }catch(IOException e){
            System.out.println("Error connecting to network or site");
        }
        catch (InterruptedException e){
            System.out.println("Connection to site broken");
        }
        if (response2 == null ){
            System.out.println("Something went terribly wrong, ending program");
            System.exit(-1);
        }
        var usefulData2 = response2.body();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        var gson = new Gson();
        Map<String, String> mymap2 = gson.fromJson(usefulData2, type);
        var dataAsList2 = new ArrayList<String>(mymap2.keySet());
        ObservableList<String> stateCode = FXCollections.observableList(dataAsList2);
        DataList.setItems(stateCode);

    }



    private String getCountry(){
        TextInputDialog answer = new TextInputDialog("");
        answer.setHeaderText("Gathering Information");
        answer.setContentText("What country would you like? ");
        answer.setWidth(400);
        answer.setResizable(true);
        Optional<String> result = answer.showAndWait();
        if (result.isPresent())
            return result.get();
        else
            return "";
    }

}
