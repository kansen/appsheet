package com.appsheet.sample.helper;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class JsonHelperTest {

    private JsonHelper jsonHelper;

    @Before
    public void setUp() {
        jsonHelper = new JsonHelper();
    }

    @Test
    public void testParseJsonStr() {
        String jsonStr = "{\"result\":[1,2], \"token\":\"1234\"}";
        JsonObject expectedResult = setupTestJsonObject();
        JsonObject result = jsonHelper.parseJsonStr(jsonStr);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetJsonElement() {
        JsonObject inputObject = setupTestJsonObject();
        JsonElement expectedResult = setupTestJsonElement();
        JsonElement result = jsonHelper.getJsonElement(inputObject, "token");
        assertThat(result).isEqualTo(expectedResult);
    }

    private JsonObject setupTestJsonObject() {
        JsonArray array = new JsonArray();
        array.add(1);
        array.add(2);
        JsonObject object = new JsonObject();
        object.addProperty("token", "1234");
        object.add("result", array);
        return object;
    }

    private JsonElement setupTestJsonElement() {
        JsonObject object = new JsonObject();
        object.addProperty("token", "1234");
        return (JsonElement)object.get("token");
    }
}
