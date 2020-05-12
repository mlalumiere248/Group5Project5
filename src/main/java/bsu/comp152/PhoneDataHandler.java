package bsu.comp152;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class PhoneDataHandler {
    private HttpClient dataGrabber;
    private String webLocation;

    public PhoneDataHandler(String webLocation) {
        dataGrabber = HttpClient.newHttpClient();
        this.webLocation = webLocation;
    }
    public ArrayList<phoneDataType> getData1(){
        var requestBuilder = HttpRequest.newBuilder();
        var dataRequest = requestBuilder.uri(URI.create(webLocation)).build();
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
        var jsonInterpreter = new Gson();
        var phoneData = jsonInterpreter.fromJson(usefulData, response1DataType.class);
        return phoneData.results;

    }

    class response1DataType {
        String abbreviation;
        String Country;
        ArrayList<phoneDataType> results;
    }

    class phoneDataType {
        String abbreviation;
        String Country;

        @Override
        public String toString() {
            return "Country Abbreviation: " +Country ;
        }
    }

    public ArrayList<phoneDataType> getData2(){
        var requestBuilder = HttpRequest.newBuilder();
        var dataRequest = requestBuilder.uri(URI.create(webLocation)).build();
        HttpResponse<String> response = null;
        try {
            response=dataGrabber.send(dataRequest, HttpResponse.BodyHandlers.ofString());
        }catch(IOException e){
            System.out.println("Error connecting to network or site"); }
        catch (InterruptedException e){
            System.out.println("Connection to site broken"); }
        if (response == null ){
            System.out.println("Something went terribly wrong, ending program");
            System.exit(-1);
        }
        var usefulData = response.body();
        var jsonInterpreter = new Gson();
        var phoneCodeData = jsonInterpreter.fromJson(usefulData, response2DataType.class);
        return phoneCodeData.results;
    }

    class response2DataType {
        String Country;
        Float code;
        ArrayList<phoneDataType> results;
    }
    class phoneCodeDataType {
        String Country;
        Float Code;

        @Override
        public String toString() {
            return "Country: " +Country;  //only title for display later
        }}
}

