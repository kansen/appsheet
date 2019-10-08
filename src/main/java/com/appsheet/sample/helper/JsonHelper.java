package com.appsheet.sample.helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

/**
 * @author Kansen Wu
 */
@Component
public class JsonHelper {

    public JsonObject parseJsonStr(String jsonStr) {
        JsonParser parser = new JsonParser();
        return parser.parse(jsonStr).getAsJsonObject();
    }

    public JsonElement getJsonElement(JsonObject json, String name) {
        return json.get(name);
    }

    public JsonArray getJsonArray(JsonObject json, String name) {
        return (JsonArray)json.get(name);
    }
}
