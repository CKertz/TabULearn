package TabSearch;

//import Models.SearchQuery;
//import com.google.gson.Gson;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Cooper on 6/1/2017.
 */
public class googleSearch {
    public googleSearch(String songToBeSearched){
        ArrayList<String> listedLinks = new ArrayList<>();


        songToBeSearched = formatQuery(songToBeSearched);
        listedLinks = getLinks(songToBeSearched);
    }
    public static String formatQuery(String query){
        String reformattedQuery = "";
        for (int i = 0; i < query.length() ; i++) {
            if (query.charAt(i) == ' '){
                reformattedQuery += "_";

            }else{
                reformattedQuery+=query.charAt(i);
            }
        }
        return reformattedQuery;
    }
    public static ArrayList<String> getLinks(String query){
        ArrayList<String> listedLinks = new ArrayList<>();
        try{
            URL url = new URL("https://www.googleapis.com/customsearch/v1?key=AIzaSyDvmpt6icH-UN0-e9twhgN-wqRwIJk9X3Q&cx=013036536707430787589:_pqjad5hr1a&q=" + query + "_guitar_tab&alt=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            String finalJSON = buffer.toString();

           //Above gets JSON raw, below is attempting to parse what we need

            JSONObject fullObject = new JSONObject(finalJSON);
            JSONArray jsonItems = fullObject.getJSONArray("items");
            for (int i = 0; i < jsonItems.length(); i++) {
                String link = jsonItems.getJSONObject(i).getString("link");
                listedLinks.add(link);
            }
            conn.disconnect();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("Output from Server .... \n");

        return listedLinks;
    }
    public static void main(String[] args) throws IOException {
        ArrayList<String> listedLinks = new ArrayList<>();

        String query ="smoke on the water";
        query = formatQuery(query);
        listedLinks = getLinks(query);

    }
}

