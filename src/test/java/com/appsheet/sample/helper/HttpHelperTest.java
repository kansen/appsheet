package com.appsheet.sample.helper;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@RunWith(SpringRunner.class)
public class HttpHelperTest {

    private static final String TEST_URL = "http://appsheettest1.azurewebsites.net/sample/List";

    @Mock
    private JsonHelper jsonHelper;

    @Mock
    HttpURLConnection connection;

    private HttpHelper httpHelper;

    @Before
    public void setUp() {
        httpHelper = new HttpHelper(jsonHelper);
    }


    @Test
    public void testDoConnection() throws IOException {
        JsonObject result = genTestJsonHelper();
        byte[] responseBytes = "{\"result\":[1,2]}".getBytes();
        connection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(connection.getInputStream())
                .thenReturn(new ByteArrayInputStream(responseBytes));
        Mockito.when(jsonHelper.parseJsonStr(Mockito.anyString())).thenReturn(result);
        JsonObject expectedResult = httpHelper.doConnection(TEST_URL);
        assertThat(result).isEqualTo(expectedResult);
    }

    private JsonObject genTestJsonHelper() {
        return jsonHelper.parseJsonStr("\"result\":[1,2]}");
    }

}
