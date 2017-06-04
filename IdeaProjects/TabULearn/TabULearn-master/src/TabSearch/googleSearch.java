package TabSearch;

//import Models.SearchQuery;
//import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Cooper on 6/1/2017.
 */
public class googleSearch {
    public googleSearch(String songToBeSearched){

    }
    public static void main(String[] args) throws IOException {
     //   Gson gson = new Gson();
    //    SearchQuery searchQuery;
        URL url = new URL(
                "https://www.googleapis.com/customsearch/v1?key=AIzaSyDvmpt6icH-UN0-e9twhgN-wqRwIJk9X3Q&cx=013036536707430787589:_pqjad5hr1a&q=flowers&alt=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String stringToBeDeleted =""; // this string will be the placeholder for JSON string of google search query
        JSONObject fullObject = new JSONObject(stringToBeDeleted);
        JSONArray jsonItems = fullObject.getJSONArray("items");
        for (int i = 0; i < jsonItems.length(); i++) {
            String link = jsonItems.getJSONObject(i).getString("link");
        }

        String output;
        System.out.println("Output from Server .... \n");
/*        while ((output = br.readLine()) != null) {
            for (SearchQuery s :
                 ) {

            }
            System.out.println(output);
        }*/

        conn.disconnect();
    }
}

