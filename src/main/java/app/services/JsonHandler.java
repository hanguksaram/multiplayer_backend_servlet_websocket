package app.services;

import javax.json.*;
import java.io.StringReader;
import java.lang.reflect.Field;

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


    public static String createJsonMessage(GameStatusNotifier.gameStatus gameStatus, Object... objects) {

        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder jsonObjectBuilder = factory.createObjectBuilder().add("status", gameStatus.toString());
        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        for (Object obj: objects
             ) {
            Class type = obj.getClass();
            JsonObjectBuilder jsonObjBuilder = factory.createObjectBuilder();
            Field[] fields = type.getDeclaredFields();
            for (Field field: fields ) {
                try{field.setAccessible(true);
                    jsonObjBuilder.add(field.getName(), field.get(obj).toString());}
                catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }

            }
            arrayBuilder.add(jsonObjBuilder);

        }
        jsonObjectBuilder.add("data", arrayBuilder);
        return jsonObjectBuilder.build().toString();

    }
}

