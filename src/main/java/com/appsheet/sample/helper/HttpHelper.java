package com.appsheet.sample.helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kansen Wu
 */
@Component
public class HttpHelper {

    public final static String SERVICE_END_POINT = "http://appsheettest1.azurewebsites.net/sample";

    public final static String LIST_PATH = "/list";

    public static String userDetailUrl = SERVICE_END_POINT + "/detail/%s";

    private final JsonHelper jsonHelper;

    @Autowired
    public HttpHelper(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    public JsonObject doConnection(String url) throws IOException {
        HttpURLConnection connection = getConnection(url, "GET");
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException(String.format("Invalid Response Code: %s", responseCode));
        }
        return handleResponse(connection);
    }

    public List<String> getResult(JsonObject response) {
        List<String> idList = new ArrayList<String>();
        try {
            JsonArray ids = jsonHelper.getJsonArray(response,"result");
            for (JsonElement id : ids) idList.add(id.getAsString());
        } catch (NullPointerException e) {
            // Its a show stopper if there is no result
            throw new NullPointerException("Result not found");
        }
        return idList;
    }

    public String getToken(JsonObject response) {
        try {
            JsonElement tokenElement =
                    jsonHelper.getJsonElement(response, "token");
            return tokenElement.getAsString();
        } catch (NullPointerException e) {
            // stop continuing retrieve from endpoint.
            return null;
        }
    }

    public String getNewUserDetailUrl(String token) {
        return new StringBuilder(SERVICE_END_POINT)
                .append(LIST_PATH)
                .append("?token=")
                .append(token)
                .toString();
    }

    private HttpURLConnection getConnection(String url, String httpMethod)
            throws IOException {
        URL endPointUrl = new URL(url);
        HttpURLConnection connection = 
            (HttpURLConnection) endPointUrl.openConnection();
        connection.setRequestMethod(httpMethod);
        return connection;
    }

    private JsonObject handleResponse(HttpURLConnection connection)
            throws IOException {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        StringBuffer response = new StringBuffer();
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        return jsonHelper.parseJsonStr(response.toString());
    }
    
}
