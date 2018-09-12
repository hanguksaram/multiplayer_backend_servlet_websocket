package app.services;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class JsonHandler  {
    public static String[] readPropertyFromObject(String jsonObj, String... props) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonObj))) {
            JsonObject jsonMessage = reader.readObject();
            String[] propsArry = new String[props.length];
            for (int i = 0; i < props.length; i++) {
                propsArry[i] = jsonMessage.getString(props[i]);
            }
            return propsArry;
            }

        }

    }

